package tech.emmyolusola.shortenurl.exception;

public class NoSpecifiedUrlException extends NullPointerException {
    public NoSpecifiedUrlException(String errorMessage) {
        super(errorMessage);
    }
}
