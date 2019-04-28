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
import javax.servlet.http.HttpSession;

import com.mysql.cj.Session;

import database.DatabaseManager;
import database.dao.UserCheckDao;
import util.AttributeGetter;
import util.StrCheck;

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
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		PrintWriter writer = response.getWriter();
		DatabaseManager dm = AttributeGetter.getDatabaseManager(request);
		String name = request.getParameter("name");
		String password = request.getParameter("password");
		try(UserCheckDao ucd = dm.getDao(UserCheckDao.class)) {
			long id = -1;
			if(StrCheck.check("PASSWORD", password)  
					&& (id = ucd.check(name, password)) != -1) {
				String token = ucd.setNewToken(id);
				Cookie cookie = new Cookie("tk", token);
				cookie.setMaxAge(60*60*24);
				response.addCookie(cookie);
				response.sendRedirect(request.getContextPath() + "/User.jsp");
				return;
			}else {
				request.getRequestDispatcher("/Login.jsp").forward(request, response);
				return;
			}
		} catch (SQLException e) {
			writer.append("服务器繁忙");
			e.printStackTrace();
		}
		
	}

}
