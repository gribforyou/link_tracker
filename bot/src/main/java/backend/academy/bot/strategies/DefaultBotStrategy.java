package backend.academy.bot.strategies;

import com.pengrad.telegrambot.TelegramBot;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class DefaultBotStrategy implements BotStrategy {
    private final TelegramBot bot;

    @Override
    public void applyStrategy(long id) {
        //send help message
    }

    @Override
    public boolean supports(String message) {
        return true;
    }
}
