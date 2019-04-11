package database.gas;

import java.sql.Date;

public class ArticleGas {
	private long id;
	private long autherId;
	private String title;
	private Date crtDate;
	private String psgPath;
	public static final String PLACEHOLDER = "{a{atid}d}";
	
	public static ArticleGas generatePuttingArticleGas(long autherId, String title, String rawPsgPath) {
		ArticleGas at = new ArticleGas();
		at.setAutherId(autherId);
		at.setTitle(title);
		at.setPsgPath(rawPsgPath);
		at.setCrtDate(new java.sql.Date(new java.util.Date().getTime()));
		return at;
	}
	
	public long getId() {
		return id;
	}
	public long getAutherId() {
		return autherId;
	}
	public String getTitle() {
		return title;
	}
	public Date getCrtDate() {
		return crtDate;
	}
	public String getPsgPath() {
		return psgPath;
	}
	public void setId(long id) {
		this.id = id;
	}
	public void setAutherId(long autherId) {
		this.autherId = autherId;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public void setCrtDate(Date crtDate) {
		this.crtDate = crtDate;
	}
	public void setPsgPath(String psgPath) {
		this.psgPath = psgPath;
	}
	
	public void replaceAtid() {
		psgPath = psgPath.replace(PLACEHOLDER, Long.toString(id));
	}

}
