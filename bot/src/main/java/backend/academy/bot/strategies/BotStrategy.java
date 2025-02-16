package backend.academy.bot.strategies;

import com.pengrad.telegrambot.TelegramBot;
import org.springframework.beans.factory.annotation.Autowired;

public interface BotStrategy {
    void applyStrategy(long id);
    boolean supports(String message);
}
