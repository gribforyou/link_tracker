package backend.academy.bot.strategies;

public interface BotStrategy {
    void applyStrategy(long id);

    boolean supports(String message);
}
