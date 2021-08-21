import json
import boto3
import base64
import sys
from jsonschema import validate

check = 0
def reloadEventSchema():
    global eventschema
    global check
    s4 = boto3.client("s3")
    
    filename = 'eventschema.json'
    print("Looking For Latest Schema from Bucket with filename: "+filename)

    
    try:
        fileObj = s4.get_object(Bucket = "myeabucket", Key=filename);
    except Exception as e:
        print(filename+": Does Not exist in Bucket. No Schema being Used.")
        file = open("tts.json","w")
        file.write("{}")
        file.close()
        eventschema = {}
        check = 1
        #sys.exit(1) becasue we know
        return {}
    

    file_content = fileObj["Body"].read().decode('utf-8')
    file = open("tts.json","w")
    file.write(file_content)
    file.close()
    try:
        eventschema = json.loads(file_content)
    except ValueError as er:
        print(filename+": Found in Bucket but INVALID JSON. No Schema being Used.")
        eventschema = None
        check = 1
        #sys.exit(1) becasue we know
        return None
    
    print(filename+": Found in Bucket and Cached.")

    return eventschema

def getEventLocal():
    global eventschema

    print("Looking For Cached Schema: "+"tts.json")

    try:
        with open("tts.json", "r") as f:
            data = f.read()
            f.close()
    except Exception as e:
        print("Cached Schema: "+"tts.json"+" Not Found")
        return reloadEventSchema()
    
    try:
        eventschema = json.loads(data)

    except ValueError as er:
        print(data)
        print("Cached Schema: "+"tts.json"+" Found BUT INVALID JSON")

        eventschema = None
        return None
    print("Cached Schema: "+"tts.json"+" Found")
    return eventschema

    
        
eventschema = getEventLocal()


def recheck(event):
    global eventschema
    print("Trying to Validate Message:")
    print("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX")
    print(event)
    print("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX")
    print("Against Schema:")
    print("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX")
    print(eventschema)
    print("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX")
    try:
        validate(instance=event, schema=eventschema)
    except Exception as e:
        print("Status Fail. Event Does Not Validate:")
        print("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX")
        print(e)
        print("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX")

        sys.exit(0)#EXIT EVERYTHING    

def lambda_handler(args):
    print("------------------"+"In Python eventFilter.py Filtering Event Format"+"------------------")
    eventS = (str(args[1]))
    if eventS[0] == '\'':
        #FOR FORMATTED COMMANDLINE ENTRIES
        event = json.loads(eventS[1:-1])
    else:
        #FOR LITERAL ENTRIES FROM OTHER PROGRAMS SUCH AS JAVA
        event = json.loads(eventS)
    
    if check == 0:
        if eventschema:
            try:
                print("Trying to Validate Message:")
                print("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX")
                print(event)
                print("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX")
                print("Against Schema:")
                print("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX")
                print(eventschema)
                print("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX")

                validate(instance=event, schema=eventschema)
            except Exception as e:
                print("Status Failed:")
                print("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX")
                print(e)
                print("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX")

                reloadEventSchema()
                if not eventschema:
                    pass
                recheck(event)
        else:
            reloadEventSchema()
            recheck(event)

           #here 
    print("Status Success: Event Validated:")
    print(event)

    print("------------------"+"END Python eventFilter.py"+"------------------")

    sys.exit(1) #UNCOMMENT FOR THREADING
    print("Sucess:")
    print("---------------------------------")
    print(event)
    print("---------------------------------")
    print(eventschema)
    print("---------------------------------")


if __name__ == '__main__':
    #DIRECT CALL TO THIS SCRIPT(FROM COMMANDLINE - NOT JAVA) NEED TO PASS IN THE EVENT SOURCE LIKE SO - ''\EVENTSOURCE'\'
    lambda_handler(sys.argv)
