package demo;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class parser
{
    public static void main(String args[])
    {
        JSONParser parser = new JSONParser();
        try
        {
            Object object = parser
                    .parse(new FileReader("D:/Bineet_Resume.json"));
            
            //convert Object to JSONObject
            JSONObject jo1 = (JSONObject)object;
            JSONObject jo2 = (JSONObject)jo1.get("Resume");
           
            JSONObject jo4 = (JSONObject)jo2.get(  "StructuredXMLResume");
            JSONObject jo5 = (JSONObject)jo4.get(  "ContactInfo");
            JSONObject jo6 = (JSONObject)jo5.get(  "PersonName");
            for (Object key : jo6.keySet()){
            	String keyStr = (String)key;
            	Object keyvalue = jo6.get(keyStr);
            	System.out.println(keyStr);
            	System.out.println(keyvalue);
            }
        	   
           
           JSONArray ja1=(JSONArray)jo5.get("ContactMethod");
           for(int i=0;i<ja1.size();i++){
        	   JSONObject jo7=(JSONObject)ja1.get(i);
        	   incrementValue((JSONObject) jo7, ja1);       	  
           }
          JSONObject jo7=(JSONObject)jo4.get("EmploymentHistory");
          JSONArray ja2=(JSONArray)jo7.get("EmployerOrg");
          for(int i1=0;i1<ja2.size();i1++){
       	   JSONObject jo8=(JSONObject)ja2.get(i1);
       	   incrementValue((JSONObject) jo8, ja2);       	  
          }
          
           
    
        
        }
        
        catch(FileNotFoundException fe)
        {
            fe.printStackTrace();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

	
	private static void incrementValue(JSONObject obj, List<String> keysToIncrementValue) {
		@SuppressWarnings("unchecked")
		Set<String> keys = obj.keySet();
        for (String key : keys) {
            Object ob = obj.get(key);
            String keyStr = (String)key;
           
        	System.out.println(keyStr);
            
            if (!(ob instanceof JSONObject)&&!(ob instanceof JSONArray)) {
            
            	 System.out.println(ob);              
            }
           if (ob instanceof JSONObject) {
                incrementValue((JSONObject) ob, keysToIncrementValue);
           }
            
            else if (ob instanceof JSONArray) {
                JSONArray arr = (JSONArray) ob;              
              
                for (int i=0; i < arr.size(); i++) {
                    Object arrObj = arr.get(i);
                    if (arrObj instanceof JSONObject) {
                        incrementValue((JSONObject) arrObj, keysToIncrementValue);
                       
                    }
                }
            }
        }
        
		
      
		
	}

	

	
}