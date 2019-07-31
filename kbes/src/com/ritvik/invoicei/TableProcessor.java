package com.ritvik.invoicei;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class TableProcessor {
	
	private HashMap<String, TableDefinition> tableDefinitions = new HashMap<String, TableDefinition>();
	TableDefinition table = new TableDefinition();
	
    private int globalBufferOffset = 0;
    private int globalBufferOffset_ =-1;
    private boolean startCycleFlag = false;
    String BAN;
     
    public TableProcessor( String ban) {
        this.BAN=ban;
    }
    

    public boolean process(StringBuffer buffer, String tableId){

    	//get all rules for table parsing
    	tableDefinitions = getTableDefinitions();
    	table = tableDefinitions.get(tableId);if (table.isReversible()) {
            return processReverseTable(buffer, tableId);
        } else if ((table.getCyclic()== null) || (table.getCyclic().equals("")) || (table.getCyclic().equals(new Integer("0")))) {
            return processSimpleTable(buffer, tableId);
    	} else if (table.getCyclic().equals(new Integer("1"))) {
            return processCyclicTable(buffer, tableId);
    	} else {
            Launcher.log(BAN+" ERROR IN TABLE DEFINITION"); 
            return false;
    	}
    	
    }
    
    //processor for simple table without cyclic structure
    private boolean processSimpleTable(StringBuffer buffer, String tableId){
    	
    	ArrayList<String> entries = getInputEntries(buffer, tableId);
    	boolean result = processEntries(entries, tableId);
    	return result;
    } 	
    	
    	/*ArrayList<BigDecimal> values = new ArrayList<BigDecimal>();
    	Launcher.log(values);
    	
    	Pattern pattern = Pattern.compile("\\(?\\$([\\d]+.{1}[\\d]{2})\\)?");
    	
    	BigDecimal checkSum = new BigDecimal("0.00");
    	BigDecimal value = new BigDecimal("0.00");
    	BigDecimal total = new BigDecimal("0.00");

    	for(String s: entries){  
    	    if (!s.toLowerCase().contains(table.getCheckSumItem().toLowerCase())) {
    	    	for (String item : table.getThingsToIgnore()) {
    	    		if (!s.toLowerCase().contains(item.toLowerCase())){
    	    	    	Matcher matcher = pattern.matcher(s);
    	    	    	if (matcher.find()) {
    	    	    		if (matcher.group().contains("(")  && matcher.group().contains(")")){  	    			
    	    	    			value = new BigDecimal(matcher.group().replace("$","").replace("(","").replace(")",""));
    	    	    			value = value.negate();
    	    	    		} else {
    	    	    			value = new BigDecimal(matcher.group().replace("$",""));
    	    	    		}    	    		
    	    	    		checkSum = checkSum.add(value);
    	    	    	}   	    			
    	    		}
    	    	}	  	    	
    	    } else {
    	    	Matcher matcher = pattern.matcher(s);
    	    	if (matcher.find()) {
	    	    	if (matcher.group().contains("(")  && matcher.group().contains(")")){  	    			
	    	    		total = new BigDecimal(matcher.group().replace("$","").replace("(","").replace(")",""));
	    	    		total = total.negate();
	    	    	} else {
	    	    		total = new BigDecimal(matcher.group().replace("$",""));
	    	    	} 		
    	    	}  
    	    	
    	    }
    	}
    	if (total.equals(checkSum)) {
    		Launcher.log("TOTAL IS " + total+"\n");
    		Launcher.log("CHECK SUM IS " +checkSum+"\n");
    		Launcher.log("TOTAL AND CHECK SUM ARE EQUAL\n");
    	} else {
    		 throw new RuntimeException("TOTAL AND CHECK SUM ARE NOT EQUAL!!!");
    	}*/
   // }
    
    //get input entries for simple, not cyclic table
    private ArrayList<String>  getInputEntries(StringBuffer buffer, String tableId) {
       	
    	table = tableDefinitions.get(tableId);
    	boolean startFlag = false;
    	String[] lines = buffer.toString().split("\\n");
    	ArrayList<String> entries = new ArrayList<String>();
    	for(String s: lines){
    	    if ((startFlag == false) && (s.matches(".*("+table.getStart()+").*"))){
    	    	startFlag = true;
    	    	continue;
    	    }
    	    if (startFlag == true) {   	    	
    	    	entries.add(s); 
    	    }
            
    	    if (s.matches(".*("+table.getCheckSumItem()+").*")){
    	    	break;
    	    }
    	}
    	Launcher.log(BAN+" Simple Input Entries: " + entries.toString() + "\n");
    	return entries;
    }
    
    private boolean processCyclicTable(StringBuffer buffer, String tableId){
    	boolean result = false;
    	while (true) {
    		ArrayList<String> entries = getCyclicInputEntries(buffer, tableId);
    		if ((entries!=null)  && (entries.size()>0)) {
    			result = processEntries(entries, tableId);
    		} else {
    			break;
    		}
                if(globalBufferOffset_ ==globalBufferOffset) break;
    	}
    	return result;
    }
    
    //get input entries for cyclic table structure
    public ArrayList<String>  getCyclicInputEntries(StringBuffer buffer, String tableId) {
    	globalBufferOffset_ =globalBufferOffset;
    	table = tableDefinitions.get(tableId);
    	String[] lines = buffer.toString().split("\n");
    	ArrayList<String> entries = new ArrayList<String>();
    	//use startCycleFlag and globalBufferOffset for tracking the state that we entered the cyclic table and the line we're currently at
    	for (int i = globalBufferOffset; i < lines.length; i++) {
            
    	    if (lines[i].matches(".*("+table.getStart()+").*")){
    	    	startCycleFlag = true;
    	    	continue;
    	    }
    	    if (startCycleFlag == true) {   	    	
    	    	entries.add(lines[i]); 
    	    }
    	    if ((startCycleFlag == true) && (lines[i].matches(".*("+table.getCheckSumItem()+").*"))){
    	    	globalBufferOffset = ++i;
    	    	break;
    	    }
    	    if (lines[i].matches(".*("+table.getCycleEnd()+").*")) {
    	    	return null;
    	    }
    	}
    	Launcher.log(BAN+" Cyclic Input Entries: " + entries.toString() + "\n");
        
    	return entries;
    }
    
    //process entries as a table with total and checksum using regex
    private boolean processEntries(ArrayList<String> entries, String tableId){

    	table = tableDefinitions.get(tableId);
    	ArrayList<BigDecimal> values = new ArrayList<BigDecimal>();
    	Launcher.log(values.toString());
    	
    	Pattern pattern = Pattern.compile("\\(?\\$([\\d,]+\\.{1}[\\d]{2})\\)?");
    	
    	BigDecimal checkSum = new BigDecimal("0.00");
    	BigDecimal value = new BigDecimal("0.00");
    	BigDecimal total = new BigDecimal("0.00");

    	for(String s: entries){  
    	    if (!s.matches(".*("+table.getCheckSumItem()+").*")) {
                boolean itemFound=false;
                for (String item : table.getThingsToIgnore()) {
                    if (s.matches(".*("+item+").*")) itemFound=true;
                }
                if (!itemFound){
                Matcher matcher = pattern.matcher(s);
                    if (matcher.find()) {
                        if (matcher.group().contains("(")  && matcher.group().contains(")")){  	
                                //values in () - need to deduct
                                value = new BigDecimal(matcher.group().replace("$","").replace("(","").replace(")","").replace(",", ""));
                                value = value.negate();
                        } else {
                                value = new BigDecimal(matcher.group().replace("$","").replace(",", ""));
                        } 
                        Launcher.log(BAN+"   ->>>>> "+value);
                        checkSum = checkSum.add(value);
                    }   	    			
                }
    	    } else {
    	    	Matcher matcher = pattern.matcher(s);
    	    	if (matcher.find()) {
	    	    	if (matcher.group().contains("(")  && matcher.group().contains(")")){  	    			
	    	    		total = new BigDecimal(matcher.group().replace("$","").replace("(","").replace(")","").replace(",", ""));
	    	    		total = total.negate();
	    	    	} else {
	    	    		total = new BigDecimal(matcher.group().replace("$","").replace(",", ""));
	    	    	} 		
    	    	}  
    	    	
    	    }
    	}
        	if (total.equals(checkSum)) {
        		Launcher.log(BAN+" TOTAL IS " + total+"\n");
        		Launcher.log(BAN+" CHECK SUM IS " +checkSum+"\n");
        		Launcher.log(BAN+" TOTAL AND CHECK SUM ARE EQUAL\n");

        		return true;
        	} else {
        		Launcher.log(BAN+" TOTAL IS " + total+"\n");
        		Launcher.log(BAN+" CHECK SUM IS " +checkSum+"\n");
        		Launcher.log(BAN+" ***ERROR***TOTAL AND CHECK SUM ARE NOT EQUAL\n");
        		return false;
        	}
    }

    private boolean processReverseTable(StringBuffer buffer, String tableId) {
        boolean result = false;
        ArrayList<String> entries = getReverseTableEntries(buffer, tableId);
        if ((entries != null) && (entries.size() > 0)) {
            result = processReverseTables(entries, tableId);
        }
        return result;
    }

    // get all input entries for all reverse tables
    public ArrayList<String> getReverseTableEntries(StringBuffer buffer, String tableId) {
        boolean startReverseFlag = false;
        table = tableDefinitions.get(tableId);
        String[] lines = buffer.toString().split("\\n");
        ArrayList<String> entries = new ArrayList<String>();
        // use startReverseFlag and globalReverseOffset for tracking the state that we
        // entered the cyclic reverse table and the line we're currently at
        for (int i = 0; i < lines.length; i++) {
                if (lines[i].contains(table.getStart())) {
                        startReverseFlag = true;
                        continue;
                }
                if (startReverseFlag == true) {
                        entries.add(lines[i]);
                }
/*			if (lines[i].contains(table.getCycleEnd())) {
                        break;
                }*/
        }
        Launcher.log(BAN+" Cyclic Input Entries: " + entries.toString() + "\n");
        return entries;
    }

        public ArrayList<String> getReverseTables(StringBuffer buffer, String tableId) {

            boolean startReverseFlag = false;
            ArrayList<String> tags = table.getTags();

            table = tableDefinitions.get(tableId);
            String[] lines = buffer.toString().split("\\n");
            ArrayList<String> entries = new ArrayList<String>();
            // use startReverseFlag and globalReverseOffset for tracking the state that we
            // entered the cyclic reverse table and the line we're currently at
            for (int i = 0, j = 0; i < lines.length; i++) {
                    if (lines[i].contains(table.getStart())) {
                            startReverseFlag = true;
                            continue;
                    }
                    if ((startReverseFlag == true) && (j < tags.size()) && (lines[i].startsWith(tags.get(j)))) {
                            entries.add(lines[i]);
                            j++;
                    }
                    if ((startReverseFlag == true) && ((j == tags.size() - 1) || (lines[i].startsWith(tags.get(j))))) {
                            break;
                    }
                    if (lines[i].contains(table.getCycleEnd())) {
                            return null;
                    }
            }
            Launcher.log(BAN+" Cyclic Input Entries: " + entries.toString() + "\n");
            return entries;
        }

        // process entries as a table with total and checksum using regex
        private boolean processReverseTables(ArrayList<String> entries, String tableId) {

                table = tableDefinitions.get(tableId);
                ArrayList<String> tags = table.getTags();
                ArrayList<BigDecimal> values = new ArrayList<BigDecimal>();
                System.out.println(values);

                Pattern pattern = Pattern.compile("\\(?\\$([\\d]+.{1}[\\d]{2})\\)?");

                BigDecimal checkSum = new BigDecimal("0.00");
                BigDecimal value = new BigDecimal("0.00");
                BigDecimal total = new BigDecimal("0.00");

                BigDecimal lastCheckSum = new BigDecimal("0.00");
                BigDecimal lastValue = new BigDecimal("0.00");
                BigDecimal lastTotal = new BigDecimal("0.00");

                boolean lastTable = false;
                boolean isLastEntry = false;

                for (int i = 0, j = 0; i < entries.size(); i++) {
                        if (j == tags.size() - 1) {
                                lastTable = true;
                                lastTotal = total;
                        }
                        if (lastTable == true) {
                                for (int k = i; k < entries.size(); k++) {
                                        if (entries.get(k).startsWith(tags.get(j))) {
                                                Matcher matcher = pattern.matcher(entries.get(k));
                                                if (matcher.find()) {
                                                        if (matcher.group().contains("(") && matcher.group().contains(")")) {
                                                                lastTotal = new BigDecimal(
                                                                                matcher.group().replace("$", "").replace("(", "").replace(")", ""));
                                                                lastTotal = lastTotal.negate();
                                                        } else {
                                                                lastTotal = new BigDecimal(matcher.group().replace("$", ""));
                                                        }
                                                }
                                        } else {
                                                boolean isIgnored = false;
                                                for (String item : table.getThingsToIgnore()) {
                                                        if (entries.get(i).contains(item)) {
                                                                isIgnored = true;
                                                        }
                                                }
                                                if (isIgnored == false) {
                                                        Matcher matcher = pattern.matcher(entries.get(k));
                                                        if (matcher.find()) {
                                                                if (matcher.group().contains("(") && matcher.group().contains(")")) {
                                                                        // values in () - need to deduct
                                                                        lastValue = new BigDecimal(
                                                                                        matcher.group().replace("$", "").replace("(", "").replace(")", ""));
                                                                        lastValue = lastValue.negate();
                                                                } else {
                                                                        lastValue = new BigDecimal(matcher.group().replace("$", ""));
                                                                }
                                                                lastCheckSum = lastCheckSum.add(lastValue);
                                                        }
                                                }
                                        }
                                        for (String item : table.getReverseEnds()) {
                                                if (entries.get(k).contains(item)) {
                                                        isLastEntry = true;
                                                }
                                        }
                                        if (isLastEntry == true) {
                                                break;
                                        }
                                }
                                if (!(lastTotal.equals(new BigDecimal("0.00"))) && !(lastCheckSum.equals(new BigDecimal("0.00")))) {
                                        if (lastTotal.equals(lastCheckSum)) {
                                                Launcher.log(BAN+" TOTAL IS " + lastTotal + "\n");
                                                Launcher.log(BAN+" CHECK SUM IS " + lastCheckSum + "\n");
                                                Launcher.log(BAN+" TOTAL AND CHECK SUM ARE EQUAL\n");
                                                return true;
                                        } else {
                                                Launcher.log(BAN+" TOTAL IS " + lastTotal + "\n");
                                                Launcher.log(BAN+" CHECK SUM IS " + lastCheckSum + "\n");
                                                Launcher.log(BAN+" ***ERROR***TOTAL AND CHECK SUM ARE NOT EQUAL\n");
                                                return false;
                                        }
                                }
                        }
                        if ((j < tags.size() - 1) && (entries.get(i).startsWith(tags.get(j + 1))) && (lastTable == false)) {
                                if (total.equals(checkSum)) {
                                        Launcher.log(BAN+" TOTAL IS " + total + "\n");
                                        Launcher.log(BAN+" CHECK SUM IS " + checkSum + "\n");
                                        Launcher.log(BAN+" TOTAL AND CHECK SUM ARE EQUAL\n");
                                        
                                } else {
                                        Launcher.log(BAN+" TOTAL IS " + total + "\n");
                                        Launcher.log(BAN+" CHECK SUM IS " + checkSum + "\n");
                                        Launcher.log(BAN+" ***ERROR***TOTAL AND CHECK SUM ARE NOT EQUAL\n");
                                        return false;
                                }
                                checkSum = new BigDecimal("0.00");
                                value = new BigDecimal("0.00");
                                total = new BigDecimal("0.00");
                                j++;
                        }

                        if (((j < tags.size() - 2) && (entries.get(i).startsWith(tags.get(j + 2))))) {
                                if (total.equals(checkSum)) {
                                        Launcher.log(BAN+" TOTAL IS " + total + "\n");
                                        Launcher.log(BAN+" CHECK SUM IS " + checkSum + "\n");
                                        Launcher.log(BAN+" TOTAL AND CHECK SUM ARE EQUAL\n");
                                } else {
                                        Launcher.log(BAN+" TOTAL IS " + total + "\n");
                                        Launcher.log(BAN+" CHECK SUM IS " + checkSum + "\n");
                                        Launcher.log(BAN+" ***ERROR***TOTAL AND CHECK SUM ARE NOT EQUAL\n");
                                        return false;
                                }
                                checkSum = new BigDecimal("0.00");
                                value = new BigDecimal("0.00");
                                total = new BigDecimal("0.00");
                                j = j + 2;
                        }

                        if (entries.get(i).contains(tags.get(j))) {
                                Matcher matcher = pattern.matcher(entries.get(i));
                                if (matcher.find()) {
                                        if (matcher.group().contains("(") && matcher.group().contains(")")) {
                                                total = new BigDecimal(matcher.group().replace("$", "").replace("(", "").replace(")", ""));
                                                total = total.negate();
                                        } else {
                                                total = new BigDecimal(matcher.group().replace("$", ""));
                                        }
                                }
                        } else {
                                boolean isIgnored = false;
                                for (String item : table.getThingsToIgnore()) {
                                        if (entries.get(i).contains(item)) {
                                                isIgnored = true;
                                        }
                                }
                                if (isIgnored == false) {
                                        Matcher matcher = pattern.matcher(entries.get(i));
                                        if (matcher.find()) {
                                                if (matcher.group().contains("(") && matcher.group().contains(")")) {
                                                        // values in () - need to deduct
                                                        value = new BigDecimal(matcher.group().replace("$", "").replace("(", "").replace(")", ""));
                                                        value = value.negate();
                                                } else {
                                                        value = new BigDecimal(matcher.group().replace("$", ""));
                                                }
                                                checkSum = checkSum.add(value);
                                        }
                                }
                        }
                }
                return true;
        }

    //get all rules for table parsing
    private  HashMap<String, TableDefinition>  getTableDefinitions() {
    	tableDefinitions = new Database().getTableDefinitions();  
    	return tableDefinitions;
    }
    
}
