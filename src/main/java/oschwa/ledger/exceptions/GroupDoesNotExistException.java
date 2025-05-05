package oschwa.ledger.exceptions;

public class GroupDoesNotExistException extends RuntimeException {
    public GroupDoesNotExistException(String message) {
        super(message);
    }
}
