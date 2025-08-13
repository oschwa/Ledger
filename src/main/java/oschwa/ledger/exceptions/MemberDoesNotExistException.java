package oschwa.ledger.exceptions;

public class MemberDoesNotExistException extends Exception {
    public MemberDoesNotExistException(String errorMessage) {
        super(errorMessage);
    }
}
