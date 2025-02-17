package backend.academy.bot.strategies;

import backend.academy.ErrorDto;
import backend.academy.bot.scrapper.communication.ScrapperClient;
import backend.academy.bot.scrapper.communication.ScrapperConnectionFailedException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;
import java.net.http.HttpResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class StartBotStrategy extends RestBotStrategy {
    private static final String COMMAND = "/start";

    private static final String SUCCESS_MESSAGE =
            """
            This chat is successfully registered!
            Use /help to see the list of available commands!
            """;

    public StartBotStrategy(TelegramBot bot, ObjectMapper mapper, ScrapperClient scrapperClient) {
        super(bot, mapper, scrapperClient);
    }

    @Override
    public void applyStrategy(long id) {
        HttpResponse<String> response = null;

        try {
            response = scrapperClient.registerChat(id);
        } catch (ScrapperConnectionFailedException e) {
            sendFailureMessage(id);
            log.error("Scrapper connection failure", e);
            return;
        }

        if (response.statusCode() == 200) {
            bot.execute(new SendMessage(id, SUCCESS_MESSAGE));
            final String report = String.format("Successfully registered chat with id %d", id);
            log.info(report);
        } else {
            try {
                sendFailureMessage(id);
                ErrorDto error = mapper.readValue(response.body(), ErrorDto.class);
                final String report = String.format(
                        "Failed to register chat with id %d with description %s", id, error.description());
                log.error(report);
            } catch (JsonProcessingException e) {
                handleJsonProcessingException(id, e);
            }
        }
    }

    @Override
    public boolean supports(String message) {
        return COMMAND.equals(message);
    }
}
