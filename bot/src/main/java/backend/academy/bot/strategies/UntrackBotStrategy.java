package backend.academy.bot.strategies;

import com.pengrad.telegrambot.TelegramBot;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class UntrackBotStrategy implements BotStrategy {
    private final TelegramBot bot;

    @Override
    public void applyStrategy(long id) {
        // todo
        // getLink()
        // send DELETE /links
    }

    @Override
    public boolean supports(String message) {
        return message.equals("/untrack");
    }
}
