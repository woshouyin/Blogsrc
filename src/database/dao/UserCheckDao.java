package database.dao;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Random;

import database.connectionPool.connections.PooledConnection;
import sun.print.PeekGraphics;

/**
 * 用于检查用户
 */
public class UserCheckDao extends AbsDao{

	/**
	 * @param conn
	 * 数据库连接
	 */
	public UserCheckDao(PooledConnection conn) {
		super(conn);
	}
	
	/**
	 * id password检查
	 * @param id
	 * 用户ID
	 * @param password
	 * 密码
	 */
	public boolean check(long id, String password) throws SQLException {
		boolean ret = false;
		String sql = "SELECT `id` FROM `user_check` WHERE `id` = ? AND `password` = MD5(?)";
		PreparedStatement psta = connection.prepareStatement(sql);
		psta.setLong(1, id);
		psta.setString(2, password);
		ResultSet rs = psta.executeQuery();
		if(rs.next()) {
			ret = true;
		}
		rs.close();
		psta.close();
		return ret;
	}
	
	
	/**
	 * 检查token值是否正确
	 * @param token
	 * 
	 * @return
	 * 返回对应的id，若失败，返回-1查
	 * @throws SQLException 
	 */
	public long checkByToken(String token) throws SQLException {
		long ret = -1;
		String sql = "select `id` from `user_check` where `token` = MD5(?)";
		PreparedStatement psta = connection.prepareStatement(sql);
		psta.setString(1, token);
		ResultSet rs = psta.executeQuery();
		if(rs.next()) {
			ret = rs.getLong("id");
		}
		rs.close();
		psta.close();
		return ret;
	}
	
	/**
	 * 为用户生成并设置新的token值
	 * @throws SQLException 
	 * 
	 */
	
	public String setNewToken(long id) throws SQLException {
		//生成token String:id + 随机数 + 服务器时间秒数  MD5加密
		StringBuffer str = new StringBuffer();
		str.append(id).append(new Random().nextInt(9999999)).append(new Date().getTime());
		MessageDigest md;
		StringBuffer token = null;
		try {
			md = MessageDigest.getInstance("MD5");
			byte[] bts = md.digest(str.toString().getBytes());
			token = new StringBuffer();
			for(byte bt : bts) {
				token.append(Integer.toHexString((0x000000FF & bt)| 0xFFFFFF00).substring(6));
			}
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		if(token == null) {
			return null;
		}
		
		//修改数据库
		
		String token_str = token.toString();
		try {
			connection.setAutoCommit(false);
			String sql = "update `user_check` set `token` = MD5(?) where id = ?";
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setString(1, token_str);
			ps.setLong(2, id);
			ps.execute();
		} catch (SQLException e) {
			connection.rollBack();
			throw e;
		}
		
		return token_str;
		
	}
	
	
	
}
