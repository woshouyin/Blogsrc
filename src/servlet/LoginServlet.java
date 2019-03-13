package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.DatabaseManager;
import database.dao.UserCheckDao;
import util.AttributeGetter;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
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
		response.setCharacterEncoding("UTF-8");
		PrintWriter writer = response.getWriter();
		//DatabaseManager dm = (DatabaseManager) request.getServletContext().getAttribute("DatabaseManager");
		DatabaseManager dm = AttributeGetter.getDatabaseManager(request);
		String idstr = request.getParameter("id");
		String password = request.getParameter("password");
		System.out.println(idstr);
		System.out.println(password);
		
		if(idstr == null || password == null) {
			request.getRequestDispatcher("/Login.jsp").forward(request, response);
			return;
		}
		
		long id = Long.parseLong(idstr);
		try(UserCheckDao ucd = dm.getDao(UserCheckDao.class)) {
			if(ucd.check(id, password)) {
				String token = ucd.setNewToken(id);
				System.out.println("reg:" + token);
				Cookie cookie = new Cookie("tk", token);
				cookie.setMaxAge(60*60*24);
				response.addCookie(cookie);
				request.getRequestDispatcher("/User.jsp").forward(request, response);
				return;
			}else {
				request.getRequestDispatcher("/Login.jsp").forward(request, response);
				return;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		writer.flush();
		
		
	}

}
