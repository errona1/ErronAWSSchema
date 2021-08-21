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
        file_content = None
        return None
    
    file_content = fileObj["Body"].read().decode('utf-8')

    #now i need this in my package to filter actually json message coming in 
    print("Here is filters: ", file_content)

    return file_content

file_content = filterString()


def messageHand(message):
    global file_content
    wordlist = file_content.split(",")

    #check
    for word in wordlist:
        print(word)
    for word in wordlist:
        if word not in message:
            print("MESSAGE FILTERED OUT")
            return 0

    print("Message Passed All Filters: " + message)

    return 0

  
def process(message):
    global file_content
    if not file_content:
        if file_content is None:
            print("No Filter File\n")
        print("NO FILTER TEXT GIVEN. MESSAGE: ", message)
        return 0
    messageHand(message)
    return 0
        
    
def lambda_handler(event, context):
    
    global file_content
    
    #IMPORTANT - NOTE WHEN OUR CACHE FALLS OFF AND LAMBDA REINITIALIZES 
    #.BSS, .TEXT, AND .DATA SECTIONS WE WILL AUTOMATICALLY GET THE SCHEMA FROM
    #S3
    try:
        if event['Records'][0]['eventSource'] == 'aws:s3':
            print("Filter Text Before: ", file_content)
            if event['Records'][0]['eventName'] == 'ObjectRemoved:Delete':
                file_content = None
            else:
                filterString()
            print("Filter Text After: ", file_content)
            return
    except:
        pass

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
