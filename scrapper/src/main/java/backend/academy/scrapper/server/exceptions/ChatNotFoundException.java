package backend.academy.scrapper.server.exceptions;

public class ChatNotFoundException extends RuntimeException {
    public ChatNotFoundException(long id) {
        super("id: " + id);
    }
}
