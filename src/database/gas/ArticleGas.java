package database.gas;

import java.sql.Date;

public class ArticleGas {
	private long id;
	private long autherId;
	private String title;
	private Date crtDate;
	private String psgPath;
	
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
	
	public void writeArticle() {
		
	}
	

}
