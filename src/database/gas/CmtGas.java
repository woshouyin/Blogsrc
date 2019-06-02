package database.gas;

import java.sql.Date;

public class CmtGas {

	private long id;
	private long autherId;
	private String autherName;
	private String contant;
	private Date crtDate;
	private long directId;
	private long articleId;
	
	
	public long getId() {
		return id;
	}
	public long getAutherId() {
		return autherId;
	}
	public String getAutherName() {
		return autherName;
	}
	public String getContant() {
		return contant;
	}
	public Date getCrtDate() {
		return crtDate;
	}
	public long getDirectId() {
		return directId;
	}
	public long getArticleId() {
		return articleId;
	}
	public void setId(long id) {
		this.id = id;
	}
	public void setAutherId(long autherId) {
		this.autherId = autherId;
	}
	public void setAutherName(String autherName) {
		this.autherName = autherName;
	}
	public void setContant(String contant) {
		this.contant = contant;
	}
	public void setCrtDate(Date crtDate) {
		this.crtDate = crtDate;
	}
	public void setDirectId(long directId) {
		this.directId = directId;
	}
	public void setArticleId(long articleId) {
		this.articleId = articleId;
	}
	
	
}
