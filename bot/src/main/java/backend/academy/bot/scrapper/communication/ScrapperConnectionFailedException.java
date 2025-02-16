package backend.academy.bot.scrapper.communication;

public class ScrapperConnectionFailedException extends Exception {
    public ScrapperConnectionFailedException(String message) {
        super(message);
    }
}
