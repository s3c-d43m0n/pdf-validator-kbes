/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ritvik.invoicei;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;



/**
 *
 * @author ritvikc
 */
public class Launcher {
    // Debug Mode
    public static boolean DEBUG             = false;    
    // Database Details
    public static String  hostname          = "localhost";
    public static int     port              = 1521;
    public static String  database          = "database";
    public static String  username          = "username";
    public static String  password          = "password";
    public static String  folder            = ".";
    public static int     threads           = 1;
    public static boolean needOverlapCheck  = false;
    public static double  overlap_tolerance = 1.0;    
    public static String  tns_entry         = "()";
    // Date Formatter
    public static SimpleDateFormat format   = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss.SSS a");
    
    // Main Method From here
    public static void main(String[] args) {
        // Config File Location Check
        File config = new File("tool.conf");
        
        boolean fileProblem = false;
        boolean[] checks={false,true,false,false,false,true,true,true,true,true};
        
        if(config.exists()){
            BufferedReader r = null;
            try {
                r = new BufferedReader(new FileReader(config));
                String x=r.readLine();
                while(x!=null){
                    if(x.matches("^HOSTNAME=.*")){   
                        hostname=x.split("=")[1];
                        checks[0]=true;
                    }
                    if(x.matches("^PORT=.*")){
                        port=Integer.parseInt(x.split("=")[1]);
                        //checks[1]=true;
                    }
                    if(x.matches("^SID=.*")){
                        database=x.split("=")[1];
                        checks[2]=true;
                    }
                    if(x.matches("^USERNAME=.*")){
                        username=x.split("=")[1];
                        checks[3]=true;
                    }
                    if(x.matches("^PASSWORD=.*")){
                        try{
                            password=(new String(Base64.getDecoder().decode(x.substring(x.indexOf('=')+1)))).replaceAll("[\n\r]", "");
                            checks[4]=true;
                        }
                        catch(Exception e){
                            System.out.println("ERROR: PASSWORD IS NOT IN PROPER FORMAT");
                            fileProblem=true;
                            checks[4]=false;
                        }
                    }
                    if(x.matches("^DIRECTORY=.*")){
                        folder=x.split("=")[1];
                        //checks[5]=true;
                    }  
                    if(x.matches("^THREADS=.*")){
                        threads=Integer.parseInt(x.split("=")[1]);
                        //checks[6]=true;
                    }
                    if(x.matches("^OVERLAPCHECK=.*")){
                        if(Integer.parseInt(x.split("=")[1])==1){
                            needOverlapCheck=true;
                            //checks[7]=true;
                        }
                    }
                    if(x.matches("^OVERLAPTOLERANCE=.*")){
                        overlap_tolerance = Double.parseDouble(x.split("=")[1]);
                        //checks[8]=true;
                    }
                    if(x.matches("^TNS=.*")){
                        tns_entry = x.substring(x.indexOf('=')+1);
                        //checks[9]=true;
                    }
                                      
                    x=r.readLine();
                }
            } catch (FileNotFoundException ex) {
                fileProblem=true;
            } catch (IOException ex) {
                fileProblem=true;
            } finally {
                try {
                    r.close();
                } catch (IOException ex) {
                    fileProblem=true;
                }
            }
        }
        else{
            fileProblem=true;
        }
        for(Boolean q : checks){
            if(!q) fileProblem=true;
        }
        if(fileProblem){
            System.err.println("ERROR: Please Check "+config.getAbsolutePath()+" File.");
            System.exit(-1);
        }
        
        
        //Argument Check
        if(args.length >=3 && args.length <=4){
            //Command Line Parameter
            String cc = args[0];
            String cm = args[1];
            String cy = args[2];
            
            //Checking for optional flag for debug mode
            if(args.length==4){
                if(0==args[3].compareToIgnoreCase("DEBUG")){
                    DEBUG=true;
                }
            }
            
            ////Get BANs from Table and Get the files
            Launcher.log("Starting Program and checking for BAN");
            LinkedList<String> bans = new Database().getBans(cc,cy,cm);
            Launcher.log("Total Bans Collected for Processing : "+bans.size());  
            
            //Cant have more threds than number of total bans
            threads = (threads>bans.size())?bans.size():threads;
            
            //Setting Security MAnager to prevent 
            System.setSecurityManager(new MySecurityManager(System.getSecurityManager()));

            //Multithread Execution
            BAN queue[] = new BAN[threads];
            while(bans.size()!=0){    
                for(int index=0; index < threads; index++){
                    if(queue[index] == null || queue[index].isFinished()){
                        String nextBan = bans.remove();
                        queue[index]   = new BAN(nextBan);
                        Launcher.log(nextBan + " queued for validation.");
                        queue[index].start();
                    }
                }
            }
        }
        else{
            viewUsage();
        }
    }
    
    public static void log(String msg){
        if(Launcher.DEBUG){
            System.out.println(format.format(new java.util.Date())+" :: "+msg);
        }
    }

    private static void viewUsage() {
        System.out.println("");
        System.out.println(" ============================================");
        System.out.println("|              Invoice - I                   |");
        System.out.println("|              ~~~~~~~~~~~                   |");
        System.out.println("|  Advanced Bill PDF Validator Tool v1.7.4   |");
        System.out.println(" ============================================");
        System.out.println("");
        System.out.println("");
        System.out.println("USAGE:");
        System.out.println("java -jar <Application.jar> Cycle_Code Cycle_Month Cycle_Year <DEBUG>");
        System.out.println("");
        System.out.println("");
        System.out.println("EXAMPLE:");
        System.out.println("java -jar PdfValidator.jar 02 02 2018 ");
        System.out.println("java -jar PdfValidator.jar 02 02 2018 DEBUG");
        System.out.println("");
        System.out.println("");
        System.out.println("REPORT:");
        System.out.println("Please report code related bugs to: Ritvik Chauhan <contact.ritvik@gmail.com>");
        System.out.println("");
    }
}
