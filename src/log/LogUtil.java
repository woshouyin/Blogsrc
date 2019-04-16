package log;

import java.io.IOException;
import java.util.Calendar;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
/**
 * 用于输出日志的工具类，单例模式。
 * 输出日志按照获取Logger的时间按天分开，
 * 保存在logPath里的yyyy-mm-dd.log文件中。
 * 通过静态方法获取唯一Logger。
 */
public class LogUtil{
	private static LogUtil LOG_UTIL;
	private String logPath;
	private Logger logger;
	private Calendar calendar;
	private FileHandler fileHandler;
	private Formatter formatter;
	
	/**
	 * 不允许外部直接创建实例。
	 * @param name
	 * Logger 的 name
	 * @param path
	 * 日志储存路径
	 */
	private LogUtil(String name, String path){
		logger = Logger.getLogger(name);
		logPath = path;
		calendar = Calendar.getInstance();
		formatter = new SimpleFormatter();
		setHandler();
	}
	
	/**
	 * 设置新的FileHandler，关闭旧的FileHandler
	 */
	private void setHandler() {
		String filePath = logPath + getLogFileName(calendar);
		FileHandler fh;
		try {
			fh = new FileHandler(filePath, true);
			fh.setFormatter(formatter);
			if(fileHandler != null) {
				logger.removeHandler(fileHandler);
				fileHandler.close();
			}
			fileHandler = fh;
			logger.addHandler(fh);
		} catch (SecurityException | IOException e) {
			Logger.getLogger("AMS").log(Level.SEVERE, "Can not get LogFile");
			e.printStackTrace();
		}
	}
	
	/**
	 * 获取logger，若到了新的一天，则改变文件的Handler
	 * @return
	 * 返回logger
	 */
	private synchronized Logger get() {
		Calendar cal = Calendar.getInstance();
		if(cal.get(Calendar.YEAR) != calendar.get(Calendar.YEAR)
			|| cal.get(Calendar.DAY_OF_YEAR) != calendar.get(Calendar.DAY_OF_YEAR)) {
			calendar = cal;
			setHandler();
		}
		return logger;
	}

	/**
	 *释放相关资源
	 */
	private void release() {
		if(fileHandler != null) {
			fileHandler.close();
			logger.removeHandler(fileHandler);
		}
	}
	
	/**
	 *初始化工具类，创建单例。 
	 * @param name
	 * Logger 的 name
	 * @param path
	 * 日志储存路径
	 */
	public static void init(String name, String logPath) {
		if(LOG_UTIL == null) {
			LOG_UTIL = new LogUtil(name, logPath);
		}
	}
	
	/**
	 * 获取logger，若到了新的一天，则改变文件的Handler
	 * @return
	 * 返回logger
	 */
	public static Logger getLogger(){
		return LOG_UTIL.get();
	}
	
	public static void info(String str) {
		getLogger().log(Level.INFO, str);
	}
	
	public static void warning(String str) {
		getLogger().log(Level.WARNING, str);
	}
	
	public static void severe(String str) {
		getLogger().log(Level.SEVERE, str);
	}
	
	
	/**
	 *释放相关资源
	 */
	public static void close() {
		LOG_UTIL.release();
		LOG_UTIL = null;
	}
	
	/**
	 * 通过日期获得日志文件名。
	 * @param cal
	 * 日期
	 * @return
	 * 文件名
	 */
	public static String getLogFileName(Calendar cal) {
		StringBuilder ret = new StringBuilder();
		ret.append(cal.get(Calendar.YEAR)).append("-").append(cal.get(Calendar.MONTH) + 1)
					.append("-").append(cal.get(Calendar.DAY_OF_MONTH)).append(".log");
		return ret.toString();
	}
}
