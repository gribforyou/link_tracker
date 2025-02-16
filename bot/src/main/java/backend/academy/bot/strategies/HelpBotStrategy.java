package backend.academy.bot.strategies;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class HelpBotStrategy implements BotStrategy {
    private final TelegramBot bot;
    private final static String COMMAND = "/help";
    private final static String HELP_MESSAGE = """
        To communicate with bot use following commands:
        \t/track - for adding link to tracked
        \t/untrack - for removing link from tracked
        \t/list - for listing tracked links
        \t/help - fot listing commands
        """;

    @Override
    public void applyStrategy(long id) {
        bot.execute(new SendMessage(id, HELP_MESSAGE));
    }

    @Override
    public boolean supports(String message) {
        return COMMAND.equals(message);
    }
}
