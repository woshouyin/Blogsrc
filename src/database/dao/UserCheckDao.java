package database.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import database.connectionPool.connections.PooledConnection;

/**
 * 用于检查用户和密码是否允许登录
 */
public class UserCheckDao extends AbsDao{

	/**
	 * @param conn
	 * 数据库连接
	 */
	public UserCheckDao(PooledConnection conn) {
		super(conn);
	}
	
	/**
	 * 检查
	 * @param id
	 * 用户ID
	 * @param password
	 * 密码
	 */
	public boolean check(long id, String password) throws SQLException {
		String sql = "SELECT `id`, `password` FROM `users` WHERE `id` = ? AND `password` = PASSWORD(?)";
		PreparedStatement psta = connection.prepareStatement(sql);
		psta.setLong(1, id);
		psta.setString(2, password);
		ResultSet rs = psta.executeQuery();
		if(rs.next()) {
			rs.close();
			psta.close();
			return true;
		}else {
			rs.close();
			psta.close();
			return false;
		}
	}
	
}
