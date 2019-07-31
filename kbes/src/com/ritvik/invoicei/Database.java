 package com.ritvik.invoicei;
import java.sql.*;
import java.util.*;
import java.util.logging.*;

public class Database {
    //DB DRIVER
    private static final String driver = "oracle.jdbc.driver.OracleDriver";
    
    //give all collected bans from table for converiosn and parsing
    LinkedList<String> getBans(String cc, String cy, String cm){
        
        //list to collect bans
        LinkedList<String> bans = new LinkedList<String>();
        
        //connection for jdbc
        Connection c = null;
        try{
            //driver jdbc6.jar
            Class.forName(driver).newInstance();
            
            //getting connection
            if(Launcher.tns_entry.equalsIgnoreCase("()")){
                c = DriverManager.getConnection("jdbc:oracle:thin:@"+Launcher.hostname+":"+Launcher.port+":"+Launcher.database,Launcher.username,Launcher.password);
            }
            else{
                c = DriverManager.getConnection("jdbc:oracle:thin:@"+Launcher.tns_entry,Launcher.username,Launcher.password);
            }
            //sql statemnt to execute query
            Statement s = c.createStatement();
            
            //for query result
            ResultSet r = s.executeQuery("SELECT BAN,cycle_code,cycle_year,cycle_month,BILL_SEQ_NO FROM DND_BAN_LIST_FOR_VALIDATION WHERE LOWER(STATUS)='new' and cycle_code="+cc+" and cycle_year="+cy+" AND cycle_month="+cm);
            
            //Iteration
            while(r.next()){
                
                //column BN value
                bans.add(r.getString("BAN")+"_"+r.getString("cycle_code")+"_"+r.getString("cycle_year")+"_"+r.getString("cycle_month")+"_"+r.getString("BILL_SEQ_NO"));
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException  ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally{
            try {
                c.close();
            } catch (SQLException ex) {
                Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        //RETURN LIST 
        return bans;
    }
    
    //Updating Status in DB
    void mark(String BAN, String Status, String cc, String cy, String cm){
        
        this.mark(BAN, Status, "STATUS", cc, cy, cm);
    }
    
    void mark(String BAN, String Status, String Ops, String cc, String cy, String cm){
        
        //DB Connection
        Connection c = null;
        
        try{
            //driver jdbc6.jar
            Class.forName(driver).newInstance();
            
            //getting connection
            c = DriverManager.getConnection("jdbc:oracle:thin:@"+Launcher.hostname+":"+Launcher.port+":"+Launcher.database,Launcher.username,Launcher.password);
            
            //sql statemnt to execute update query
            PreparedStatement s = c.prepareStatement("UPDATE DND_BAN_LIST_FOR_VALIDATION SET "+Ops+"=? WHERE BAN=? and cycle_code="+cc+" and cycle_year="+cy+" AND cycle_month="+cm);
            
            //BAN
            s.setString(2, BAN);
            
            //Status
            s.setString(1, Status);
            
            //Executing Update
            s.executeUpdate();

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException  ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally{
            try {
                //Commiting change
                c.commit();
            } catch (SQLException ex) {
                //Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                //closing connection
                c.close();
            } catch (SQLException ex) {
                Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    //Get Query for BAN
    ArrayList<String> getQuery(String BAN,String CC,String CY,String CM) {
        
        //DATABASE 
        Connection c = null;
        
        //Store query
        ArrayList<String> q = new ArrayList<String>();
        
        try{
            //driver jdbc6.jar
            Class.forName(driver).newInstance();
            
            //getting connection
            c = DriverManager.getConnection("jdbc:oracle:thin:@"+Launcher.hostname+":"+Launcher.port+":"+Launcher.database,Launcher.username,Launcher.password);
            
            String bill=getBillFormat(BAN,CC,CY,CM);
            
            //If no format specified
            if(bill==null){
                Launcher.log(" :: BAN : "+BAN+" is not having any bill format.");
            }
            
            //Getting RULES FROM TABLE
            PreparedStatement s = c.prepareStatement("SELECT * FROM DND_BAN_VALIDATION_RULES WHERE FORMAT=? ORDER BY SEQ_NO ASC");
            
            //set BILL FORMAT
            s.setString(1, bill);
            
            //Getting data
            ResultSet r= s.executeQuery();
            
            while(r.next()){
                StringBuffer line = new StringBuffer();
                line.append(r.getInt("SEQ_NO"));
                line.append("|||");
                line.append(r.getString("FORMAT"));
                line.append("|||");
                line.append(r.getString("RULE"));
                line.append("|||");
                line.append(r.getString("QUERY"));
                line.append("|||");
                line.append(r.getString("IS_QUERY"));
                line.append("|||");
                line.append(r.getString("TAG"));
                line.append("|||");
                line.append(r.getString("OFFSET"));
                line.append("|||");
                line.append(r.getInt("LENGTH"));
                line.append("|||");
                line.append(r.getString("SKIP"));
                line.append("|||");
                line.append(r.getString("END_TAG"));
                q.add(line.toString());
            }
            
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException  ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally{
            try {
                //closing connection
                c.close();
            } catch (SQLException ex) {
                Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return q;
    }
    
    //Get Bill Format for BAN
    String getBillFormat(String BAN,String CC,String CY,String CM){
        //return value
        String bill_format=null;
        
        //DATABASE 
        Connection c = null;
        
        try{
            //driver jdbc6.jar
            Class.forName(driver).newInstance();
            
            //getting connection
            c = DriverManager.getConnection("jdbc:oracle:thin:@"+Launcher.hostname+":"+Launcher.port+":"+Launcher.database,Launcher.username,Launcher.password);
            
            //sql statemnt to execute update query
            PreparedStatement s = c.prepareStatement("SELECT BILL_FORMAT FROM DND_BAN_LIST_FOR_VALIDATION WHERE BAN=? AND cycle_code=? AND cycle_month=? AND cycle_year=?");
            
            //BAN
            s.setString(1, BAN);
            s.setString(2, CC);
            s.setString(3, CM);
            s.setString(4, CY);
            
            //Execute Select
            ResultSet r = s.executeQuery();
            
            if(r.next()){
                bill_format = r.getString("BILL_FORMAT");
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException  ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally{
            try {
                //closing connection
                c.close();
            } catch (SQLException ex) {
                Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return bill_format;
    }
    
    //Get Text from Rule Query
    String getRuleText(String Query, String BAN, String CC, String CY, String CM, String BSN, String Type){
        String x = null;
        
        //DATABASE 
        Connection c = null;
        
        try{
            //driver jdbc6.jar
            Class.forName(driver).newInstance();
            
            //getting connection
            c = DriverManager.getConnection("jdbc:oracle:thin:@"+Launcher.hostname+":"+Launcher.port+":"+Launcher.database,Launcher.username,Launcher.password);

            // Search and replace
            Query = Query.replaceAll("\\$INPUT_BAN", BAN);
            Query = Query.replaceAll("\\$INPUT_CC", CC);
            Query = Query.replaceAll("\\$INPUT_CY", CY);
            Query = Query.replaceAll("\\$INPUT_CM", CM);
            Query = Query.replaceAll("\\$INPUT_BSN", BSN);

            Type = Type.replaceAll("\\s", "");
            
            if(Type.equalsIgnoreCase("query")){

                //sql statemnt to execute update query
                Statement s = c.createStatement();

                //Execute Select
                //System.err.println(Query);
                ResultSet r = s.executeQuery(Query);
                ResultSetMetaData m = r.getMetaData();
                int nCols = m.getColumnCount();
                x="";
                while(r.next()){
                    for (int i=1; i <= nCols; i++ ){
                        x = x + r.getString(i);                        
                        if(i!=nCols){
                            x = x + "~";
                        }
                    }
                    x = x + "\n";
                }
                x.replaceFirst("\n$", "");
                if(x.length()==0) x=null;
            }
            else if(Type.equalsIgnoreCase("Function")){
                CallableStatement s = c.prepareCall("{ ? = call "+Query+"}");
                s.registerOutParameter(1, Types.VARCHAR);
                s.executeUpdate();
                x = s.getString(1);
            }
            
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException  ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally{
            try {
                //closing connection
                c.close();
            } catch (SQLException ex) {
                Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return x;
    }
    
        //give all rules for table parsing
    HashMap<String, TableDefinition> getTableDefinitions(){
            
            HashMap<String, TableDefinition> tableDefinitions = new HashMap<String, TableDefinition>();
            
            //connection for jdbc
            Connection c = null;
            try{
                //driver jdbc6.jar
                Class.forName(driver).newInstance();
                
                //getting connection
                c = DriverManager.getConnection("jdbc:oracle:thin:@"+Launcher.hostname+":"+Launcher.port+":"+Launcher.database,Launcher.username,Launcher.password);
                
                //sql statemnt to execute query
                Statement s = c.createStatement();
                
                //for query result
                ResultSet r = s.executeQuery("SELECT * FROM DND_TABLE_DEFINITION");
                //Iteration
                while(r.next()){
                    TableDefinition table = new TableDefinition();	
                    String id = r.getString("ID");
                    table.setId(r.getString("ID"));
                    table.setStart(r.getString("START_TABLE")+"");
                    ArrayList<String> thingsToIgnore = new ArrayList<String>();
                    String ignoreLine = r.getString("IGNORE");
                    if (ignoreLine != null){
                        String[] value_split = ignoreLine.split("\\|");
                        Collections.addAll(thingsToIgnore, value_split); 
                        table.setThingsToIgnore(thingsToIgnore);
                    }                	
                    table.setCheckSumItem(r.getString("CHECK_SUM_ITEM"));
                    table.setCyclic(r.getInt("CYCLIC"));
                    table.setCycleEnd(r.getString("CYCLE_END"));
                    table.setIgnoreStart(r.getString("IGNORE_START"));
                    table.setIgnoreStart(r.getString("IGNORE_END"));
                    tableDefinitions.put(id, table);
                    
                    ArrayList<String> tags = new ArrayList<String>();
                    String tagsLine = r.getString("TAGS");
                    if (tagsLine != null){
                        String[] value_split = tagsLine.split("\\|");
                        Collections.addAll(tags, value_split); 
                        table.setTags(tags);
                    }  
                    ArrayList<String> reverseEnds = new ArrayList<String>();
                    String reverseEndsLine = r.getString("REVERSE_TABLES_ENDS");
                    if (reverseEndsLine != null){
                        String[] value_split = reverseEndsLine.split("\\|");
                        Collections.addAll(reverseEnds, value_split); 
                        table.setReverseEnds(reverseEnds);
                    }
                }
            } catch (Exception ex) {
                Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
            }
            finally{
                try {
                    c.close();
                } catch (SQLException ex) {
                    Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
            //RETURN table definition from DB 
            return tableDefinitions;
    }

    ArrayList<String> getTableEntry(String BAN,String CC,String CM,String CY) {
        ArrayList<String> table = new ArrayList<String>();
        //connection for jdbc
        Connection c = null;
        String bill=getBillFormat(BAN,CC,CY,CM);
        if(bill==null){
            Launcher.log(" :: BAN : "+BAN+" is not having any bill format.");
        }
        else {
            try{
                //driver jdbc6.jar
                Class.forName(driver).newInstance();

                //getting connection
                c = DriverManager.getConnection("jdbc:oracle:thin:@"+Launcher.hostname+":"+Launcher.port+":"+Launcher.database,Launcher.username,Launcher.password);

                //sql statemnt to execute query
                Statement s = c.createStatement();

                //for query result
                ResultSet r = s.executeQuery("SELECT ID FROM DND_TABLE_DEFINITION WHERE BILL_FORMAT='"+bill+"'");

                //Iteration
                while(r.next()){                
                    //column BN value
                    table.add(r.getString("ID"));
                }
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InstantiationException  ex) {
                Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalAccessException ex) {
                Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
            }
            finally{
                try {
                    c.close();
                } catch (SQLException ex) {
                    Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        //RETURN LIST 
        return table;
    }

    void insertStatusLog(String BAN, String CC, String CM, String CY, String BSN, String Rule, String html, String db, boolean matched) {
        //DATABASE 
        Connection c = null;
        
        try{
            //driver jdbc6.jar
            Class.forName(driver).newInstance();
            
            //getting connection
            c = DriverManager.getConnection("jdbc:oracle:thin:@"+Launcher.hostname+":"+Launcher.port+":"+Launcher.database,Launcher.username,Launcher.password);
            
            //sql statemnt to execute insert query
            PreparedStatement s = c.prepareStatement("INSERT INTO DND_RULE_VALIDATION_STATUS(TIME_LOG,BAN,cycle_code,cycle_year,cycle_month,BILL_SEQ_NO,RULE_ID,HTML_DATA,RULE_DATA,STATUS) VALUES(?,?,?,?,?,?,?,?,?,?)"); //count and check if all is matched
            s.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
            s.setString(2, BAN);
            s.setString(3, CC);
            s.setString(4, CY);
            s.setString(5, CM);
            s.setString(6, BSN);
            s.setString(7, Rule);
            s.setString(8, html);
            s.setString(9, db);
            s.setString(10, (matched)?"Matched":"Failed");
            try{
                int n = s.executeUpdate();
                
                try {
                    //Commiting change
                    c.commit();
                } catch (SQLException ex) {
                    //Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            catch(Exception e){
                Launcher.log(BAN + " Unable to LOG "+Rule);
            }
            s.close();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException  ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally{
            try {
                //closing connection
                c.close();
            } catch (SQLException ex) {
                Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    ArrayList<String> getRegex(String BAN, String CC, String CY, String CM) {
        //return
        ArrayList<String> regex = new ArrayList<String>();
        //DATABASE 
        Connection c = null;
        //bill format
        String Bill_Format = getBillFormat(BAN,CC,CY,CM);
        if(Bill_Format==null){
            Launcher.log(" :: BAN : "+BAN+" is not having any bill format.");
        }
        else {
            try{
                //driver jdbc6.jar
                Class.forName(driver).newInstance();

                //getting connection
                c = DriverManager.getConnection("jdbc:oracle:thin:@"+Launcher.hostname+":"+Launcher.port+":"+Launcher.database,Launcher.username,Launcher.password);

                //sql statemnt to execute query
                Statement s = c.createStatement();

                //for query result
                ResultSet r = s.executeQuery("SELECT REGEXID, REGEX FROM DND_REGEX_DEFINITION WHERE BILL_FORMAT='"+Bill_Format+"'");

                //Iteration
                while(r.next()){                
                    //column BN value
                    regex.add(r.getString("REGEXID")+"~~~~~~"+r.getString("REGEX"));
                }
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InstantiationException  ex) {
                Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalAccessException ex) {
                Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
            }
            finally{
                try {
                    c.close();
                } catch (SQLException ex) {
                    Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return regex;
    }

    ArrayList<String> getPdfRule(String BAN, String CC, String CY, String CM) {
        ArrayList<String> rules = new ArrayList<String>();
        //DATABASE 
        Connection c = null;
        //bill format
        String Bill_Format = getBillFormat(BAN,CC,CY,CM);
        if(Bill_Format==null){
            Launcher.log(" :: BAN : "+BAN+" is not having any bill format.");
        }
        else {
            try{
                //driver jdbc6.jar
                Class.forName(driver).newInstance();

                //getting connection
                c = DriverManager.getConnection("jdbc:oracle:thin:@"+Launcher.hostname+":"+Launcher.port+":"+Launcher.database,Launcher.username,Launcher.password);

                //sql statemnt to execute query
                Statement s = c.createStatement();

                //for query result
                ResultSet r = s.executeQuery("SELECT RULE_ID,TAG1,OFFSET1,SKIP1,REGEX1,OPS1,TAG2,OFFSET2,SKIP2,REGEX2,OPS2 FROM DND_TEXT_CHECK_RULE WHERE FORMAT='"+Bill_Format+"'");

                //Iteration
                while(r.next()){                
                    rules.add(r.getString("RULE_ID") +"|||" +r.getString("TAG1") +"|||" +r.getString("OFFSET1") +"|||" +r.getString("SKIP1") +"|||" +r.getString("REGEX1") +"|||" +r.getString("OPS1") +"|||" +r.getString("TAG2") +"|||" +r.getString("OFFSET2") +"|||" +r.getString("SKIP2") +"|||" +r.getString("REGEX2") +"|||" +r.getString("OPS2"));
                }
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InstantiationException  ex) {
                Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalAccessException ex) {
                Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
            }
            finally{
                try {
                    c.close();
                } catch (SQLException ex) {
                    Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return rules;
    }
}
