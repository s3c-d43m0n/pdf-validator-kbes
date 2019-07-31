/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ritvik.invoicei;

import java.io.*;
import java.util.*;
import org.apache.pdfbox.text.*;
import org.bouncycastle.util.encoders.Hex;

/**
 *
 * @author ritvikc
 */
public class OverlapChecker extends PDFTextStripper {

    ArrayList<String> overlapped;
    String BAN;
    
    public OverlapChecker(String ban) throws IOException{
        this.BAN=ban;
        overlapped = new ArrayList<String>();
    }
    
    public boolean isOverlappedTextFound(){
        if(overlapped.isEmpty()) return false;
        return true;
    }
   
    @Override
    protected void writeString(String string, List<TextPosition> textPositions) throws IOException {
        double x=0,y=0,w=0,h=0,tx=0,ty=0,tw=0,th=0;
        String c="",tc="";
        int count=0;
        for (TextPosition text : textPositions) {
            //ignoring for WHITESPACE in UTF-8
            if(text.getUnicode().matches("\\s")) continue;
            //ignoring for WHITESPACE in Unicode
            if(text.getUnicode().matches("\\p{Z}")) continue;
            
            c=text.getUnicode();
            //Getting Current Charecter Params
            /*
            x=Math.round(text.getXDirAdj());
            y=Math.round(text.getYDirAdj());
            w=Math.round(Math.floor(text.getWidthDirAdj()));
            h=Math.round(Math.floor(text.getHeightDir()));
            */
            x=text.getXDirAdj();
            y=text.getYDirAdj();
            w=text.getWidthDirAdj();
            h=text.getHeightDir();
            
            //Checking if X,Y cordinates are overlapped
            if(x>=tx && x<(tx+tw-Launcher.overlap_tolerance)) if(y>=ty && y<(ty+th-Launcher.overlap_tolerance)){
                //System.out.println(string);
                //System.out.println("->"+c+"<-  ->"+tc+"<- "+" "+x+","+y+" "+tx+","+ty+" "+w+","+tw+" "+h+","+th+"\n\n");
                //System.out.println(Hex.toHexString(c.getBytes()));
                count++;
            } 
            
            //Assigning current chars params in temp
            tx=x; ty=y; tw=w; th=h; tc=c;
        }
        if(count>0){
            //If overlapped
            overlapped.add(string);
            Launcher.log(BAN+" Overlapped Count : "+count+" for String :: "+string);
        }
    }

    String getOverlappedStrings() {
        StringBuffer x = new StringBuffer();
        for(String s : overlapped){
            x.append(s+";");
        }
        return  x.toString();
    }
}
