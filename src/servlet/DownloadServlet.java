package servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import config.AMSConfig;
import util.AttributeGetter;
import util.DownloadUtil;
import util.FileBuilder;
import util.StrCheck;

/**
 * Servlet implementation class DownloadServlet
 */
@WebServlet("/DownloadServlet")
public class DownloadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DownloadServlet() {
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
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		PrintWriter writer = response.getWriter();
		AMSConfig config = AttributeGetter.getAMSConfig(request);
		String fileName = request.getParameter("fileName");
		if(!StrCheck.check("FILE_NAME", fileName)) {
			writer.append("请输入正确的文件路径!!");
			return;
		}
		String urlStr = request.getParameter("urlStr");
		StringBuilder path = new StringBuilder(config.getString("amsHomePath"));
		path.append("files/download/").append(fileName);
		File file = new File(path.toString());
		FileBuilder.buildPathEx(file.getParentFile());
		try{
			URL url = new URL(urlStr);
			DownloadUtil.DownloadFromUrl(url, file, 3000);
		}catch(MalformedURLException e) {
			writer.append("请输入正确的URL！！").append("<br>").append(e.getMessage());
			e.printStackTrace();
			return;
		}catch(IOException e) {
			writer.append("下载失败！！").append("<br>").append(e.getMessage());
			e.printStackTrace();
			return;
		}
		response.sendRedirect("Download.jsp");
	}

}
