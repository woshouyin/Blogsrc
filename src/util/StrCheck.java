package util;

import java.util.HashMap;
import java.util.regex.Pattern;

/**
 * 用于校验字符串格式
 */
public class StrCheck {
	private static HashMap<String, Pattern> PATTERNS = new HashMap<String, Pattern>();
	static {
		PATTERNS.put("PASSWORD", Pattern.compile("[a-zA-Z0-9!@#\\$%\\^&\\*\\(\\)_\\+-=\\[\\];'\\,\\./\\{\\}:\\\"<>\\? \\|\\\\]*"));
		PATTERNS.put("USER_NAME", Pattern.compile("[a-zA-Z0-9_]*"));
		PATTERNS.put("E_MAIL", Pattern.compile("[a-zA-Z0-9]*@[a-zA-Z0-9]*\\.[a-zA-Z]*"));
		PATTERNS.put("FILE_NAME", Pattern.compile("^[a-zA-Z0-9\\.\\-_]+$"));
	}
	public static boolean check(String mode, String str) {
		return PATTERNS.get(mode).matcher(str).matches();
	}
}
