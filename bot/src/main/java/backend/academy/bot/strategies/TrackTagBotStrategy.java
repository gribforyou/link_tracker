package backend.academy.bot.strategies;

import backend.academy.bot.app.ChatState;
import backend.academy.bot.app.StateOwner;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class TrackTagBotStrategy implements BotStrategy {
    private static final ChatState TARGET_STATE = ChatState.WAITING_TEG;
    private static final ChatState RESULT_STATE = ChatState.WAITING_FILTER;
    private static final String INSTRUCTION_MESSAGE = "Input filters or use command /scip";

    private final StateOwner stateOwner;
    private final TelegramBot bot;

    @Override
    public void applyStrategy(long id, String message) {
        stateOwner.putState(id, RESULT_STATE);
        stateOwner.putSavedTag(id, message);
        bot.execute(new SendMessage(id, INSTRUCTION_MESSAGE));
    }

    @Override
    public boolean supports(String message, ChatState state) {
        return TARGET_STATE.equals(state);
    }
}
