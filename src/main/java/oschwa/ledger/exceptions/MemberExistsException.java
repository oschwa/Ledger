package oschwa.ledger.exceptions;

public class MemberExistsException extends RuntimeException {
    public MemberExistsException(String errorMessage) {
        super(errorMessage);
    }
}
