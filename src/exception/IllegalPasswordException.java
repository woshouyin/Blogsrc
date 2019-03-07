package exception;

public class IllegalPasswordException extends Exception{
	private static final long serialVersionUID = 1L;
	public enum Type{ILLEGAL_CHARACTER, TOO_LONG, TOO_SHORT, ALL_NUM}
	private Type type;
	
	public IllegalPasswordException(Type type) {
		super();
		this.type = type;
	}
	
	public Type getType() {
		return type;
	}
}
