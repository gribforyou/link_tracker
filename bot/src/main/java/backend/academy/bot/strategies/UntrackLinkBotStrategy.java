package backend.academy.bot.strategies;

import backend.academy.RemoveLinkDto;
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
public class UntrackLinkBotStrategy extends RestBotStrategy {
    private final static ChatState TARGET_STATE = ChatState.WAITING_UNTRACKED_LINK;
    private final static ChatState RESULT_STATE = ChatState.READY;
    private final static String SUCCESS_MESSAGE = "Link is successfully untracked";
    private final static String UNKNOWN_LINK_MESSAGE = "This link was not found in tracked";

    private final StateOwner stateOwner;

    public UntrackLinkBotStrategy(TelegramBot bot, ObjectMapper mapper, ScrapperClient scrapperClient, StateOwner stateOwner) {
        super(bot, mapper, scrapperClient);
        this.stateOwner = stateOwner;
    }


    @Override
    public void applyStrategy(long id, String message) {
        RemoveLinkDto removeLinkDto = new RemoveLinkDto(message);
        HttpResponse<String> response = null;
        try {
            response = scrapperClient.removeLink(id, removeLinkDto);
        } catch (ScrapperConnectionFailedException e) {
            sendFailureMessage(id);
            log.error("Scrapper connection failed", e);
            return;
        } catch (JsonProcessingException e) {
            handleJsonProcessingException(id, e);
            return;
        }

        if (response.statusCode() == 200) {
            bot.execute(new SendMessage(id, SUCCESS_MESSAGE));
        } else if (response.statusCode() == 400) {
            sendFailureMessage(id);
        } else if (response.statusCode() == 404) {
            bot.execute(new SendMessage(id, UNKNOWN_LINK_MESSAGE));
        }
        stateOwner.putState(id, RESULT_STATE);
    }

    @Override
    public boolean supports(String message, ChatState state) {
        return TARGET_STATE.equals(state);
    }
}
