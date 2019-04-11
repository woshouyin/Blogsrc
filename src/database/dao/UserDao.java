package database.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.sql.Statement;
import java.util.HashMap;
import database.connectionPool.connections.PooledConnection;
import database.gas.RegisterUserGas;
import database.gas.UserGas;

/**
 * 用于执行与用户相关的数据库操作 
 */
public class UserDao extends AbsDao{
	
	/**
	 * @param conn
	 * 数据库连接
	 */
	public UserDao(PooledConnection conn) {
		super(conn);
	}
	
	/**
	 * 获取一个用户的信息，以map形式返回, 该功能已写入抽象类AbsDao.selectAllFromTableById(String table, long id)
	 * @param id
	 * 用户ID
	 * @return
	 * key为字段名，value为字段值
	 * @deprecated
	 */
	HashMap<String, Object> getUser(long id) throws SQLException {
		String sql = "SELECT * FROM `users` WHERE `id` = ?;";
		PreparedStatement stat = connection.prepareStatement(sql);
		stat.setLong(1, id);
		ResultSet rs = stat.executeQuery();
		if(rs.next()) {
			ResultSetMetaData rsmd = rs.getMetaData();
			HashMap<String, Object> map = new HashMap<String, Object>();
			int col = rsmd.getColumnCount();
			for(int i = 1; i <= col; i++) {
				map.put(rsmd.getColumnName(i), rs.getObject(i));
			}
			return map;
		}else {
			return null;
		}
	}
	
	public void addUser(HashMap<String, Object> user) {
		
	}
	
	/**
	 * 通过用户ID从数据库众获取一个用户对应的UserGas
	 * @param id
	 * 用户ID
	 * @return
	 * 对应的UserGas
	 */
	public UserGas getUserGas(long id) throws SQLException {
		UserGas user = new UserGas(id);
		HashMap<String, Object> map = selectAllFromTableById("users", id);
		if (map != null) {
			user.setName((String) map.get("name"));
			user.setRegDate((Date)map.get("reg_date"));
			user.setPrivilege((int) map.get("privilege"));
			user.setEmail((String)map.get("E-mail"));
			return user;
		}else {
			return null;
		}
	}
	
	/**
	 * 注册用户
	 * @param rug
	 * 用户信息
	 */
	public void registerUser(RegisterUserGas rug) throws SQLException{
		Savepoint sp = connection.setSavepoint();
		
		//String sql = "INSERT INTO `users` (`name`, `reg_date`, `privilege`, `E-mail`) VALUES(?,?,?,?)";
		//String sqlCk = "INSERT INTO `user_check` (`name`, `password`) VALUES (?, MD5(?))";
		String sql = "call add_user(?,?,?,?,?)";
		
		PreparedStatement ups = connection.prepareStatement(sql);
		ups.setString(1, rug.getName());
		ups.setDate(2, rug.getRegDate());
		ups.setInt(3, rug.getPrivilege());
		ups.setString(4, rug.getEmail());
		ups.setString(5, rug.getPassword());
		ups.execute();
	
	}
}










