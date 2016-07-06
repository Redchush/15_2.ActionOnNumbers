package root.service.exception;

public class SourceException extends ServiceException {
    public SourceException() {
    }

    public SourceException(String message) {
        super(message);
    }

    public SourceException(String message, Throwable cause) {
        super(message, cause);
    }
}
