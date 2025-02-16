package backend.academy.bot.strategies;

import backend.academy.ErrorDto;
import backend.academy.bot.scrapper.communication.ScrapperClient;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;
import java.net.http.HttpResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor
public class StartBotStrategy implements BotStrategy {
    private final static String COMMAND = "/help";
    private final static String SUCCESS_MESSAGE = """
        This chat is successfully registered!
        Use /help to see the list of available commands!
        """;
    private final static String FAILURE_MESSAGE = """
        Failed to register a new chat!
        Try again later!
        """;

    private final TelegramBot bot;
    private final ScrapperClient scrapperClient;
    private final ObjectMapper objectMapper;

    @Override
    public void applyStrategy(long id) {
        HttpResponse<String> response = scrapperClient.registerChat(id);
        if (response.statusCode() == 200) {
            bot.execute(new SendMessage(id, SUCCESS_MESSAGE));
            log.info("Successfully registered chat with id %d".formatted(id));
        } else {
            try {
                bot.execute(new SendMessage(id, FAILURE_MESSAGE));
                ErrorDto error = objectMapper.readValue(response.body(), ErrorDto.class);
                log.error("Failed to register chat with id %d with description %s".formatted(id, error.description()));
            } catch (JsonProcessingException e) {
                log.error("Can't parse json response", e);
            }
        }
    }

    @Override
    public boolean supports(String message) {
        return COMMAND.equals(message);
    }
}
