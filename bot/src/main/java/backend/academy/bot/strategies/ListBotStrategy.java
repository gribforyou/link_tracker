package backend.academy.bot.strategies;

import backend.academy.ErrorDto;
import backend.academy.LinksDto;
import backend.academy.bot.app.ChatState;
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
public class ListBotStrategy extends RestBotStrategy {
    private static final ChatState TARGET_STATE = ChatState.READY;
    private static final String COMMAND = "/list";
    private static final String LINKS_NOT_FOUND_MESSAGE =
        """
            There are no tracking links.
            Use /track command to add link.
            """;

    public ListBotStrategy(TelegramBot bot, ObjectMapper mapper, ScrapperClient scrapperClient) {
        super(bot, mapper, scrapperClient);
    }

    @Override
    public void applyStrategy(long id, String message) {
        HttpResponse<String> response;

        try {
            response = scrapperClient.getLinks(id);
        } catch (ScrapperConnectionFailedException e) {
            sendFailureMessage(id);
            log.error("Scrapper connection failed", e);
            return;
        }

        if (response.statusCode() == 200) {
            try {
                LinksDto linksDto = mapper.readValue(response.body(), LinksDto.class);
                sendLinks(linksDto, id);
            } catch (JsonProcessingException e) {
                handleJsonProcessingException(id, e);
            }
        } else {
            sendFailureMessage(id);
            try {
                ErrorDto error = mapper.readValue(response.body(), ErrorDto.class);
                final String report =
                    String.format("Failed to get links by id %d with description %s", id, error.description());
                log.error(report);
            } catch (JsonProcessingException e) {
                handleJsonProcessingException(id, e);
            }
        }
    }

    private void sendLinks(LinksDto linksDto, long id) {
        if (linksDto.size() == 0) {
            bot.execute(new SendMessage(id, LINKS_NOT_FOUND_MESSAGE));
        } else {
            StringBuilder linksBuilder = new StringBuilder();
            for (int i = 0; i < linksDto.size(); i++) {
                linksBuilder.append(linksDto.links()[i]);
                linksBuilder.append("\n");
            }
            bot.execute(new SendMessage(id, linksBuilder.toString()));
        }
    }

    @Override
    public boolean supports(String message, ChatState state) {
        return COMMAND.equals(message) && TARGET_STATE.equals(state);
    }
}
