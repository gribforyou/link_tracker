package backend.academy.bot.strategies;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class DefaultBotStrategy implements BotStrategy {
    private final TelegramBot bot;
    private static final String DEFAULT_MESSAGE =
            """
        Bot doesn't understand your command(
        Please, use /help command for listing commands
        """;

    @Override
    public void applyStrategy(long id) {
        bot.execute(new SendMessage(id, DEFAULT_MESSAGE));
    }

    @Override
    public boolean supports(String message) {
        return true;
    }
}
