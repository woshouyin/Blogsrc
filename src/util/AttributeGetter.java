package util;

import javax.servlet.http.HttpServletRequest;

import config.AMSConfig;
import database.DatabaseManager;

/**
 *	偷懒用工具类 
 */
public class AttributeGetter {
	public static DatabaseManager getDatabaseManager(HttpServletRequest request) {
		return (DatabaseManager) request.getServletContext().getAttribute("DatabaseManager");
	}
	
	public static AMSConfig getAMSConfig(HttpServletRequest request) {
		return (AMSConfig) request.getServletContext().getAttribute("AMSConfig");
	}

}
