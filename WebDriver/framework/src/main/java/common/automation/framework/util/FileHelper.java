package common.automation.framework.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class FileHelper {
	
	public static String readFileToString(String filePath, String lineDelimiter){
		String s = new String();  
        StringBuffer sb = new StringBuffer();  
        
        try{
			FileReader fr = new FileReader(new File(filePath));  
	        BufferedReader br = new BufferedReader(fr);  
	
	        while((s = br.readLine()) != null)  
	        {  
	            sb.append(s + lineDelimiter);  
	        }  
	        br.close(); 
        }
        catch(Exception e){
        	e.printStackTrace();
        }
        
		String fileContent = sb.toString();
		return fileContent;
	}
}
