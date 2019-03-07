package database.connectionPool.connections;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;

import database.connectionPool.TimeOutConnectionPool;

/**
 * 记录连接时间的连接，可判断是否超时，应当被一个TimeOutConnectionPool拥有
 */
public class TimeOutPooledConnection extends AbsPooledConnection<TimeOutConnectionPool>{
	private long time;
	
	/**
	 * 创建时记录一次时间
	 * @param conn
	 * 控制的连接
	 * @param tconnPool
	 * 连接池
	 */
	public TimeOutPooledConnection(Connection conn, TimeOutConnectionPool tconnPool){
		super(conn, tconnPool);
		this.time = new Date().getTime();
	}
	
	/**
	 * @return
	 * 超时为true，否则为false
	 */
	public boolean isTimeOut() {
		if(time - new Date().getTime() > connPool.getTimeOut() * 1000) {
			return true;
		}else {
			return false;
		}
	}

	
	/**
	 * 放回时记录一次时间
	 */
	@Override
	public void putBack() throws SQLException {
		time = new Date().getTime();
		super.putBack();
	}

}
