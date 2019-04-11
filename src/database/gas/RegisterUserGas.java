package database.gas;

import java.sql.Date;
import java.util.regex.Pattern;

import exception.IllegalPasswordException;
import exception.IllegalPasswordException.Type;

public class RegisterUserGas extends UserGas{
	private String password;
	private static Pattern pattern = Pattern.compile("[a-zA-Z0-9!@#\\$%\\^&\\*\\(\\)_\\+-=\\[\\];'\\,\\./\\{\\}:\\\"<>\\? \\|\\\\]*");
	private static Pattern patternNumCheck = Pattern.compile("[0-9]*");
	
	public RegisterUserGas(String name, String password) throws IllegalPasswordException {
		this(password);
		this.name = name;
		this.privilege = 0;
	}
	
	public RegisterUserGas(String password) throws IllegalPasswordException {
		if(!pattern.matcher(password).matches()) {
			throw new IllegalPasswordException(Type.ILLEGAL_CHARACTER);
		}else if(patternNumCheck.matcher(password).matches()) {
			throw new IllegalPasswordException(Type.ALL_NUM);
		}else if(password.length() > 18) {
			throw new IllegalPasswordException(Type.TOO_LONG);
		}else if(password.length() < 8) {
			throw new IllegalPasswordException(Type.TOO_SHORT);
		}else {
			this.password = password;
		}
	}
	
	public String getPassword() {
		return password;
	}

	public Date getRegDate() {
		return new java.sql.Date(new java.util.Date().getTime());
	}
	
	
}
