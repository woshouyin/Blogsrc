package util;

public class URLBuilder{
	
	/**
	 * 构建mysql连接的URL
	 * @param host
	 * 地址
	 * @param port
	 * 端口
	 * @param name
	 * 数据库名
	 * @param declar
	 * 附加在url尾部的字符串
	 */
	public static String getMysqlURL(String host, int port, String name, String declar) {
		StringBuilder ret = new StringBuilder();
		ret.append("jdbc:mysql://").append(host).append(":").append(port).append("/").append(name).append(declar);
		return ret.toString();
	}
	/**
	 * 构建mysql连接的URL
	 * @param host
	 * 地址
	 * @param port
	 * 端口
	 * @param name
	 * 数据库名
	 */
	public static String getMysqlURL(String host, int port, String name) {
		return getMysqlURL(host, port, name, "");
	}
}
