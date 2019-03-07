package database.connectionPool.connections;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import database.connectionPool.ConnectionPool;

/**
 * 被放入连接池中的连接 
 */
public interface PooledConnection {
	
	public PreparedStatement prepareStatement(String sql) throws SQLException;

	void setAutoCommit(boolean arg0) throws SQLException;

	void commit() throws SQLException;

	void rollBack() throws SQLException;

	boolean getAutoCommit() throws SQLException;
	
	/**
	 * 放回到连接池中
	 * @throws SQLException
	 */
	public void putBack() throws SQLException;
	
	/**
	 * 关闭连接
	 * @throws SQLException
	 */
	public void close() throws SQLException;
	
	/**
	 * @return
	 * 连接关闭返回true，否则返回false
	 */
	public boolean isClosed() throws SQLException;
	
	/**
	 * 检查该连接是否属于某个连接池
	 * @param connPool
	 * 检查的连接池
	 * @return
	 * 属于为true，否则为false
	 */
	public boolean belongOf(ConnectionPool connPool);

}
