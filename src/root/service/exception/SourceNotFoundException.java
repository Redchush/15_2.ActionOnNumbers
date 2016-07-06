package root.service.exception;

public class SourceNotFoundException extends SourceException {

    public SourceNotFoundException() {
    }

    public SourceNotFoundException(String message) {
        super(message);
    }

    public SourceNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
