import java.lang.*;
import java.util.*;
import java.io.*;
import java.util.concurrent.TimeUnit;

class pjava {
    public static void main(String[] args) {
        try {
            
            //WHEN DIRECTLY INVOKING JAVA VIA COMMAND LINE THE EVENT INPUT FORMAT MUST BE - \''EVENT'\' FOR
            //BASH TO PROPERLY RECOGNIZE QUOTES

            //LITERAL ENTRY WITH A EXISTING STRING
            //String s = "{\"eventSource\":\"aws:mq\",\"eventSourceArn\":\"arn:aws:mq:us-west-2:622311011993:broker:mybroker:b-e7e2a779-dc96-4f60-af90-ca5c64e0c266\",\"messages\":[{\"messageID\":\"ID:b-e7e2a779-dc96-4f60-af90-ca5c64e0c266-1.mq.us-west-2.amazonaws.com-41583-1627609853162-4:6:1:1:4\",\"messageType\":\"jms/text-message\",\"timestamp\":1628192090487,\"deliveryMode\":1,\"correlationID\":\"\",\"replyTo\":\"null\",\"destination\":{\"physicalName\":\"q\"},\"redelivered\":false,\"type\":\"\",\"expiration\":0,\"priority\":0,\"data\":\"eyJmaXJzdE5hbWUiOiJCaWxsYSIsImxhc3ROYW1lIjoib25ldyIsImFnZSI6MTcsImNvbG9yIjoieWVsbG93In0=\",\"brokerInTime\":1628192090488,\"brokerOutTime\":1628192090488}]}";
            //System.out.println(s);
            //String[] cmd = new String[]{"python3.8", "/home/errona/workplace/javapack/jp/lambda_handler_cache.py", s};


            //ENTRY FROM COMMAND LINE
            System.out.println(args[0]);
            String[] cmd = new String[]{"python3.8", "messageFilter.py", args[0]};

            Process p = Runtime.getRuntime().exec(cmd);
            try {
                /*// THIS CODE IS TO READ OUTPUTS TO CONSOLE FROM THE PYTHON SCRIPT
                String line;
                InputStreamReader isr = new InputStreamReader(p.getInputStream());
                BufferedReader rdr = new BufferedReader(isr);
                while((line = rdr.readLine()) != null) { 
                    System.out.println(line);
                } 

                isr = new InputStreamReader(p.getErrorStream());
                rdr = new BufferedReader(isr);
                while((line = rdr.readLine()) != null) { 
                    System.out.println(line);
                }*/ //instead of exit we return string of numbers 1,0,1,0 which messages we pass
                p.waitFor();


            } catch (InterruptedException e) {
                System.out.println("NO: We have a error In waiting for proccess execution Script");
                System.exit(1);
            }
            int exitStatus = p.exitValue();
            if(exitStatus == 1)
                System.out.println("Message Passes - Exit: " + exitStatus);//POLL
            else
                System.out.println("Message Does not pass - Exit: " + exitStatus);//NOT POLL
            try{
                TimeUnit.SECONDS.sleep(1);
            }catch(InterruptedException e){
                System.exit(1);
            }
                System.exit(0);
            } catch (IOException  e) {
                System.out.println("NO: We have a error In executing the execution Script");
                System.exit(1);
        }

    }
}