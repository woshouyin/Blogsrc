package database.dao;

import java.sql.CallableStatement;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLType;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import database.connectionPool.connections.PooledConnection;
import database.gas.ArticleGas;

public class ArticleDao extends AbsDao{
	
	public ArticleDao(PooledConnection conn) {
		super(conn);
	}
	
	/**
	 * 通过文章id获取文章
	 */
	public ArticleGas getArticleGas(long id) throws SQLException {
		HashMap<String, Object> map = selectAllFromTableById("article", id);
		if(map == null) {
			return null;
		}
		ArticleGas ag = new ArticleGas();
		ag.setId((long) map.get("id"));
		ag.setTitle((String) map.get("title"));
		ag.setAutherId((long) map.get("auther_id"));
		ag.setAutherName((String) map.get("auther_name"));
		ag.setCrtDate((Date) map.get("crt_date"));
		ag.setPsgPath((String) map.get("psg_path"));
		return	ag;
	}
	
	/**
	 * 将文章存入数据库，存入后返回文章id
	 * @param ag
	 * 文章内容
	 * @return
	 * 文章id
	 */
	public long putArticle(ArticleGas ag) throws SQLException {
		long id = -1;
		connection.setAutoCommit(false);
		String sql = "{call add_article(?,?,?,?,?,?,?)}";
		CallableStatement cstat = connection.prepareCall(sql);
		cstat.setLong(1, ag.getAutherId());
		cstat.setString(2, ag.getAutherName());
		cstat.setString(3, ag.getTitle());
		cstat.setDate(4, ag.getCrtDate());
		cstat.setString(5, ag.getPsgPath());
		cstat.setString(6, ArticleGas.PLACEHOLDER);
		cstat.registerOutParameter(7, Types.BIGINT);
		cstat.execute();
		id = cstat.getLong(7);
		return id;
	}
	
	public ArrayList<ArticleGas> getArticlesByAutherId(long autherId) throws SQLException{
		ArrayList<ArticleGas> ags = new ArrayList<ArticleGas>();
		String sql = "select * from `article` where `auther_id`=? order by `id` asc";
		PreparedStatement stat = connection.prepareStatement(sql);
		stat.setLong(1, autherId);
		ResultSet rs = stat.executeQuery();
		while(rs.next()) {
			ArticleGas ag = new ArticleGas();
			ag.setAutherId(rs.getLong("auther_id"));
			ag.setAutherName(rs.getString("auther_name"));
			ag.setCrtDate(rs.getDate("crt_date"));
			ag.setId(rs.getLong("id"));
			ag.setPsgPath(rs.getString("psg_path"));
			ag.setTitle(rs.getString("title"));
			ags.add(ag);
		}
		return ags;
	}

}
