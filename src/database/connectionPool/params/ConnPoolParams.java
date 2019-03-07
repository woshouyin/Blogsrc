package database.connectionPool.params;

import config.AMSConfig;

/**
 * 用来存放数据库初始化参数的类的抽象类
 */
public abstract class ConnPoolParams {
	private String host;
	private int port;
	private String name;
	private String user;
	private String password;
	private String urlDeclar;
	
	/**
	 * 从AMSConfig中提取参数
	 * @param config
	 * AMSConfig实例
	 */
	public void fromAMSConfig(AMSConfig config) {
		host = config.getString("databaseHost");
		port = config.getInt("databasePort");
		name = config.getString("databaseName");
		user = config.getString("databaseUser");
		password = config.getString("databasePassword");
		urlDeclar = config.getString("databaseUrlDeclar");
	}
	
	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUrlDeclar() {
		return urlDeclar;
	}

	public void setUrlDeclar(String urlDeclar) {
		this.urlDeclar = urlDeclar;
	}
	
}
