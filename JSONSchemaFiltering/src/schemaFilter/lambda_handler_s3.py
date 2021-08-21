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
        fileObj = s4.get_object(Bucket = "schemaeventbucket", Key=filename);
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

def noSchema():
    global schema
    if schema is None:
        print("JSON schema.json file invalid JSON, SKIPPING SCHEMA VALIDATION\n")
        return 1
    print("No Schema Given, SKIPPING SCHEMA VALIDATION\n")
    return 0
    

def schemaVal(message):
    global schema
    try:
        jsonmessage = json.loads(message)
    except ValueError as er:
        print(message)
        print("MESSAGE IS NOT VALID JSON, MESSAGE WILL BE FILTERED OUT")
        return 0

    if not schema:
        noSchema()
        print("Message: ", message)
        return 0
    try:
        validate(instance=jsonmessage, schema=schema)
    except:
        print("Does Not Match Schema")
        print(jsonmessage)
        print(schema)
        return 0
        
    print("Message Passed All Filters: ", jsonmessage)
    return 0
    


def lambda_handler(event, context):
    print(event)

    global schema

    #IMPORTANT - NOTE WHEN OUR CACHE FALLS OFF AND LAMBDA REINITIALIZES 
    #.BSS, .TEXT, AND .DATA SECTIONS WE WILL AUTOMATICALLY GET THE SCHEMA FROM
    #S3
    try:
        if event['Records'][0]['eventSource'] == 'aws:s3':
            print("Schema Before: ", schema)
            if event['Records'][0]['eventName'] == 'ObjectRemoved:Delete':
                schema = {}
            else:
                reloadSchema()
            print("Schema After: ", schema)
            return
    except:
        pass

    records = ''
    
    if event['eventSource'] == 'aws:mq':
        records = event['messages']
        for index, value in enumerate(records): 
            payload=base64.b64decode(records[index]['data'])
            
            message = str(payload, 'UTF-8')
            #print(message)
            schemaVal(message)
            
    else:
        records = event['records']
        for topic in records:
            #topic
            for index, value in enumerate(records[topic]):
                payload=base64.b64decode(records[topic][index]['value'])
                
                message = str(payload, 'UTF-8')
                
                schemaVal(message)
                
    return event
