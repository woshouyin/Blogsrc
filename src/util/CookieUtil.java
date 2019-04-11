package util;

import java.sql.SQLException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import database.DatabaseManager;
import database.dao.UserCheckDao;
import database.dao.UserDao;
import database.gas.UserGas;
import log.LogUtil;

/**
 * 用于解析Cookie的工具类
 */
public class CookieUtil {
	private Cookie[] cookies;
	
	public CookieUtil(Cookie[] cookies) {
		this.cookies = cookies;
	}
	
	/**
	 * 从请求中获取Cookies
	 */
	public CookieUtil(HttpServletRequest request) {
		this.cookies = request.getCookies();
	}
	
	
	/**
	 * 通过Cookies中的token获取用户数据
	 * @param dm
	 * 数据库管理者对象
	 */
	public UserGas getUserByTokenCookie(DatabaseManager dm) {
		String token = null;
		if(cookies == null){
			return null;
		}
		
		for(Cookie cookie : cookies){
			if(cookie.getName().equals("tk")){
				token = cookie.getValue();
			}
		}
		
		if (token == null){
			return null;
		}
		long id = -1;
	 	try (UserCheckDao ucd = dm.getDao(UserCheckDao.class)){
		 	id = ucd.checkByToken(token);
			if (id == -1){
				return null;
			}
	 	} catch(SQLException e){
	 		e.printStackTrace();
	 	}
		
	 	UserGas ug = null;
	 	try(UserDao ud = dm.getDao(UserDao.class)){
	 		ug = ud.getUserGas(id);
	 		if(ug == null && id != -1){
	 			LogUtil.warning("users表与user_check表未同步");
	 		}
	 	} catch(SQLException e){
	 		e.printStackTrace();
	 	}
		return ug;
	}
	
}
