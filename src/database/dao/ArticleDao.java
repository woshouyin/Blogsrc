package database.dao;

import java.sql.CallableStatement;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.SQLType;
import java.sql.Types;
import java.util.HashMap;
import database.connectionPool.connections.PooledConnection;
import database.gas.ArticleGas;

public class ArticleDao extends AbsDao{
	
	public ArticleDao(PooledConnection conn) {
		super(conn);
	}
	
	public ArticleGas getArticleGas(long id) throws SQLException {
		HashMap<String, Object> map = selectAllFromTableById("article", id);
		if(map == null) {
			return null;
		}
		ArticleGas ag = new ArticleGas();
		ag.setId((long) map.get("id"));
		ag.setTitle((String) map.get("title"));
		ag.setAutherId((long) map.get("auther_id"));
		ag.setCrtDate((Date) map.get("crt_date"));
		ag.setPsgPath((String) map.get("psg_path"));
		return	ag;
	}
	
	public long putArticle(ArticleGas ag) throws SQLException {
		long id = -1;
		connection.setAutoCommit(false);
		String sql = "{call add_article(?,?,?,?,?,?)}";
		CallableStatement cstat = connection.prepareCall(sql);
		cstat.setLong(1, ag.getAutherId());
		cstat.setString(2, ag.getTitle());
		cstat.setDate(3, ag.getCrtDate());
		cstat.setString(4, ag.getPsgPath());
		cstat.setString(5, ArticleGas.PLACEHOLDER);
		cstat.registerOutParameter(6, Types.BIGINT);
		cstat.execute();
		id = cstat.getLong(6);
		return id;
	}

}
