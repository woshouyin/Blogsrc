package service.storage;

import java.io.File;
import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import database.gas.UserGas;

public class Storage {
	UserGas ug;
	String rootPath;
	
	public Storage(UserGas ug, String rootPath){
		this.ug = ug;
		this.rootPath = rootPath;
	}
	
	public void init() throws IOException {
		StringBuilder ufp = new StringBuilder(rootPath);
		ufp.append("user/").append(Long.toString(ug.getId())).append(".json");
		File f = new File(ufp.toString());
		if(f.exists()) {
			throw new IOException();
		}
		
	}
	
}
