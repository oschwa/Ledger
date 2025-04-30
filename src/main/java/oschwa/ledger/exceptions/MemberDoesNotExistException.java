package oschwa.ledger.exceptions;

public class MemberDoesNotExistException extends RuntimeException {
    public MemberDoesNotExistException(String errorMessage) {
        super(errorMessage);
    }
}
