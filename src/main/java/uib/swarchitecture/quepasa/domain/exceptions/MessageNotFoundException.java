package uib.swarchitecture.quepasa.domain.exceptions;

public class MessageNotFoundException extends RuntimeException {
    public MessageNotFoundException(long id) {
        super("Message " + id + " not found");;
    }
}
