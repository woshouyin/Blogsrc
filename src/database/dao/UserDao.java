package database.dao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;

import org.apache.xmlbeans.impl.jam.mutable.MPackage;

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
			user.setEmail((String)map.get("e-mail"));
			return user;
		}else {
			return null;
		}
	}
	
	public void registerUser(RegisterUserGas rug) throws SQLException{
		connection.setAutoCommit(false);
		
		
		String sql = "INSERT INTO `USERS` (`id`, `name`, `reg_date`, `privilege`, `password`, `E-mail`) VALUES(?,?,?,?,MD5(?),?)";
		PreparedStatement ups = connection.prepareStatement(sql);
		ups.setLong(1, rug.getId());
		ups.setString(2, rug.getName());
		ups.setDate(3, rug.getRegDate());
		ups.setInt(4, rug.getPrivilege());
		ups.setString(5, rug.getPassword());
		ups.setString(6, rug.getEmail());
		try {
			ups.execute();
			connection.commit();
		} catch (SQLException e) {
			connection.rollBack();
			throw e;
		}
	
	}
}










