import json
import boto3
import base64
from jsonschema import validate


def reloadSchema():
    global schema
    s4 = boto3.client("s3")
    

    filename = 'schema.json'
    print("Filename: ", filename)
    
    try:
        fileObj = s4.get_object(Bucket = "myeabucket", Key=filename);
    except:
        print("schema.json Does Not exists in s3 bucket filters")
        schema = {}
        return {}
    
    file_content = fileObj["Body"].read().decode('utf-8')
    try:
        schema = json.loads(file_content)
    except ValueError as er:
        print("JSON schema.json file invalid JSON, SKIPPING SCHEMA VALIDATION")
        schema = None
        return None
    return schema

schema = reloadSchema()

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
        print(message)
        print("MESSAGE IS NOT VALID JSON, MESSAGE WILL BE FILTERED OUT")
        return 0

    try:
        validate(instance=jsonmessage, schema=schema)
    except:
        reloadSchema()
        schemaValHelper(jsonmessage)
        return 0
    print("Message Passed All Filters: ", jsonmessage)
    return 0
    
def schemaValHelper(jsonmessage):
    global schema
  
    try:
        validate(instance=jsonmessage, schema=schema)
    except:
        print("Does Not Match Schema")
        print(jsonmessage)
        print(schema)
        return 0
    print("Message Passed All Filters: ", jsonmessage)
    return 0

def process(message):
    global schema
    if schema:
        schemaVal(message)
    else:
        reloadSchema()
        if schema:
            schemaVal(message)
            return
        else:
            if schema is None:
                print("INVALID SCHEMA")
            try:
                jsonmessage = json.loads(message)
            except ValueError as er:
                print(message)
                print("NO SCHEMA AND MESSAGE IS NOT VALID JSON")
                return 0
            print("SCHEMA VALIDATION SKIPPED. MESSAGE: ", message)
            return
    

def lambda_handler(event, context):
    print(event)
    
    global schema
    

    records = ''
    
    if event['eventSource'] == 'aws:mq':
        records = event['messages']
        for index, value in enumerate(records): 
            payload=base64.b64decode(records[index]['data'])
            
            message = str(payload, 'UTF-8')
            #print(message)
            process(message)
            
    else:
        records = event['records']
        for topic in records:
            #topic
            for index, value in enumerate(records[topic]):
                payload=base64.b64decode(records[topic][index]['value'])
                
                message = str(payload, 'UTF-8')
                
                process(message)
                
    return event
