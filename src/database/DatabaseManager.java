package database;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import database.connectionPool.ConnectionPool;
import database.connectionPool.TimeOutConnectionPool;
import database.connectionPool.connections.PooledConnection;
import database.connectionPool.params.ConnPoolParams;
import database.connectionPool.params.TimeOutConnPoolParams;
import database.dao.AbsDao;

/**
 * 数据库管理者类
 * 负责对数据库层进行综合管理。
 */
public class DatabaseManager {
	private ConnectionPool connPool;
	
	/**
	 * 创建管理者
	 * @param poolName
	 * 数据库连接池的类型名
	 * @param params
	 * 数据库连接池参数
	 */
	public DatabaseManager(String poolName, ConnPoolParams params) {
		if(poolName.equals("TimeOutConnectionPool")) {
			try {
				connPool = TimeOutConnectionPool.getInitedConnPool((TimeOutConnPoolParams) params);
				connPool.start();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}else {
			throw new IllegalArgumentException("Not a avilable pool name");
		}
	}
	
	/**
	 * 获取一个Dao
	 * @param cls
	 * Dao的Class对象
	 * @throws SQLException 
	 */
	public <T extends AbsDao> T getDao(Class<T> cls) throws SQLException {
		T dao = null;
		try {
			dao = cls.getConstructor(PooledConnection.class).newInstance(connPool.get());
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
		if(dao != null) {
			return dao;
		}else {
			throw new SQLException("Can not get the dao instance");
		}
	}
}
