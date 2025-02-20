package backend.academy.bot;

import backend.academy.bot.strategies.BotStrategy;
import backend.academy.bot.strategies.DefaultBotStrategy;
import backend.academy.bot.strategies.HelpBotStrategy;
import backend.academy.bot.strategies.ListBotStrategy;
import backend.academy.bot.strategies.StartBotStrategy;
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
    public List<BotStrategy> botStrategiesList(
            StartBotStrategy start,
            ListBotStrategy list,
            HelpBotStrategy help,
            DefaultBotStrategy defaul) {
        List<BotStrategy> botStrategies = new ArrayList<>();
        botStrategies.add(start);
        botStrategies.add(list);
        botStrategies.add(help);
        botStrategies.add(defaul);
        return botStrategies;
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }
}
