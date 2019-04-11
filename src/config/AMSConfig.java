package config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Set;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import exception.AMSConfigException;

/**
 * 用来载入和保存配置的类。
 */
public class AMSConfig {
	
	private String configPath;
	private String configEncode;
	private HashMap<String, Object> configs;

	/**
	 * 构造AMSConfig，传入配置文件路径和配置文件编码方式，调用
	 * load()方法导入配置文件。
	 * @param configPath 
	 * 配置文件路径
	 * @param encode 
	 * 配置文件编码
	 */
	public AMSConfig(String configPath, String encode) {
		this.configPath = configPath;
		this.configEncode = encode;
		this.load();
	}
	/**
	 * 载入配置文件，若文件无法访问则复制模板文件内容并抛出错误，
	 * 若缺失某些必须量，则报错。
	 */
	private void load() {
		File configFile = new File(configPath);
		File configModelFile = new File(AMSConfig.class.getResource("configModel.json").getFile());
		Type type = new TypeToken<HashMap<String, Object>>(){}.getType();
		String modelEncode = "utf-8";
	/*
	 * TODO:自动创建目录
		if(!configFile.getParentFile().exists()) {
			LogUtil.getLogger().log(Level.INFO, Boolean.valueOf(configFile.getParentFile().mkdir()).toString());
		}
		*/
		if(!configFile.exists()) {
			try {
				configFile.createNewFile();
				byte[] buf = new byte[(int)configModelFile.length()];
				FileInputStream modin = new FileInputStream(configModelFile);
				FileOutputStream cfgout = new FileOutputStream(configFile);
				modin.read(buf);
				cfgout.write(buf);
				modin.close();
				cfgout.close();
				throw new AMSConfigException("Config file not exist");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		try {
			byte[] cfgbuf = new byte[(int)configFile.length()];
			byte[] modbuf = new byte[(int)configModelFile.length()];
			FileInputStream cfgin = new FileInputStream(configFile);
			FileInputStream modin = new FileInputStream(configModelFile);
			Gson gson = new Gson();
			cfgin.read(cfgbuf);
			modin.read(modbuf);
			String cfgJson = new String(cfgbuf, configEncode);
			String modJson = new String(modbuf, modelEncode);
			this.configs = gson.fromJson(cfgJson, type);
			Set<String> cfgKeys = this.configs.keySet();
			@SuppressWarnings("unchecked")
			Set<String> modelKeys = ((HashMap<String, Object>)gson.fromJson(modJson, type)).keySet();
			for(String key : modelKeys) {
				if(!cfgKeys.contains(key)) {
					cfgin.close();
					modin.close();
					throw new AMSConfigException("Not have enough config keys");
				}
			}
			cfgin.close();
			modin.close();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 *	通过属性名获取属性。
	 *	@param key
	 *	属性名
	 *	@return
	 *	属性值
	 */
	public Object get(String key) {
		return configs.get(key);
	}
	
	/**
	 *	通过属性名获取属性。
	 *	该方法调用get(String)方法并将返回值强制转换为int型并返回。
	 *	@param key
	 *	属性名
	 *	@return
	 *	属性值
	 */
	public int getInt(String key) {
		return ((Double)this.get(key)).intValue();
	}
	/**
	 *	通过属性名获取属性。
	 *	该方法调用get(String)方法并调用返回值的toString()方法，
	 *	若get(String)方法返回空指针，则抛出空指针异常。
	 *	@param key
	 *	属性名
	 *	@return
	 *	属性值
	 */	
	public String getString(String key) {
		return this.get(key).toString();
	}
	
	/**
	 *	获取配置文件地址。
	 *	@return
	 *	配置文件地址 
	 */
	public String getConfigPath() {
		return configPath;
	}
	/**
	 * 获取配置文件编码格式。
	 *	@return
	 *	配置文件编码格式
	 */
	public String getConfigEncode() {
		return configEncode;
	}
	
	public String toString() {
		StringBuilder str = new StringBuilder();
		str.append("configPath:").append(configPath).append("\n");
		str.append("configEncode:").append(configEncode).append("\n");
		for(String key : configs.keySet()) {
			str.append(key).append(":").append(configs.get(key)).append("\n");
		}
		return str.toString();
	}
}
