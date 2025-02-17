package backend.academy.bot;

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

    public MessageHandler(TelegramBot bot, @Qualifier("sortedStrategies") List<BotStrategy> strategies) {
        this.bot = bot;
        this.strategies = strategies;
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
                    for (BotStrategy strategy : strategies) {
                        if (strategy.supports(messageText)) {
                            strategy.applyStrategy(id);
                            break;
                        }
                    }
                }
                return CONFIRMED_UPDATES_ALL;
            }
        });
    }
}
