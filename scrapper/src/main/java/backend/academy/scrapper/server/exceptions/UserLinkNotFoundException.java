package backend.academy.scrapper.server.exceptions;

public class UserLinkNotFoundException extends RuntimeException {
    public UserLinkNotFoundException(String message) {
        super(message);
    }
}
