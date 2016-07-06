package root.service.exception;


public class WriteResultException extends ServiceException {

    public WriteResultException() {}

    public WriteResultException(String message) {
        super(message);
    }

    public WriteResultException(String message, Throwable cause) {
        super(message, cause);
    }
}
