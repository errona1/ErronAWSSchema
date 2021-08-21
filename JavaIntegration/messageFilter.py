import json
import boto3
import base64
import sys
import multiprocessing
import headerFilter
from jsonschema import validate

#ON FAILURE/EMPTY RELOAD
def reloadSchema():
    global schema
    s4 = boto3.client("s3")
    
    filename = 'schema.json'
    print("Looking For Latest Schema from Bucket with filename: "+filename)
    
    try:
        fileObj = s4.get_object(Bucket = "myeabucket", Key=filename);
    except Exception as e:
        print(filename+": Does Not exist in Bucket. No Schema being Used.")
        file = open("localSchema.json","w")
        file.write("{}")
        file.close()
        schema = {}
        return {}
    
    file_content = fileObj["Body"].read().decode('utf-8')
    file = open("localSchema.json","w")
    file.write(file_content)
    file.close()
    try:
        schema = json.loads(file_content)
    except ValueError as er:
        print(filename+": Found in Bucket but INVALID JSON. No Schema being Used.")
        schema = None
        return None
    
    print(filename+": Found in Bucket and Cached.")

    return schema

def getLocal():
    global schema
    print("Looking For Cached Schema: "+"localSchema.json")

    try:
        with open("localSchema.json", "r") as f:
            data = f.read()
            f.close()
    except Exception as e:
        print("Cached Schema: "+"localSchema.json"+" Not Found")
        return reloadSchema()
    
    try:
        schema = json.loads(data)

    except ValueError as er:
        print(data)
        print("Cached Schema: "+"localSchema.json"+" Found BUT INVALID JSON")
        schema = None
        return None
    print("Cached Schema: "+"localSchema.json"+" Found")

    return schema

    
        
schema = getLocal()

def checkMessage(message):
    try:
        jsonmessage = json.loads(message)
    except ValueError as er:
        print(message)
        print("MESSAGE IS NOT VALID JSON, MESSAGE WILL BE FILTERED OUT")
        return 0
    return 1

def schemaVal(message):
    global schema
    try:
        jsonmessage = json.loads(message)
    except ValueError as er:
        print("MESSAGE IS NOT VALID JSON, MESSAGE WILL BE FILTERED OUT:")
        print(message)
        return 0

    print("Trying to Validate Message:")
    print("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX")
    print(jsonmessage)
    print("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX")
    print("Against Schema:")
    print("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX")
    print(schema)
    print("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX")
    try:
        validate(instance=jsonmessage, schema=schema)
    except Exception as e:
        print("Status Failed:")
        print("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX")
        print(e)
        print("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX")

        reloadSchema()
        return schemaValHelper(jsonmessage)
    print("Message Passed All Filters: ", jsonmessage)
    return 1
    
def schemaValHelper(jsonmessage):
    global schema
  
    print("Trying to Validate Message:")
    print("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX")
    print(jsonmessage)
    print("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX")
    print("Against Schema:")
    print("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX")
    print(schema)
    print("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX")
    try:
        validate(instance=jsonmessage, schema=schema)
    except Exception as e:
        print("Status Failed:")
        print("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX")
        print(e)
        print("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX")

        return 0
    if not schema:
        print("NO Filters. Message Passes: ", jsonmessage)
    else:
        print("Message Passed All Filters: ", jsonmessage)
    return 1

def process(message):
    global schema
    if schema:
        return schemaVal(message)
    else:
        reloadSchema()
        if schema:
            return schemaVal(message)
        else:
            if schema is None:
                print("INVALID SCHEMA")
                #return 0 - if we dont want messages to pass when invalid schema
            try:
                jsonmessage = json.loads(message)
            except ValueError as er:
                print(message)
                print("NO SCHEMA AND MESSAGE IS NOT VALID JSON")
                return 0
            print("SCHEMA VALIDATION SKIPPED. MESSAGE: ", message)
            return 1
    

def lambda_handler(args):
    print("------------------"+"In Python messageFilter.py Filtering JSON Message Content"+"------------------")

     
    #tt.lambda_handler(args) #COMMENT OUT FOR THREADING
    eventS = (str(args[1]))
    if eventS[0] == '\'':
        #FOR FORMATTED COMMANDLINE ENTRIES
        event = json.loads(eventS[1:-1])
    else:
        #FOR LITERAL ENTRIES FROM OTHER PROGRAMS SUCH AS JAVA
        event = json.loads(eventS)
    
    print(event)
    
    global schema
    

    records = ''
    r2 = []

    if event['eventSource'] == 'aws:mq':
        records = event['messages']
        for index, value in enumerate(records): 
            payload=base64.b64decode(records[index]['data'])
            
            message = str(payload, 'UTF-8')
            i = process(message)

            skip = 0
            try:
                hd = records[index]['headers']
            except:
                print("Headers do not Skipping Header Filtering")
                skip = 1
            hdrs = 1
            if skip == 0:
                hdrs = headerFilter.run(json.dumps(hd))
            if i == 0 or hdrs == 0:
                r2.append(index)
        r2.sort(reverse = True)
        print(r2)
        for i in r2:
            print(i)
            del records[i]
        print("Success: Filtered Message:")

        print(event)
            
    else:
        records = event['records']
        for topic in records:
            #print(topic)
            for index, value in enumerate(records[topic]):
                payload=base64.b64decode(records[topic][index]['value'])
                

                message = str(payload, 'UTF-8')
                print(message)
                print(records[topic][index])
                print(index)
                i = process(message)
                
                skip = 0
                try:
                    hd = records[topic][index]['headers']
                except:
                    print("Headers do not Exist Skipping Header Filtering")
                    skip = 1
                hdrs = 1
                if skip == 0:
                    hdrs = headerFilter.run(json.dumps(hd))
                if i == 0 or hdrs == 0:
                    r2.append(index)
            
            r2.sort(reverse = True)
            print(r2)
            for i in r2:
                print(i)
                del records[topic][i]
        print("Success: Filtered Message:")
        print(event)#Pipe Open to STDOUT 0
    


   


if __name__ == '__main__':
    #DIRECT CALL TO THIS SCRIPT(FROM COMMANDLINE - NOT JAVA) NEED TO PASS IN THE EVENT SOURCE LIKE SO - ''\EVENTSOURCE'\'
    lambda_handler(sys.argv)
