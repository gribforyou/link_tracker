package backend.academy.bot;

import backend.academy.bot.strategies.BotStrategy;
import backend.academy.bot.strategies.DefaultBotStrategy;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pengrad.telegrambot.TelegramBot;
import java.util.ArrayList;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfiguration {
    @Bean
    public TelegramBot telegramBot(BotConfig botConfig) {
        return new TelegramBot(botConfig.telegramToken());
    }

    @Bean("sortedStrategies")
    public List<BotStrategy> botStrategiesList(List<BotStrategy> strategies, DefaultBotStrategy defaultBotStrategy) {
        List<BotStrategy> botStrategies = new ArrayList<>();
        for (BotStrategy strategy : strategies) {
            if (!(strategy instanceof DefaultBotStrategy)) {
                botStrategies.add(strategy);
            }
        }
        botStrategies.add(defaultBotStrategy);
        return botStrategies;
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }
}
