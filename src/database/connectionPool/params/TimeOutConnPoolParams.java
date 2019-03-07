package database.connectionPool.params;

import config.AMSConfig;

/**
 * 用于保存TimeOutConnPool相关参数的类
 */
public class TimeOutConnPoolParams extends ConnPoolParams{
	private String dirver;
	private int maxNum;
	private int minNum;
	private int initNum;
	private int maxActiveNum;
	private int tryTime;
	private int timeOut;
	
	@Override
	public void fromAMSConfig(AMSConfig config) {
		dirver = config.getString("databaseDriver");
		maxNum = config.getInt("dbPoolMaxNum");
		minNum = config.getInt("dbPoolMinNum");
		initNum = config.getInt("dbPoolInitNum");
		maxActiveNum = config.getInt("dbPoolMaxActiveNum");
		tryTime = config.getInt("dbPoolTryTime");
		timeOut = config.getInt("dbPoolTimeOut");
		super.fromAMSConfig(config);
	}

	public String getDirver() {
		return dirver;
	}

	public void setDirver(String dirver) {
		this.dirver = dirver;
	}

	public int getMaxNum() {
		return maxNum;
	}

	public void setMaxNum(int maxNum) {
		this.maxNum = maxNum;
	}

	public int getMinNum() {
		return minNum;
	}

	public void setMinNum(int minNum) {
		this.minNum = minNum;
	}

	public int getInitNum() {
		return initNum;
	}

	public void setInitNum(int initNum) {
		this.initNum = initNum;
	}

	public int getMaxActiveNum() {
		return maxActiveNum;
	}

	public void setMaxActiveNum(int maxActiveNum) {
		this.maxActiveNum = maxActiveNum;
	}

	public int getTryTime() {
		return tryTime;
	}

	public void setTryTime(int tryTime) {
		this.tryTime = tryTime;
	}

	public int getTimeOut() {
		return timeOut;
	}

	public void setTimeOut(int timeOut) {
		this.timeOut = timeOut;
	}
	
}
