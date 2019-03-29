package servlet;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
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

/**
 * Servlet implementation class GetArticleServlet
 */
@WebServlet("/GetArticleServlet")
public class GetArticleServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetArticleServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter writer = response.getWriter();
		DatabaseManager dm = (DatabaseManager) this.getServletContext().getAttribute("DatabaseManager");
		AMSConfig cfg = (AMSConfig) this.getServletContext().getAttribute("AMSConfig");
		String home = cfg.getString("amsHomePath");
		long aid = Long.parseLong(request.getParameter("aid"));
		try(ArticleDao ad = dm.getDao(ArticleDao.class);) {
			ArticleGas ag = ad.getArticleGas(aid);
			FileReader f = new FileReader(home + ag.getPsgPath());
			BufferedReader br = new BufferedReader(f);
			String line;
			while ((line = br.readLine()) != null) {
				writer.write(line);
			}
			ad.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
