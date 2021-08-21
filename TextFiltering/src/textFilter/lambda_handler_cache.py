import json
import boto3
import base64

def filterString():

    global file_content
    s4 = boto3.client("s3")


    filename = 'filter.txt'
    print("Filename: ", filename)

   
    try:
        fileObj = s4.get_object(Bucket = "myfilterbucket", Key=filename);
    except:
        print("filter.txt Does Not exists in s3 bucket filters")
        file_content = ""
        return ""
    
    file_content = fileObj["Body"].read().decode('utf-8')

    #now i need this in my package to filter actually json message coming in 
    print("Here is filters: ", file_content)

    return file_content

file_content = filterString()

def recheck(message):
    global file_content
    localString = file_content
    filterString()
    if file_content == localString:
        return 1
    else:
        return 0
   
def messageHelper(message):
    global file_content
    wordlist = file_content.split(",")
    for word in wordlist:
        if word not in message:
            print("MESSAGE FILTERED OUT")
            return 0
    
    print("message passed filters: ", message)
    return 0
    
def messageHand(message):
    global file_content
    wordlist = file_content.split(",")

    #check
    for word in wordlist:
        print(word)
    for word in wordlist:
        if word not in message:
            isSame = recheck(message)
            if isSame:
                print("MESSAGE FILTERED OUT")
                return 0
            else:
                if file_content:
                    messageHelper(message)
                else:
                    print("NO FILTER FILE GIVEN. MESSAGE: ", message)
                return 0

    print("Message Passed All Filters: " + message)

    return 0

def check(message):
    global file_content
    if not file_content:
        print("NO FILTER TEXT GIVEN. MESSAGE: ", message)
        return 0
    messageHand(message)
        
def process(message):
    global file_content
    if not file_content:
        filterString()
        check(message)
        return 0
    
    messageHand(message)
    return 0
        
    
def lambda_handler(event, context):
    
    global file_content
    records = ''

    if event['eventSource'] == 'aws:mq':
        records = event['messages']
        for index, value in enumerate(records): 
            payload=base64.b64decode(records[index]['data'])

            message = str(payload, 'UTF-8')
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
