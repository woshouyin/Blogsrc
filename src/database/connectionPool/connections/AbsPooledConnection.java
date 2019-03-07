package database.connectionPool.connections;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import database.connectionPool.ConnectionPool;

/**
 * 抽象的池化连接，泛型用于确定应当被哪个连接池利用，封装一个Connection
 */
abstract class AbsPooledConnection<T extends ConnectionPool> implements PooledConnection{
	protected Connection connection;
	protected T connPool;
	
	/**
	 * @param conn
	 * 实际的连接
	 * @param connPool
	 * 连接处于的连接池
	 */
	AbsPooledConnection(Connection conn, T connPool){
		this.connection = conn;
		this.connPool = connPool;
	}

	@Override
	public PreparedStatement prepareStatement(String sql) throws SQLException {
		return connection.prepareStatement(sql);
	}

	@Override
	public boolean getAutoCommit() throws SQLException {
		return connection.getAutoCommit();
	}
	
	@Override
	public void setAutoCommit(boolean arg0) throws SQLException {
		connection.setAutoCommit(arg0);	
	}

	@Override
	public void commit() throws SQLException {
		connection.commit();
	}

	@Override
	public void rollBack() throws SQLException {
		connection.rollback();
	}
	
	@Override
	public void putBack() throws SQLException {
		if(!connection.getAutoCommit()) {
			connection.setAutoCommit(true);
		}
		connPool.put(this);
	}

	@Override
	public void close() throws SQLException{
		connection.close();
	}
	
	@Override
	public boolean isClosed() throws SQLException {
		return connection.isClosed();
	}
	
	@Override
	public boolean belongOf(ConnectionPool connPool) {
		return this.connPool == connPool;
	}
	
}
