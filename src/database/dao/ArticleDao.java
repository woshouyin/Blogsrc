package database.dao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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
	
	public void putArticle(ArticleGas ag) throws SQLException {
		connection.setAutoCommit(false);
		String sql = "INSERT INTO `article`(`id`, `title`, `auther_id`, `crt_date`, `psg_path`) VALUES(?,?,?,?,?)";
		PreparedStatement stat = connection.prepareStatement(sql);
		stat.setLong(1, ag.getId());
		stat.setString(2, ag.getTitle());
		stat.setLong(3, ag.getAutherId());
		stat.setDate(4, ag.getCrtDate());
		stat.setString(5, ag.getPsgPath());
		try {
			stat.execute();
			connection.commit();
		} catch(SQLException e) {
			connection.rollBack();
			e.printStackTrace();
		}
		
	}

}
