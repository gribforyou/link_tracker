package backend.academy.bot;

import backend.academy.bot.strategies.BotStrategy;
import backend.academy.bot.strategies.DefaultBotStrategy;
import backend.academy.bot.strategies.HelpBotStrategy;
import backend.academy.bot.strategies.ListBotStrategy;
import backend.academy.bot.strategies.StartBotStrategy;
import backend.academy.bot.strategies.TrackBotStrategy;
import backend.academy.bot.strategies.UntrackBotStrategy;
import com.pengrad.telegrambot.TelegramBot;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class JavaConfiguration {
    @Bean
    public TelegramBot telegramBot(BotConfig botConfig) {
        return new TelegramBot(botConfig.telegramToken());
    }

    @Bean(name = "botStrategiesList")
    public List<BotStrategy> botStrategiesList(TelegramBot bot) {
        List<BotStrategy> botStrategies = new ArrayList<>();
        botStrategies.add(new StartBotStrategy(bot));
        botStrategies.add(new TrackBotStrategy(bot));
        botStrategies.add(new UntrackBotStrategy(bot));
        botStrategies.add(new ListBotStrategy(bot));
        botStrategies.add(new HelpBotStrategy(bot));
        //maybe some other strategies
        botStrategies.addLast(new DefaultBotStrategy(bot));
        return botStrategies;
    }
}
