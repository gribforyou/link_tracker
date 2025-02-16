package backend.academy.bot.scrapper.communication;

public class ScrapperConnectionFailedException extends RuntimeException {
    public ScrapperConnectionFailedException(String message) {
        super(message);
    }
}
