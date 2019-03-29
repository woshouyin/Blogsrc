package database.connectionPool;

import java.sql.SQLException;

import database.connectionPool.connections.PooledConnection;

/**
 * 数据库连接池接口,实现了该接口的类应保证线程安全。
 */
public interface ConnectionPool extends AutoCloseable{
	/**
	 * 用于初始化数据库连接有关的变量。
	 * @param driver
	 * 数据库驱动
	 * @param url
	 * 数据库URL
	 * @param user
	 * 数据库user
	 * @param password
	 * 数据库密码
	 */
	public void init(String driver, String url, String user, String password);
	
	/**
	 * 启动数据库连接池，开始连接数据库。必须验证已给出数据库是否能够连接。
	 * @return
	 * 是否成功启动
	 * @throws SQLException 
	 * 连接发生错误则抛出此异常
	 * @throws ClassNotFoundException 
	 * 若无法加载驱动则抛出此异常
	 */
	public void start() throws SQLException, ClassNotFoundException;
	
	/**
	 * 重置当前数据库连接池状态。
	 * @return
	 * 成功返回true，否则返回false。
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	public boolean reset() throws SQLException, ClassNotFoundException;
	
	/**
	 * 用于获取一条数据库连接。
	 * @return
	 * 若成功取出，则返回获取的连接。
	 * @throws SQLException
	 * 若无法取出，则抛出异常。 
	 */
	public PooledConnection get() throws SQLException;
	
	/**
	 * 用于放回一条数据库连接。
	 * @param connection
	 * 需要放回的连接
	 * @throws SQLException 
	 * 若无法放回，则抛出异常
	 */
	public void put(PooledConnection connection) throws SQLException;
	
	/**
	 * 若start成功且未close或出错，则应为正在工作
	 * @return
	 * 正在工作则为true，否则为false
	 */
	public boolean isWorking();
	
	
	/**
	 * 若init成功则应返回true
	 * @return
	 * 已初始化则为true，否则为false 
	 */
	public boolean isInited();
	
	/**
	 * 关闭当前数据库连接池，释放所有连接。
	 * @return
	 * 成功关闭返回true，否则返回false。
	 * @throws SQLException 
	 */
	public void close() throws SQLException;
	
}
