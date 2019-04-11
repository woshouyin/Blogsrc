package database.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import database.connectionPool.connections.PooledConnection;

/**
 * 用于与数据库进行交互，控制一条数据库连接
 */
public abstract class AbsDao implements AutoCloseable{
	protected PooledConnection connection;
	protected static final int ID_COUNT_USER = 0;
	protected static final int ID_COUNT_ARTICLE = 1;
	protected static final int ID_COUNT_CMT = 2;
	
	/**
	 * @param conn
	 * 数据库连接
	 */
	public AbsDao(PooledConnection conn){
		connection = conn;
	}
	
	
	/**
	 * 将数据库连接交还给连接池
	 */
	public void close() throws SQLException{
		connection.putBack();
	}
	
	
	/**
	 * 从表中查询所有项
	 * @param table
	 * 表名
	 * @param id
	 * 表中的id(列名必须为"id")
	 * 
	 */
	protected HashMap<String, Object> selectAllFromTableById(String table, long id) throws SQLException {
		String sql = "SELECT * FROM `" + table + "` WHERE `id` = ?;";
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
	
}
