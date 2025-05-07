package oschwa.ledger.exceptions;

public class GroupExistsException extends RuntimeException {
    public GroupExistsException(String message) {
        super(message);
    }
}
