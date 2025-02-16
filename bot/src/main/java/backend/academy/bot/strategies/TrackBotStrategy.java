package backend.academy.bot.strategies;

import com.pengrad.telegrambot.TelegramBot;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class TrackBotStrategy implements BotStrategy {
    private final TelegramBot bot;

    @Override
    public void applyStrategy(long id) {
        //todo
        //getLink()
        //send Post /links
    }

    @Override
    public boolean supports(String message) {
        return message.equals("/track");
    }
}
