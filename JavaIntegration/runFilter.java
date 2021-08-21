import java.lang.*;
import java.util.*;
import java.io.*;
import java.util.concurrent.TimeUnit;

class Foo implements Runnable {
    private volatile String event;
    private String cm;

    @Override
    public void run() {
        String[] cmd = new String[]{"python3.8", "messageFilter.py", this.cm};
        try{
            Process p = Runtime.getRuntime().exec(cmd);
            try {
                // Open Pipe to File Handler 0: STDOUT
                String line;
                String eline;
                InputStreamReader isr = new InputStreamReader(p.getInputStream());
                BufferedReader rdr = new BufferedReader(isr);
                String out = null;
                System.out.println("---------Java Start Message/Header Filter Thread--------");
                while((line = rdr.readLine()) != null) { 
                    System.out.println(line);
                    out = line;
                }
                this.event = out;
                System.out.println("Java Message/Header Filtered Message:");

                System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
                System.out.println(this.event);

                System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");


                isr = new InputStreamReader(p.getErrorStream());
                rdr = new BufferedReader(isr);

                //Add prints to print error readings
                while((eline = rdr.readLine()) != null) { 
                    //System.out.println(eline);
                }
                p.waitFor();
                System.out.println("---------Java End Message/Header Filter Thread--------");



            } catch (InterruptedException e) {
                System.out.println("Java Error: error In waiting for proccess execution Script:");
                System.out.println(e);
                System.exit(1);
            }
            

        } catch (IOException  e) {
            System.out.println("Error: error In executing the execution Script");
            System.out.println(e);
            System.exit(1);
        }

       
    }

    public String getEvent() {
        return this.event;
    }

    public int getValue(){
        return 0;
    }

    public void setS(String s){
        this.cm = s;
    }
}


class Foo2 implements Runnable {
    private volatile int value;
    private String cm;

    @Override
    public void run() {
        String[] cmd = new String[]{"python3.8", "eventFilter.py", this.cm};
        try{
            Process p = Runtime.getRuntime().exec(cmd);
            try {
                // Open Pipe to File Handler 0: STDOUT
                String line;
                InputStreamReader isr = new InputStreamReader(p.getInputStream());
                BufferedReader rdr = new BufferedReader(isr);
                System.out.println("---------Java Start Event Format Filter Thread--------");
                while((line = rdr.readLine()) != null) { 
                    System.out.println(line);
                } 

                isr = new InputStreamReader(p.getErrorStream());
                rdr = new BufferedReader(isr);
                while((line = rdr.readLine()) != null) { 
                    System.out.println(line);
                }
                p.waitFor();



            } catch (InterruptedException e) {
                System.out.println("Error: error In waiting for proccess execution Script:");
                System.out.println(e);
                System.exit(1);
            }
            int exitStatus = p.exitValue();
            if(exitStatus == 1){
                this.value = 1;
                System.out.print("Java Event Filter Thread: Event Format Passed");
            }
            else{
                this.value = 0;
                System.out.print("Java Event Filter Thread: Event Format Did NOT Pass");
            }
            System.out.println("---------Java End Event Format Filter Thread--------");


        } catch (IOException  e) {
            System.out.println("Error: error In executing the execution Script:");
            System.out.println(e);

            System.exit(1);
        }

       
    }

    public int getValue() {
        return value;
    }

    public void setS(String s){
        this.cm = s;
    }
}

class runFilter {
    public static void main(String[] args) {
        try {
            
            //WHEN DIRECTLY INVOKING JAVA VIA COMMAND LINE THE EVENT INPUT FORMAT MUST BE - \''EVENT'\' FOR
            //BASH TO PROPERLY RECOGNIZE QUOTES

            //LITERAL STRING FORMATTING EXAMPLE:
            //String s = "{\"eventSource\":\"aws:mq\",\"eventSourceArn\":\"arn:aws:mq:us-west-2:622311011993:broker:mybroker:b-e7e2a779-dc96-4f60-af90-ca5c64e0c266\",\"messages\":[{\"messageID\":\"ID:b-e7e2a779-dc96-4f60-af90-ca5c64e0c266-1.mq.us-west-2.amazonaws.com-41583-1627609853162-4:6:1:1:4\",\"messageType\":\"jms/text-message\",\"timestamp\":1628192090487,\"deliveryMode\":1,\"correlationID\":\"\",\"replyTo\":\"null\",\"destination\":{\"physicalName\":\"q\"},\"redelivered\":false,\"type\":\"\",\"expiration\":0,\"priority\":0,\"data\":\"eyJmaXJzdE5hbWUiOiJCaWxsYSIsImxhc3ROYW1lIjoib25ldyIsImFnZSI6MTcsImNvbG9yIjoieWVsbG93In0=\",\"brokerInTime\":1628192090488,\"brokerOutTime\":1628192090488}]}";
            
            System.out.println("Start Java Consumer. The Incoming Event is:");
            System.out.println(args[0]);

            Foo foo = new Foo();
            foo.setS(args[0]);
            
            Thread thread = new Thread(foo);
            thread.start();

            /* TO SEE LOGS IN SERIAL EXECUTION UNCOMMENT THREAD JOIN HERE AND COMMENT OUT BELOW */
            /*
            try {
                thread.join();
            } catch (InterruptedException e) {
                System.out.println("Error:");
                System.out.println(e);
            }*/


            Foo2 foo2 = new Foo2();
            foo2.setS(args[0]);
            
            Thread thread2 = new Thread(foo2);
            thread2.start();

            //ANY ADDITIONAL CODE 
            
            try {
                thread.join();
            } catch (InterruptedException e) {
                System.out.println("Error:");
                System.out.println(e);
            }
            
            String event = foo.getEvent(); 

            try {
                thread2.join();
            } catch (InterruptedException e) {
                System.out.println("Error:");
                System.out.println(e);
            }

            int exitStatus = foo2.getValue();
            if(exitStatus == 1){
                System.out.println("Java Consumer: Event Format Passes - Filtered Message: ");//POLL event
                System.out.println(event);
            }
            else{
                System.out.println("Java Consumer: Event Format Does Not Pass - Discarding Filtered Message");//POLL event
                System.exit(0);
            }
        
} catch (Exception e) {
            System.out.println("Error: error In executing the execution Scripts");
            System.out.println(e);
            System.exit(1);
        }
        return;
    }  
}