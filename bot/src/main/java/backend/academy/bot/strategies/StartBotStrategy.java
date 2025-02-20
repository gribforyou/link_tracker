package backend.academy.bot.strategies;

import backend.academy.ErrorDto;
import backend.academy.bot.app.ChatState;
import backend.academy.bot.app.StateOwner;
import backend.academy.bot.com.scrapper.ScrapperClient;
import backend.academy.bot.com.scrapper.ScrapperConnectionFailedException;
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
    private static final ChatState TARGET_STATE = ChatState.NONE;
    private static final String SUCCESS_MESSAGE =
            """
            This chat is successfully registered!
            Use /help to see the list of available commands!
            """;

    private final StateOwner stateOwner;

    public StartBotStrategy(TelegramBot bot, ObjectMapper mapper,
                            ScrapperClient scrapperClient, StateOwner stateOwner) {
        super(bot, mapper, scrapperClient);
        this.stateOwner = stateOwner;
    }

    @Override
    public void applyStrategy(long id, String message) {
        HttpResponse<String> response = null;

        try {
            response = scrapperClient.registerChat(id);
        } catch (ScrapperConnectionFailedException e) {
            sendFailureMessage(id);
            log.error("Scrapper connection failure", e);
            return;
        }

        if (response.statusCode() == 200) {
            stateOwner.putState(id, ChatState.READY);
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
    public boolean supports(String message, ChatState state) {
        return COMMAND.equals(message) && TARGET_STATE.equals(state);
    }
}
