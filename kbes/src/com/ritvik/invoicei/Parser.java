//My Package
package com.ritvik.invoicei;

//Used Libs
import java.io.*;
import java.util.ArrayList;
import org.jsoup.*;
import org.jsoup.nodes.*;
import java.util.logging.*;
import java.util.regex.*;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

//Parser Class
public class Parser {
    //html file whih will be processed
    String htmlFile;
    
    //BAN
    String BAN;
    
    //Cycle Month
    String CM;
        
    //Cycle Year
    String CY;
    
    //Cycle Code
    String CC;
    
    //Bill Seq No
    String BSN;
    
    //This String buffer will be processed in Cate's Class
    StringBuffer toCat;
    
    //Failed Rule Arraylist
    ArrayList<String> r;

    Parser(String file, String ban, String cc, String cy, String cm, String bsn){
        //Pass File Arg to htmlFile
        htmlFile=file;
        
        //Pass BAN arg to BAN
        BAN=ban;
        
        //Cycle Month
        CM=cm;
        
        //Cycle Year
        CY=cy;
        
        //Cycle Code
        CC=cc;
        
        //Bill Seq
        BSN=bsn;
        
        //failed rule init
        r = new ArrayList<String>();
    }
    
    void parse(){
        //HTML File Reader
        BufferedReader r = null;
        
        //We will save buffer in here
        StringBuffer html = new StringBuffer();
        try {
            Launcher.log(BAN+" Opening File "+htmlFile);
            //Html File
            File f = new File(htmlFile);
            
            //Reader Initiated
            r = new BufferedReader(new FileReader(f));
            
            //Reading File Buffer
            String line = "NOT NULL";
            while(line != null){
                line = r.readLine();
                if(line != null) html.append(line);
            }
        //Exception HAndling  
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Parser.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Parser.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                //Closing Reader
                r.close();
            } catch (IOException ex) {
                Logger.getLogger(Parser.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        Launcher.log(BAN+" Using JSoup to grab all paragraphs");
        //Parser from JSOUP Lib
        Document doc = Jsoup.parse(html.toString());
     
        //Tag p selected
        org.jsoup.select.Elements para = doc.select("p");
        
        //All Selected Tags HTML content to Buffer
        toCat = new StringBuffer(); //If already filled, it will make it blank
        for(Element p : para){
            toCat.append(p.html()+"\n");
        }
        
        //Removing Unwanted HTML entity refrence
        toCat=new StringBuffer(toCat.toString()
                .replaceAll("&amp;", "&")
                .replaceAll("&nbsp;", " ")
                .replaceAll("&#160;", " ")
                .replaceAll("&#8217;", "'")
        );
        
        Launcher.log(BAN+" Raw Data Ready for Processing");
        
        
    }
    
    void process(){
        StringBuffer t = new StringBuffer();
        String[] ar = toCat.toString().split("\n");
        for (int i=0; i< ar.length;i++){
            if(ar[i].contains("<b>Changes")){
                if(ar[i+1].contains("$")){
                    i++;
                    continue;
                }
            }
            if(ar[i].contains("<b>Included")){
                while((i+1< ar.length) && false==ar[i+1].contains("<b> Subtotal:") ) i++;
                continue;
            }
            if(ar[i].contains("Balance of remaining paymentsDescription")){
                while(false==ar[i].contains("will not reflect transactions made in proximity of bill") ) i++;
                continue;
            }
            t.append(ar[i]+"\n");
        }
        //System.err.println(t);
        for(String vTable : new Database().getTableEntry(BAN,CC,CM,CY)){        
            TableProcessor table = new TableProcessor(BAN);
            if(false==table.process(t, vTable)){
                r.add(vTable);
                Launcher.log(BAN+" Processing Columns for Ammount Validation Failed "+vTable);
            }
            else Launcher.log(BAN+" Processing Columns for Ammount Validation Success "+vTable);
        }
    }
    
    void checkRules() {
        //Get All Query FROM DB
        Database d = new Database();
        
        Launcher.log(BAN+" Getting All Rules for the BAN");
        //All Rules Query
        ArrayList<String> q = d.getQuery(BAN,CC,CY,CM);
        
        int rules_run=0;
        //Rules Matching

        
        for(String x : q){
//1|||M1|||R1|||SELECT Amt, Ban, C FROM TABLE WHERE BAN=$INPUT_BAN|||QUERY|||TAG1~TAG2~TAG3;TAG4;TAG5|||1~2~1|||50|||2~0~1;2;0|||<b> Total:
//0   1    2    3                                                    4       5                          6       7    8           9
            String[] parts = x.split("\\|\\|\\|");
            Launcher.log(BAN+" :: Checking Rule "+parts[0]+"-"+parts[1]+"-"+parts[2]);
            //getting regex to match with lines
            String raw_regexes = null;
            try{
                raw_regexes = d.getRuleText(parts[3], BAN, CC, CY, CM, BSN, parts[4]);
            }
            catch(Exception e){
                e.printStackTrace();
                Launcher.log(BAN+" Exception : "+e.getLocalizedMessage());
            }
            if(raw_regexes!=null){
                String[] regexes = raw_regexes.split("\n");
                boolean isMultiRowRule = (!parts[9].equalsIgnoreCase("null"));
                for(String regex: regexes){
                    Launcher.log(BAN +" Rule Data Returned : \""+regex+"\"");
                    if(regex==null){
                        Launcher.log(BAN +" Please check BAN account detail for rule : "+parts[0]+"-"+parts[1]+"-"+parts[2]);
                        continue;
                    }

                    String raw_tags = parts[5];
                    if(isMultiRowRule){
                        String sub = regex.split("~")[0];
                        raw_tags = raw_tags.replaceAll("\\$INPUT_SUBSCRIBER", sub);
                        regex = regex.substring(regex.indexOf('~')+1);
                    }

                    String tags[] = raw_tags.split("~");
                    String offs[] = parts[6].split("~");
                    String[] skips = parts[8].split("~");
                    String[] regex_part = regex.split("~");


                    if(tags.length!=offs.length){
                        Launcher.log(BAN +" Invalid Rule :"+parts[0]+"-"+parts[1]+"-"+parts[2]);
                        r.add(parts[0]+"-"+parts[1]+"-"+parts[2]);
                        continue;
                    }


                    for(int z=0; z< regex_part.length; z++){
                        try{
                            if(regex_part[z].matches("^null$")){
                                Launcher.log(BAN+" Rule return NULL, skiping rule "+parts[0]+"-"+parts[1]+"-"+parts[2]+"-"+(z+1));
                                continue;
                            }

                            rules_run++;
                            //buffer to save specifc lines from buffer
                            StringBuffer local = new StringBuffer();

                            //It will be true if local buffer started
                            boolean start=false;

                            //number of lines after
                            int c = Integer.parseInt(offs[z]);

                            //check if multi tag and procss accordingly
                            String[] tg = tags[z].split(";");
                            int tg_skip = tg.length - 1;
                            int tg_indx = 0;

                            //number of time match should be skip
                            String[] skp= skips[z].split(";");
                            int skip=Integer.parseInt(skp[tg_indx]);

                            //Check
                            if(skp.length!=tg.length){
                                Launcher.log(BAN +" Invalid Rule :"+parts[0]+"-"+parts[1]+"-"+parts[2]+"-"+(z+1));
                                r.add(parts[0]+"-"+parts[1]+"-"+parts[2]+"-"+(z+1));
                                continue;
                            }

                            //Grabing lines in local buffer
                            String pTagsFromHtml[] = toCat.toString().split("\n");
                            int startIndex = pTagsFromHtml.length;

                            for(int loopIndex=0; loopIndex<pTagsFromHtml.length; loopIndex++){
                                String line = pTagsFromHtml[loopIndex];
                                if(line.contains(tg[tg_indx]) || line.matches(".*("+tg[tg_indx]+").*")){
                                    if(skip>=1){ // skiping the current found tag if requires
                                        skip--;
                                    }
                                    else{
                                        if(tg_skip!=tg_indx){ // skiping to next tag if requires
                                            tg_indx++;
                                            skip=Integer.parseInt(skp[tg_indx]); 
                                        }
                                        else{
                                            //now filling the local buffer with number of lines required
                                            start=true;
                                            startIndex=loopIndex;
                                            continue;
                                        }
                                    }
                                }
                                if(start) break;
                            }                

                            //Start and end calculation
                            int iStart=-1, iEnd=-1;
                            if(c>=0){
                                iStart=startIndex;
                                iEnd=startIndex+c;
                            }
                            else{
                                iStart=startIndex+c;
                                iEnd=startIndex;
                            }
                            if(iStart<0) iStart=0;

                            if(isMultiRowRule){
                                int i=iStart;
                                for(;i<pTagsFromHtml.length;i++){
                                    if(pTagsFromHtml[i].contains(parts[9])) break; 
                                }
                                iEnd=i;
                            }

                            if( iEnd >pTagsFromHtml.length)  iEnd =pTagsFromHtml.length-1;

                            //buffer creation
                            for(int i=iStart; i<=iEnd; i++){
                                try{
                                    local.append(pTagsFromHtml[i]+"\n");
                                }
                                catch(Exception e){ 
                                    break;
                                }
                            }

                            Launcher.log(BAN +" Local Buffer : \""+local.toString()+"\"");

                            //Checking in local string if it having ^ or $ to check if it is regex
                            String vRegexString = regex_part[z];
                            if(!(vRegexString.contains("^"))){
                               vRegexString= ".*"+vRegexString+".*";
                            }

                            Launcher.log(BAN +" Rule Data : \""+vRegexString+"\"");

                            //Checking if contains text via regex
                            boolean matched=false;
                            for(String line : local.toString().split("\n")){
                                if(line.matches(vRegexString)){
                                    matched=true;
                                    break;
                                }
                            }

                            //Checking if local buffer is having regex
                            if(matched) {
                                Launcher.log(BAN +" Rule Matched :"+parts[0]+"-"+parts[1]+"-"+parts[2]+"-"+(z+1));
                            }
                            else{
                                Launcher.log(BAN +" Rule Failed :"+parts[0]+"-"+parts[1]+"-"+parts[2]+"-"+(z+1));
                                r.add(parts[0]+"-"+parts[1]+"-"+parts[2]+"-"+(z+1));
                            }
                            d.insertStatusLog(BAN,CC,CM,CY,BSN,parts[0]+"-"+parts[1]+"-"+parts[2]+"-"+(z+1),local.toString(),vRegexString,matched);
                        }
                        catch(Exception e){
                            e.printStackTrace();
                            r.add(parts[0]+"-"+parts[1]+"-"+parts[2]+"-"+(z+1));
                            Launcher.log(BAN+" Exception : "+e.getLocalizedMessage());
                        }
                    }
                }
            }
            else{
                Launcher.log(BAN +" Please check BAN account details for rule : "+parts[0]+"-"+parts[1]+"-"+parts[2]);
            }
        }
        if(rules_run==0 && r.isEmpty()){
            Launcher.log(BAN +" No Rules Matched");
            d.mark(BAN, "Failed", "\"Comment\"",CC,CY,CM);
        }
        else if(r.isEmpty()){
            Launcher.log(BAN +" All Rules Matched");
            //All Rules Matched
            d.mark(BAN, "Success", "\"Comment\"",CC,CY,CM);
        }
        else{
            //Buufer ofr rules not matched
            StringBuffer rules = new StringBuffer("Failed:");
            Launcher.log(BAN +" Few Rule(s) Failed.");
            //Adding each rule in buffer
            for(String rule : r){
                rules.append(rule+";");
            }
            
            //Updating Comment
            d.mark(BAN, rules.toString(), "\"Comment\"",CC,CY,CM);
        }
    }

    void checkRegex() {
        Launcher.log(BAN+" Checking Regex");
        Database d = new Database();
        ArrayList<String> regex = d.getRegex(BAN, CC, CY, CM);
        for ( String rr : regex){
            boolean match = false;
            StringBuffer matches = new StringBuffer();
            String[] regex_part = rr.split("~~~~~~");
            try{
                Pattern p = Pattern.compile(regex_part[1]);
                for(String line : toCat.toString().split("\n")){
                    Matcher m = p.matcher(line);
                    while(m.find()){
                        match = true;
                        matches.append(m.group()+";");
                    }
                }
            }
            catch(Exception e){
                e.printStackTrace();
                Launcher.log(BAN+" "+e.getLocalizedMessage());
            }
            if(match){
                r.add(regex_part[0]);
                Launcher.log(BAN+" : Regex "+regex_part[0]+" Matched : "+matches.toString());
                d.insertStatusLog(BAN, CC, CM, CY, BSN, regex_part[0], matches.toString().length()>4000?matches.toString().substring(0, 3999):matches.toString(), regex_part[1], true);
            }
        }
    }

    void checkOverlap(String pdf) throws IOException {
        PDDocument document = PDDocument.load( new File(pdf));
        try {
            OverlapChecker stripper = new OverlapChecker(BAN);
            stripper.setSortByPosition( true );
            stripper.setStartPage( 0 );
            stripper.setEndPage( document.getNumberOfPages() );
 
            //Writer dummy = new OutputStreamWriter(new ByteArrayOutputStream());
            Writer dummy = new OutputStreamWriter(new ByteArrayOutputStream());
            stripper.writeText(document, dummy);
            
            if(stripper.isOverlappedTextFound()){
                r.add("Overlap");
                new Database().insertStatusLog(BAN, CC, CM, CY, BSN, "Overlap", stripper.getOverlappedStrings(), "Overlapping Text", true);
            }
        }
        finally {
            if( document != null ) {
                document.close();
            }
        }
    }
    
    Double getTotalValue(String vtag, String voff, String vskip, String regex, String vop){
        String[] tags = vtag.split("~");
        String[] offs = voff.split("~");
        String[] skips = vskip.split("~");
        String[] ops = vop.split("~");
               
        Double ret_val = 0.0;
        
        String pTagsFromHtml[] = toCat.toString().split("\n");
        
        for(int index=0; index<tags.length; index++){
            String tag = tags[index];
            int c = Integer.parseInt(offs[index]);
            int startIndex = pTagsFromHtml.length;
            boolean start=false;
            String[] tg = tags[index].split(";");
            int tg_skip = tg.length - 1;
            int tg_indx = 0;
            String[] skp= skips[index].split(";");
            int skip=Integer.parseInt(skp[tg_indx]);
            if(skp.length!=tg.length){
                return -9999999999.00;
            }
            for(int loopIndex=0; loopIndex < pTagsFromHtml.length; loopIndex++){
                String line = pTagsFromHtml[loopIndex];
                if(line.contains(tg[tg_indx]) || line.matches(".*("+tg[tg_indx]+").*")){
                    if(skip>=1){ // skiping the current found tag if requires
                        skip--;
                    }
                    else{
                        if(tg_skip!=tg_indx){ // skiping to next tag if requires
                            tg_indx++;
                            skip=Integer.parseInt(skp[tg_indx]); 
                        }
                        else{
                            //now filling the local buffer with number of lines required
                            start=true;
                            startIndex=loopIndex;
                            continue;
                        }
                    }
                }
                if(start) break;
            }
            //Start and end calculation
            int iStart=-1, iEnd=-1;
            if(c>=0){
                iStart=startIndex;
                iEnd=startIndex+c;
            }
            else{
                iStart=startIndex+c;
                iEnd=startIndex;
            }
            if(iStart<0) iStart=0;
            if( iEnd >pTagsFromHtml.length)  iEnd =pTagsFromHtml.length-1;
            StringBuffer local = new StringBuffer();
            //buffer creation
            for(int i=iStart; i<=iEnd; i++){
                try{
                    local.append(pTagsFromHtml[i]+"\n");
                }
                catch(Exception e){ 
                    break;
                }
            }
            
            //extract value from local
            Pattern p = Pattern.compile(regex);
            for(String line : local.toString().split("\n")){
                Matcher m = p.matcher(line);
                if(m.find()){
                    String match = m.group();
                    Double v = 1.0;
                    if(m.group().contains("(")){
                        v=-1.0;
                    }
                    match = match.replaceAll("[^0-9\\.\\-\\+]", "");
                    Double vMatch = Double.parseDouble(match);
                    vMatch = vMatch*v;
                    if(ops[index].contains("+")) ret_val += vMatch;
                    else if (ops[index].contains("-")) ret_val -= vMatch;
                }
            }
        }
        return ret_val;
    }

    void checkTextRule() {
        Database d = new Database();
        ArrayList<String> text_rules = d.getPdfRule(BAN, CC, CY, CM);
        //
        for(String rr : text_rules){
            String [] parts = rr.split("\\|\\|\\|");
            double x1;
            if(validRulePdfRule(parts[1],parts[2],parts[3],parts[5])){
                x1 = getTotalValue(parts[1],parts[2],parts[3],parts[4],parts[5]);
            }
            else{
                r.add(parts[0]);
                Launcher.log(BAN+" : Invlid Rule "+parts[0]);
                continue;
            }
            double x2;
            if(validRulePdfRule(parts[6],parts[7],parts[8],parts[10])){
                x2 = getTotalValue(parts[6],parts[7],parts[8],parts[9],parts[10]);
            }
            else{
                r.add(parts[0]);
                Launcher.log(BAN+" : Invlid Rule "+parts[0]);
                continue;
            }
            
            if((x1!=x2) || (x1==-9999999999.00) || (x2==-9999999999.00)) {
                r.add(parts[0]);
                Launcher.log(BAN+" : Rule Failed "+parts[0]+" : "+x1+" is not matching with "+x2);
            }
            else{
                Launcher.log(BAN+" : Rule Passed "+parts[0]);
            }
            
        }
    }

    private boolean validRulePdfRule(String a, String b, String c, String d) {
        int i=a.split("~").length;
        if(i!=b.split("~").length) return false;
        if(i!=c.split("~").length) return false;
        if(i!=d.split("~").length) return false;
        return true;
    }
}
