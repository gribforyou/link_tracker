package backend.academy.bot.strategies;

import com.pengrad.telegrambot.TelegramBot;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class StartBotStrategy implements BotStrategy {
    private final TelegramBot bot;

    @Override
    public void applyStrategy(long id) {
        //todo:
        //send Post /tg-chat/{id} request and show result
    }

    @Override
    public boolean supports(String message){
        return message.equals("/start");
    }
}
