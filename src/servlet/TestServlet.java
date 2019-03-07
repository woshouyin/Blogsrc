package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.DatabaseManager;
import database.dao.UserCheckDao;
import database.dao.UserDao;
import exception.IllegalPasswordException;
import gas.RegisterUserGas;
import log.LogUtil;

/**
 * Servlet implementation class TestServlet
 */
@WebServlet("/TestServlet")
public class TestServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public TestServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8");
		PrintWriter writer = response.getWriter();
		DatabaseManager dm = (DatabaseManager) this.getServletContext().getAttribute("DatabaseManager");
		writer.append("<!DOCTYPE html>");
		writer.append("<html><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\"></head>");
		writer.append("<form method = \"POST\"><input type = \"number\" name = \"userID\"><br><input type = \"password\" name = \"password\" ><br>"
				+ "<input type = \"submit\"></form>");
		Object userIDObj = request.getParameter("userID");
		Object passwordObj = request.getParameter("password");
		if(userIDObj != null && passwordObj != null) {
			long id = Long.parseLong((String) userIDObj);
			String password = (String) passwordObj;
			boolean passed = false;
			try (UserCheckDao ucd = dm.getDao(UserCheckDao.class)){
				passed = ucd.check(id, password);
			} catch (SQLException e) {
				e.printStackTrace();
				writer.append("Database Error<br>");
			}
			if(passed) {
				writer.append("Logged");
			}else {
				try (UserDao ud = dm.getDao(UserDao.class)){
					RegisterUserGas rug = new RegisterUserGas(id, password);
					ud.registerUser(rug);
				} catch (SQLException e) {
					e.printStackTrace();
				} catch (IllegalPasswordException e) {
					e.printStackTrace();
				}
				writer.append("Not Loggined");
			}
		}
		writer.append("</html>");
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
