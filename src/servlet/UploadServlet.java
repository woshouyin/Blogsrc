package servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.FileItemFactory;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import config.AMSConfig;
import util.AttributeGetter;

/**
 * Servlet implementation class UploadServlet
 */
@WebServlet("/UploadServlet")
public class UploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UploadServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Boolean sucess = false;
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		PrintWriter pw = response.getWriter();
		AMSConfig config = AttributeGetter.getAMSConfig(request);
		StringBuffer basePath = new StringBuffer((String) config.get("amsHomePath"));
		
		if(ServletFileUpload.isMultipartContent(request)) {
			FileItemFactory factory = new DiskFileItemFactory();
			ServletFileUpload upload = new ServletFileUpload(factory);
			
			Calendar ca = Calendar.getInstance();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			
			try {
				Map<String, List<FileItem>> items = upload.parseParameterMap(request);
				
				StringBuffer path = new StringBuffer("files/");
				path.append("images/").append(sdf.format(ca.getTime()));
				
				File dir = new File(basePath.toString() + path);
				if(!dir.exists()) {
					dir.mkdir();
				}
				
				for(FileItem item : items.get("image_upload_file")) {
					String[] strs = item.getName().split("\\.");
					String type = strs[strs.length - 1];
					byte[] buff = item.get();
					MessageDigest md = MessageDigest.getInstance("MD5");
					String hashString = new BigInteger(1, md.digest(buff)).toString(16);
					StringBuffer filePath = new StringBuffer(path);
					
					filePath.append('/').append(hashString).append(".").append(type);
					File file = new File(basePath.toString() + filePath);
					if(!file.exists()) {
						item.write(file);
					}
					sucess = true;
					pw.append("{\"sucess\":").append(sucess.toString()).append(",\"file_path\":")
										.append("\"").append(filePath).append("\"}");
				}
			} catch (FileUploadException e) {
				e.printStackTrace();
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if(!sucess) {
			pw.append("{\"sucess\":").append(sucess.toString()).append(",\"file_path\":")
			.append("\"").append("\"}");
		}
		
	}

}
