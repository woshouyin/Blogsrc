package exception;

public class ImportUserException extends Exception{
	private static final long serialVersionUID = 1L;
	public enum Type{NO_ID, NO_PASSWORD}
	private Type type;
	
	public ImportUserException(Type type) {
		this.type = type;
	}
	
	public Type getType() {
		return type;
	}
}
