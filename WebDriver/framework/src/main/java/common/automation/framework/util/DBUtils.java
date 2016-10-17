package common.automation.framework.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

import common.automation.framework.config.AutomationConfig;

public class DBUtils {
	private static Connection conn = null;
	private static Statement stmt = null;
	private static ResultSet resultSet = null;
	
	public static ResultSet executeQuery(String filePath, Map<String, String> varsToReplace){
		try {
			Class.forName(AutomationConfig.getProperty("automation.dBDriver"));
			conn = DriverManager.getConnection(AutomationConfig.getDBConString());
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            
			String query = FileHelper.readFileToString(filePath, " ").replace(";", "").trim();
			
			for(Map.Entry<String, String> entry : varsToReplace.entrySet()){
				query = query.replace(entry.getKey(), entry.getValue());
			}
			
			resultSet = stmt.executeQuery(query);
		} catch (Exception e) {
			e.printStackTrace();
			try {
				conn.close();
			} catch (SQLException sql) {
				sql.printStackTrace();
			}
		}
		return resultSet;
	}
	
	public static void closeConnection(){
		try {
			stmt.close();
			conn.close();
		} catch (SQLException sql) {
			sql.printStackTrace();
		}
	}
}
