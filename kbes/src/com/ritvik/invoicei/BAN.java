/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ritvik.invoicei;

import java.io.File;
import org.apache.pdfbox.tools.PDFBox;

/**
 *
 * @author ritvikc
 */
public class BAN extends Thread implements Runnable{
    //BAN String
    String BAN;
    String CC,CY,CM,BSN;
    
    //HTML AND PDF FILE LOCATION
    String folder;
    
    //stauts
    boolean status = false;
    
    BAN(String B){
        String[] input=B.split("_");
        this.BAN=input[0];
        this.CC=input[1];
        this.CY=input[2];
        this.CM=input[3];
        this.BSN=input[4];
        folder = Launcher.folder;
    }

    boolean isFinished(){
        return this.status;
    }
    
    @Override
    public void run() {
        //Database Connection
        Database d = new Database();
        d.mark(BAN, "Starting Process",CC,CY,CM);
        String f_pdf = folder+BAN+"_"+CC+".pdf";
        String f_htm = folder+BAN+"_"+CC+".html";
        Launcher.log(BAN+" Checking PDF "+f_pdf);
        if(new File(f_pdf).exists()){
            //Passing Command Line Option to PDFBOX tool
            String[] arg = {
                "ExtractText",
                "-html",
                f_pdf,
                f_htm
            };
            Launcher.log(BAN+" Generating HTML from PDF");
            //CREATING HTML FROM PDF
            d.mark(BAN, "Creating HTML",CC,CY,CM);
            try {
                PDFBox.main(arg);
            } catch (Exception ex) {}
            Launcher.log(BAN+" Generated HTML File");
            d.mark(BAN, "Parsing HTML",CC,CY,CM);
            //Creating Parser for BAN
            Parser vParser = new Parser(f_htm,BAN,CC,CY,CM,BSN);

            //Parsing
            vParser.parse();                
            
            //Processing
            try{
                Launcher.log(BAN+" Table Validation");
                vParser.process(); 
            }
            catch(Exception e){
                e.printStackTrace();
                Launcher.log(BAN+" "+e.getMessage());
            }           
            
            //Checking Regex
            d.mark(BAN, "Running Regex",CC,CY,CM);
            try{
                vParser.checkRegex();
            }
            catch(Exception e){
                e.printStackTrace();
                Launcher.log(BAN+" Exception : "+e.getLocalizedMessage());
            }
            
            if(Launcher.needOverlapCheck){
                //Checking Overlapping
                d.mark(BAN, "Checking Overlapping",CC,CY,CM);
                try{
                    Launcher.log(BAN+" Checking Overlapping");
                    vParser.checkOverlap(f_pdf);
                }
                catch(Exception e){
                    e.printStackTrace();
                    Launcher.log(BAN+" Exception : "+e.getLocalizedMessage());
                }
            }
            
            //Checking Text Parts
            d.mark(BAN, "Running Text Detection",CC,CY,CM);
            try{
                vParser.checkTextRule();
            }
            catch(Exception e){
                e.printStackTrace();
                Launcher.log(BAN+" Exception : "+e.getLocalizedMessage());
            }
            
            //Checking Rules
            d.mark(BAN, "Running Rules",CC,CY,CM);
            try{
               vParser.checkRules();
            }
            catch(Exception e){
                e.printStackTrace();
                Launcher.log(BAN+" Exception : "+e.getLocalizedMessage());
            }
            

            d.mark(BAN, "Completed",CC,CY,CM);
            Launcher.log(BAN+" Process Completed");
        }
        else{
            Launcher.log(BAN+" PDF File Not Found");
            d.mark(BAN, "No PDF Found",CC,CY,CM);
        }
        status=true;
    }
}
