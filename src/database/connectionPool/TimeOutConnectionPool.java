package database.connectionPool;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayDeque;
import java.util.HashSet;

import database.connectionPool.connections.PooledConnection;
import database.connectionPool.connections.TimeOutPooledConnection;
import database.connectionPool.params.TimeOutConnPoolParams;
import log.LogUtil;
import util.URLBuilder;

/**
 * 基于双向队列的数据库池类。
 * 初始化时，连接initNum个数据库连接，最多维持maxActiveNum个活动连接。
 * 放回连接时，若等待连接池中的连接数超过maxNum或minNum时，自动断开多余连接，
 * 若等待池中有超时连接则断开。
 * 获取连接时，若连接池空，则添加minNum + 1个连接。
 * 每次连接的尝试次数为tryTime。
 */
public class TimeOutConnectionPool implements ConnectionPool{
	private boolean working;
	private boolean inited;
	private ArrayDeque<TimeOutPooledConnection> waittingConns;
	private HashSet<TimeOutPooledConnection> activeConns;
	private String driver;
	private String url;
	private String user;
	private String password;
	private int maxNum;
	private int minNum;
	private int initNum;
	private int tryTime;
	private int timeOut;
	private int maxActiveNum;
	
	/**
	 * 构建一个双向队列数据库连接池
	 * @param maxNum
	 * 最大等待连接数
	 * @param minNum
	 * 最小等待连接数
	 * @param initNum
	 * 初始等待连接数
	 * @param maxActiveNum
	 * 最大活跃连接数
	 * @param tryTime
	 * 连接失败后的尝试次数
	 * @param timeOut
	 * 等待中连接的超时时间
	 */
	public TimeOutConnectionPool(int maxNum, int minNum, int initNum,int maxActiveNum, int tryTime, int timeOut) {
		this.maxNum = maxNum;
		this.minNum = minNum;
		this.initNum = initNum;
		this.tryTime = tryTime;
		this.timeOut = timeOut;
		this.maxActiveNum = maxActiveNum;
		this.working = false;
		this.inited = false;
		waittingConns = new ArrayDeque<TimeOutPooledConnection>();
		activeConns = new HashSet<TimeOutPooledConnection>();
	}
	
	/**
	 * 检查等待池中的连接数量，关闭超时连接。
	 */
	private void check() throws SQLException {
		//LogUtil.info("Check Start\n" + this);
		while(!waittingConns.isEmpty() 
				&& waittingConns.getFirst().isTimeOut()) {
			LogUtil.info(Boolean.toString(waittingConns.getFirst().isTimeOut()));
			waittingConns.removeFirst().close();
		}
		
		if(waittingConns.size() > maxNum) {
			for(int i = 0; i < waittingConns.size() - maxNum; i++) {
				waittingConns.removeFirst().close();
			}
		}else if(waittingConns.size() < minNum) {
			for(int i = 0; i < minNum - waittingConns.size(); i++) {
				connect();
			}
		}
		//LogUtil.info("Check End\n" + this);
	}
	
	/**
	 * 创建一个数据库连接并放入等待池末尾，最多尝试tryTime次
	 */
	private void connect() throws SQLException {
		Connection conn;
		TimeOutPooledConnection tconn;
		int time = tryTime;
		while(true) {
			try {
				conn = DriverManager.getConnection(url, user, password);
				conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
				tconn= new TimeOutPooledConnection(conn, this);
				waittingConns.add(tconn);
				break;
			} catch (SQLException e) {
				LogUtil.severe("Faild to connect with database!");
				if(time != 0) {
					LogUtil.info("Retrying.....");
					time--;
				}else {
					throw e;
				}
			}
		}
	}
	
	
	@Override
	public void init(String driver, String url, String user, String password) {
		this.driver = driver;
		this.url = url;
		this.user = user;
		this.password = password;
		this.inited = true;
	}

	
	@Override
	public synchronized void start() throws SQLException, ClassNotFoundException {
		if(!isWorking()) {
			if(isInited()) {
				Class.forName(driver);
				for(int i = 0; i < initNum; i++) {
					connect();
				}
				if(waittingConns.size() != 0) {
					working = true;
				}else {
					LogUtil.severe("Failed to start up the TimeOutConnectionPool");
				}
			}else {
				throw new SQLException("The ConnectionPool never inited");
			}
		}else {
			LogUtil.warning("The pool is already working");
		}
	}

