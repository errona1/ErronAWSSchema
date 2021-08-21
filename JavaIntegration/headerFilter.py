import json
import boto3
import base64
import sys
from jsonschema import validate

check = 0
def reloadHeader():
    global header
    global check
    s4 = boto3.client("s3")
    
    filename = 'header.json'
    print("Looking For Latest Schema from Bucket with filename: "+filename)
    try:
        fileObj = s4.get_object(Bucket = "myeabucket", Key=filename);
    except Exception as e:
        print(filename+": Does Not exist in Bucket. No Schema being Used.")
        file = open("header.json","w")
        file.write("{}")
        file.close()
        header = {}
        check = 1
        #sys.exit(1) becasue we know
        return {}
    
    file_content = fileObj["Body"].read().decode('utf-8')
    file = open("header.json","w")
    file.write(file_content)
    file.close()
    try:
        header = json.loads(file_content)
    except ValueError as er:
        print(filename+": Found in Bucket but INVALID JSON. No Schema being Used.")
        header = None
        check = 1
        #sys.exit(1) becasue we know
        return None
    
    print(filename+": Found in Bucket and Cached.")

    return header

def getEventLocal():
    global header
    print("Looking For Cached Schema: "+"header.json")

    try:
        with open("header.json", "r") as f:
            data = f.read()
            f.close()
    except Exception as e:
        print("Cached Schema: "+"header.json"+" Not Found")
        return reloadHeader()
    
    try:
        header = json.loads(data)

    except ValueError as er:
        print(data)
        print("Cached Schema: "+"tts.json"+" Found BUT INVALID JSON.")
        header = None
        return None
    print("Cached Schema: "+"header.json"+" Found")

    return header

    
        
header = getEventLocal()


def recheck(event):
    global header
    print("Trying to REValidate Message:")
    print("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX")
    print(event)
    print("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX")
    print("Against Schema:")
    print("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX")
    print(header)
    print("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX")
    try:
        validate(instance=event, schema=header)
    except Exception as e:
        print("Status Fail Event Does Not Validate:")
        print("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX")
        print(e)
        print("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX")

        return 0
    return 1
    

    
def lambda_handler(args):
    eventS = (str(args[1]))
    run(eventS)

def run(eventS):
    print("------------------"+"In Python headerFilter.py Filtering Header Content"+"------------------")

    if eventS[0] == '\'':
        #FOR FORMATTED COMMANDLINE ENTRIES
        event = json.loads(eventS[1:-1])
    else:
        #FOR LITERAL ENTRIES FROM OTHER PROGRAMS SUCH AS JAVA
        event = json.loads(eventS)
    
    #for i in range(len(event)):
     #   print(event[i])
    k = start(event)
    if k == 0:
        print("Status Fail: Headers Do Not Validate")
        return 0
    print("Status Success: Event Validated:")
    print(event)
    print("------------------"+"END headerFilter.py"+"------------------")
    return 1

def start(event):
    global header
    if check == 0:
        if header:
            try:
                print("Trying to Validate Message:")
                print("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX")
                print(event)
                print("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX")
                print("Against Schema:")
                print("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX")
                print(header)
                print("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX")

                validate(instance=event, schema=header)
            except Exception as e:
                print("Status Failed:")
                print("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX")
                print(e)
                print("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX")

                reloadHeader()
                if not header:
                    return 1
                return recheck(event)
            return 1
        else:
            reloadHeader()
            return recheck(event)

    return 1 
    


if __name__ == '__main__':
    #DIRECT CALL TO THIS SCRIPT(FROM COMMANDLINE - NOT JAVA) NEED TO PASS IN THE EVENT SOURCE LIKE SO - ''\EVENTSOURCE'\'
    lambda_handler(sys.argv)
