package util;

public class UnitConUtil {
	public static final String[] UNITS = {"", "K", "M", "G", "T", "P"
					, "E", "Z", "Y", "B", "N", "D", "C"};
	
	public static String longToString(long num) {
		double nd = num;
		
		int count = 0;
		while(nd >= 1024) {
			nd = nd/1024;
			count++;
		}
		return String.format("%.2f", nd) + UNITS[count];
	}
}
