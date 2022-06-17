package tech.emmyolusola.shortenurl.exception;

public class ExpiredUrlException extends NullPointerException {
    public ExpiredUrlException(String errorMessage) {
        super(errorMessage);
    }
}
