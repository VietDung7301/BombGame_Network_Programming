package exception;

public class InvalidResponseException extends RuntimeException {
	public InvalidResponseException() {
		super("Error: invalid response");
	}
	
	public InvalidResponseException(String message) {
		super("Error: " + message);
	}
}
