package database.gas;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;

import util.FileBuilder;

public class ArticleGas {
	private long id;
	private long autherId;
	private String autherName;
	private String title;
	private Date crtDate;
	private String psgPath;
	public static final String PLACEHOLDER = "{a{atid}d}";
	
	public static ArticleGas generatePuttingArticleGas(long autherId, String autherName, String title, String rawPsgPath) {
		ArticleGas at = new ArticleGas();
		at.setAutherId(autherId);
		at.setAutherName(autherName);
		at.setTitle(title);
		at.setPsgPath(rawPsgPath);
		at.setCrtDate(new java.sql.Date(new java.util.Date().getTime()));
		return at;
	}
	
	public void writeContent(String base, String content) throws FileNotFoundException {
		File f = new File(base + psgPath);
		if(!f.getParentFile().exists()) {
			FileBuilder.buildPathEx(f.getParentFile());
		}
		PrintWriter fpw = new PrintWriter(f);
		fpw.print(content);
		fpw.close();
	}
	
	public String readContent(String base) throws IOException {
		StringBuilder ret = new StringBuilder();
		FileReader f = new FileReader(base + psgPath);
		BufferedReader br = new BufferedReader(f);
		String line;
		while ((line = br.readLine()) != null) {
			ret.append(line);
		}
		br.close();
		return ret.toString();
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
	public String getAutherName() {
		return autherName;
	}
	public void setAutherName(String autherName) {
		this.autherName = autherName;
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
