package root.service.exception;

public class SourceFormatException extends SourceException {

    public SourceFormatException() {}

    public SourceFormatException(String message) {
        super(message);
    }

    public SourceFormatException(String message, Throwable cause) {
        super(message, cause);
    }
}
