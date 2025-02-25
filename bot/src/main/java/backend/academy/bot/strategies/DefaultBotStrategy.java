package backend.academy.bot.strategies;

import backend.academy.bot.app.ChatState;
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
            Unknown command or command not supported in current state.
            Please, use /help command for listing commands
            """;

    @Override
    public void applyStrategy(long id, String message) {
        bot.execute(new SendMessage(id, DEFAULT_MESSAGE));
    }

    @Override
    public boolean supports(String message, ChatState state) {
        return true;
    }
}
