package backend.academy.bot.strategies;

import backend.academy.bot.app.ChatState;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class HelpBotStrategy implements BotStrategy {
    private static final ChatState TARGET_STATE = ChatState.READY;
    private static final String COMMAND = "/help";
    private static final String HELP_MESSAGE =
            """
            To communicate with bot use following commands:
            \t/track - for adding link to tracked
            \t/untrack - for removing link from tracked
            \t/list - for listing tracked links
            \t/help - fot listing commands
            """;

    private final TelegramBot bot;

    @Override
    public void applyStrategy(long id, String message) {
        bot.execute(new SendMessage(id, HELP_MESSAGE));
    }

    @Override
    public boolean supports(String message, ChatState state) {
        return COMMAND.equals(message) && TARGET_STATE.equals(state);
    }
}
