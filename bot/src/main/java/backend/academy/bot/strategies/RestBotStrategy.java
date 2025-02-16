package backend.academy.bot.strategies;

import backend.academy.bot.scrapper.communication.ScrapperClient;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
public abstract class RestBotStrategy implements BotStrategy {
    private final static String FAILURE_MESSAGE = "Something went wrong... Try again later!";

    protected final TelegramBot bot;
    protected final ObjectMapper mapper;
    protected final ScrapperClient scrapperClient;


    protected void sendFailureMessage(long id) {
        bot.execute(new SendMessage(id, FAILURE_MESSAGE));
    }

    protected void handleJsonProcessingException(long id, JsonProcessingException e) {
        sendFailureMessage(id);
        log.error("Can't parse json response", e);
    }
}
