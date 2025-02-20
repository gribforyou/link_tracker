package backend.academy.bot.app;

import backend.academy.bot.strategies.BotStrategy;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import jakarta.annotation.PostConstruct;
import java.util.List;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class MessageHandler {
    private final TelegramBot bot;
    private final List<BotStrategy> strategies;
    private final StateOwner stateOwner;

    public MessageHandler(TelegramBot bot, @Qualifier("sortedStrategies") List<BotStrategy> strategies,
                          StateOwner stateOwner) {
        this.bot = bot;
        this.strategies = strategies;
        this.stateOwner = stateOwner;
    }

    @PostConstruct
    private void initBot() {
        bot.setUpdatesListener(new UpdatesListener() {
            @Override
            public int process(List<Update> list) {
                for (Update update : list) {
                    Message message = update.message();
                    String messageText = message.text();
                    Long id = message.chat().id();
                    ChatState state = stateOwner.getState(id);
                    BotStrategy validStrategy = chooseStrategy(messageText, state);
                    validStrategy.applyStrategy(id, messageText);
                }
                return CONFIRMED_UPDATES_ALL;
            }
        });
    }

    private BotStrategy chooseStrategy(String messageText, ChatState state) {
        for (BotStrategy strategy : strategies) {
            if (strategy.supports(messageText, state)) {
                return strategy;
            }
        }
        return null;
    }
}
