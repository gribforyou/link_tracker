package backend.academy.bot.strategies;

import backend.academy.bot.app.ChatState;

public interface BotStrategy {
    void applyStrategy(long id, String message);

    boolean supports(String message, ChatState state);
}
