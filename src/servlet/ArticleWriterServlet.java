package servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import config.AMSConfig;
import database.DatabaseManager;
import database.dao.ArticleDao;
import database.gas.ArticleGas;
import database.gas.UserGas;
import util.AttributeGetter;
import util.CookieUtil;
import util.FileBuilder;

/**
 * Servlet implementation class ArticleWriterServlet
 */
@WebServlet("/ArticleWriterServlet")
public class ArticleWriterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ArticleWriterServlet() {
        super();
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
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter pw = response.getWriter();
		String title = request.getParameter("title");
		String atc = request.getParameter("article");
		DatabaseManager dm = AttributeGetter.getDatabaseManager(request);
		AMSConfig config = AttributeGetter.getAMSConfig(request);
		CookieUtil cu = new CookieUtil(request);
		UserGas ug = cu.getUserByTokenCookie(dm);
		if(ug == null) {
			pw.append("Not Logined");
			return;
		}
		String base = config.getString("amsHomePath");
		StringBuilder path = new StringBuilder("files/articles/");
		path.append(ug.getName()).append("/ac").append(ArticleGas.PLACEHOLDER).append(".html");
		ArticleGas ag = ArticleGas.generatePuttingArticleGas(ug.getId(), ug.getName(), title, path.toString());
		try(ArticleDao ad = dm.getDao(ArticleDao.class);) {
			long id = ad.putArticle(ag);
			ag.setId(id);
			ag.replaceAtid();
			ag.writeContent(base, atc);
		} catch (SQLException e) {
			pw.append("写入失败");
			e.printStackTrace();
			return;
		}
		response.sendRedirect("GetArticleServlet?aid=" + ag.getId());
		
	}

}
