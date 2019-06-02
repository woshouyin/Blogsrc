package database.dao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Stack;

import database.connectionPool.connections.PooledConnection;
import database.gas.ArticleGas;
import database.gas.CmtGas;
import database.gas.CmtTreeGas;

public class CmtDao extends AbsDao{

	public CmtDao(PooledConnection conn) {
		super(conn);
	}
	
	public CmtGas getCmtGas(long id) throws SQLException {
		HashMap<String, Object> map = selectAllFromTableById("article", id);
		if(map == null) {
			return null;
		}
		CmtGas cg = new CmtGas();
		cg.setId((long) map.get("id"));
		cg.setAutherId((long) map.get("auther_id"));
		cg.setAutherName((String) map.get("auther_name"));
		cg.setContant((String) map.get("contant"));
		cg.setCrtDate((Date) map.get("crt_date"));
		cg.setDirectId((long) map.get("direct_id"));
		cg.setArticleId((long) map.get("article_id"));
		return	cg;
	}
	
	public CmtTreeGas getCmtTreeGasByArticleId(long articleId) throws SQLException {
		String sql = "select * from cmt where `article_id`=? order by `id` desc";
		PreparedStatement stat = connection.prepareStatement(sql);
		stat.setLong(1, articleId);
		ResultSet rs = stat.executeQuery();
		Stack<CmtGas> cgs = new Stack<>();
		while(rs.next()) {
			CmtGas cg = new CmtGas();
			cg.setId(rs.getLong("id"));
			cg.setAutherId(rs.getLong("auther_id"));
			cg.setAutherName(rs.getString("auther_name"));
			cg.setContant(rs.getString("contant"));
			cg.setCrtDate(rs.getDate("crt_date"));
			cg.setDirectId(rs.getLong("direct_id"));
			cg.setArticleId(rs.getLong("article_id"));
			cgs.push(cg);
		}
		return CmtTreeGas.genCmtTree(cgs);
	}
	

}