	@Override
	public synchronized void close() throws SQLException {
		for(TimeOutPooledConnection tc : waittingConns) {
			tc.close();
		}
		for(TimeOutPooledConnection tc : activeConns) {
			tc.close();
		}
		working = false;
	}

	/**
	 * 还未实现
	 */
	@Override
	public boolean reset() throws SQLException, ClassNotFoundException {
		return false;
	}

	/**
	 * 获取一个链接,在获取前检查该池。
	 */
	@Override
	public synchronized PooledConnection get() throws SQLException {
		if(isWorking()) {
			check();
			/*
			if(waittingConns.isEmpty()) {
				for(int i = 0; i < minNum + 1; i++) {
					connect();
				}
			}
			*/
			if(maxActiveNum == 0 || activeConns.size() < maxActiveNum) {
				TimeOutPooledConnection tc = waittingConns.removeFirst();
				if(tc == null || tc.isClosed()) {
					throw new SQLException("Cannot get an active connection");
				}else {
					activeConns.add(tc);
					return tc;
				}
			}else {
				throw new SQLException("Too many active connection");
			}
		}else {
			throw new SQLException("Try to get a connection from a closed pool");
		}
	}

	/**
	 * 将被取出一个连接插入队列末尾, 放回后检查连接池。
	 */
	@Override
	public synchronized void put(PooledConnection connection) throws SQLException {
		if(isWorking()) {
			if(connection.belongOf(this)) {
				if(activeConns.remove(connection)) {
					if(!connection.isClosed()) {
						waittingConns.add((TimeOutPooledConnection) connection);
					}
				}else {
					if(connection.isClosed()) {
						waittingConns.remove(connection);
					}
				}
			}else {
				throw new SQLException("The Connection does not belong of this pool!");
			}
			check();
		}else {
			throw new SQLException("ConnectionPool not working!");
		}
	}
	
	@Override
	public synchronized boolean isWorking() {
		return working;
	}
	
	@Override
	public synchronized boolean isInited() {
		return inited;
	}
	
	public int getTimeOut() {
		return timeOut;
	}
	
	public static ConnectionPool getInitedConnPool(TimeOutConnPoolParams params) {
		TimeOutConnectionPool dcp = new TimeOutConnectionPool(params.getMaxNum()
				, params.getMinNum(), params.getInitNum(), params.getMaxActiveNum()
				, params.getTryTime(), params.getTimeOut());
		dcp.init(params.getDirver()
				, URLBuilder.getMysqlURL(params.getHost(), params.getPort(), params.getName(), params.getUrlDeclar())
				, params.getUser(), params.getPassword());
		return dcp;
	}
	
	/**
	 * 返回连接池状态
	 */
	public synchronized String toString() {
		StringBuilder ret = new StringBuilder("TimeOutConnetionPool:");
		ret.append(this.hashCode()).append("\n");
		ret.append("Active:").append(activeConns.toArray().length).append("{");
		for(TimeOutPooledConnection tp : activeConns) {
			try {
				ret.append("(").append(Boolean.toString(tp.isClosed())).append(",")
					.append(Boolean.toString(tp.isTimeOut())).append(")").append("\n");
			} catch (SQLException e) {
				ret.append("\n[").append(e.getMessage());
			}
		}
		ret.append("}\n");
		
		ret.append("Waitting:").append(waittingConns.toArray().length).append("{");
		for(TimeOutPooledConnection tp : waittingConns) {
			try {
				ret.append("(").append(Boolean.toString(tp.isClosed())).append(",")
					.append(Boolean.toString(tp.isTimeOut())).append(")");
			} catch (SQLException e) {
				ret.append("[").append(e.getMessage()).append("]");
			}
		}
		ret.append("}\n");
		
		return ret.toString();
		
	}
	
	
}
