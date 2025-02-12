package backend.academy.bot;

import com.pengrad.telegrambot.TelegramBot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JavaConfiguration {
    @Bean
    public TelegramBot telegramBot(BotConfig botConfig) {
        return new TelegramBot(botConfig.telegramToken());
    }
}
