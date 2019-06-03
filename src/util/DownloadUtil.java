package util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class DownloadUtil {
	
	/**
	 * 从指定url下载文件
	 */
	public static void DownloadFromUrl(URL url, File file, int timeout) throws IOException {
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setConnectTimeout(timeout);
		conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
		
		InputStream is = conn.getInputStream();
		FileOutputStream fos = new FileOutputStream(file);
		byte[] buffer = new byte[2048];
		int len = 0;
		while((len = is.read(buffer)) != -1) {
			fos.write(buffer, 0, len);
		}
		fos.close();
		is.close();
	}
}
