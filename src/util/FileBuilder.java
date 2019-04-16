package util;

import java.io.*;
import java.util.HashSet;
import java.util.Stack;

/**
 * 用于创建文件目录
 */
public class FileBuilder {
	private HashSet<String> pathList = new HashSet<String>();

	/**
	 * 构建目录,需要一层层创建。
	 * @param homePath
	 * 文件根目录
	 */
	public void build(String homePath) {
		for(String patht:this.pathList) {
			File f =new File(homePath + patht);
			f.mkdirs();
		}
	}

	/**
	 * 注册文件目录
	 * @param path
	 * 基于根目录的文件目录 
	 */
	public void registerPath(String path) {
		this.pathList.add(path);
	}
	
	/**
	 * 创建任意路径
	 */
	public static void buildPathEx(File f) {
		Stack<File> fs = new Stack<File>();
		while(f != null && !f.exists()) {
			fs.push(f);
			f = f.getParentFile();
		}
		while(!fs.isEmpty()) {
			f = fs.pop();
			f.mkdir();
		}
	}
}

