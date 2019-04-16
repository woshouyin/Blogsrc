package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import database.DatabaseManager;
import database.dao.UserDao;
import database.gas.RegisterUserGas;
import exception.IllegalPasswordException;

/**
 * Servlet implementation class RegisterServlet
 */
@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegisterServlet() {
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
		response.setContentType("text/html;charset=utf-8");
		PrintWriter writer = response.getWriter();
		DatabaseManager dm = (DatabaseManager) request.getServletContext().getAttribute("DatabaseManager");
		boolean flag = false;
		
		try(UserDao ud = dm.getDao(UserDao.class)) {
			RegisterUserGas rug= new RegisterUserGas(request.getParameter("name"), request.getParameter("password"));
			rug.setEmail(request.getParameter("email"));
			ud.registerUser(rug);
			flag = true;
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IllegalPasswordException e1) {
			e1.printStackTrace();
		}
		
		if (flag) {
			response.sendRedirect("Login.jsp");
		}else {
			writer.append("fall");
		}
		
	}

}
