package servlet;

import java.io.IOException;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import config.AMSConfig;
import database.DatabaseManager;
import database.connectionPool.params.TimeOutConnPoolParams;
import log.LogUtil;
import util.FileBuilder;

/**
 * Servlet implementation class InitServlet
 */
@WebServlet(name = "InitServlet", urlPatterns = "/InitServlet", loadOnStartup = 0)
public class InitServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
    public InitServlet() {
        super();
    }
    /**
     * 在该方法对webapp进行初始化
     */
    @Override
    public void init() throws ServletException {
    	ServletContext context = this.getServletContext();
    	//初始化设置
    	String configPath = (String) context.getInitParameter("AMSConfigPath");
    	String configEncode = (String) context.getInitParameter("AMSConfigEncode");
    	AMSConfig config = new AMSConfig(configPath, configEncode);
    	context.setAttribute("AMSConfig", config);
    	//构建文件目录
    	FileBuilder fb = new FileBuilder();
    	fb.registerPath("/log");
    	fb.registerPath("/files");
    	fb.registerPath("/files/images");
    	fb.registerPath("/files/articles");
    	fb.registerPath("/files/download");
    	fb.build(config.getString("amsHomePath"));
		//初始化日志
    	LogUtil.init("AMSLogger", config.getString("amsHomePath") + "log/");
    	//启动数据库连接池
		config = (AMSConfig) this.getServletContext().getAttribute("AMSConfig");
		TimeOutConnPoolParams tcpp = new TimeOutConnPoolParams();
		tcpp.fromAMSConfig(config);
		DatabaseManager dm = new DatabaseManager("TimeOutConnectionPool", tcpp);
    	context.setAttribute("DatabaseManager", dm);
    	super.init();
    	
    }
    
    /**
     * 释放有关
     */
    @Override
    public void destroy() {
    	LogUtil.close();
    	super.destroy();
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
