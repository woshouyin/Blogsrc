package util;

import java.io.*;
import java.util.HashSet;

/**
 * 用于创建文件目录
 */
public class FileBuilder {
	private HashSet<String> pathList = new HashSet<String>();

	/**
	 * 构建目录
	 * @param homePath
	 * 文件根目录
	 */
	public void build(String homePath) {
		for(String patht:this.pathList) {
			StringBuffer str = new StringBuffer(homePath);
			str.append(patht);
			File f =new File(str.toString());
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
}

