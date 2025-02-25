package backend.academy.bot.strategies;

import backend.academy.bot.app.ChatState;
import backend.academy.bot.app.StateOwner;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class UntrackBotStrategy implements BotStrategy {
    private static final String COMMAND = "/untrack";
    private static final String INSTRUCTION_MESSAGE = "Input link you want to untrack";
    private static final ChatState TARGET_STATE = ChatState.READY;
    private static final ChatState RESULT_STATE = ChatState.WAITING_UNTRACKED_LINK;

    private final StateOwner stateOwner;
    private final TelegramBot telegramBot;

    @Override
    public void applyStrategy(long id, String message) {
        telegramBot.execute(new SendMessage(id, INSTRUCTION_MESSAGE));
        stateOwner.putState(id, RESULT_STATE);
    }

    @Override
    public boolean supports(String message, ChatState state) {
        return COMMAND.equals(message) && TARGET_STATE.equals(state);
    }
}
