package database.dao;

import java.sql.SQLException;

import database.connectionPool.connections.PooledConnection;

/**
 * 用于与数据库进行交互，控制一条数据库连接
 */
public abstract class AbsDao implements AutoCloseable{
	protected PooledConnection connection;
	
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
	
}
