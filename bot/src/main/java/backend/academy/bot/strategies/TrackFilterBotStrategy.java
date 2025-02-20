package backend.academy.bot.strategies;

import backend.academy.LinkDto;
import backend.academy.bot.app.ChatState;
import backend.academy.bot.app.StateOwner;
import backend.academy.bot.com.scrapper.ScrapperClient;
import backend.academy.bot.com.scrapper.ScrapperConnectionFailedException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;
import java.net.http.HttpResponse;
import org.springframework.stereotype.Component;

@Component
public class TrackFilterBotStrategy extends RestBotStrategy {
    private final static ChatState TARGET_STATE = ChatState.WAITING_FILTER;
    private final static ChatState RESULT_STATE = ChatState.READY;
    private final static String WRONG_REQUEST_MESSAGE = "Some data is invalid. Try again";
    private final static String SUCCESS_MESSAGE = "Link untracked successfully";

    private final StateOwner stateOwner;

    public TrackFilterBotStrategy(TelegramBot bot, ObjectMapper mapper,
                                  ScrapperClient scrapperClient, StateOwner stateOwner) {
        super(bot, mapper, scrapperClient);
        this.stateOwner = stateOwner;
    }

    @Override
    public void applyStrategy(long id, String message) {
        String link = stateOwner.getSavedLink(id);
        String tag = stateOwner.getSavedTag(id);
        LinkDto dto = new LinkDto(id, link, tag.split(" "), message.split(" "));

        HttpResponse<String> response = null;
        try {
            response = scrapperClient.addLink(id, dto);
        } catch (ScrapperConnectionFailedException e) {
            sendFailureMessage(id);
            return;
        } catch (JsonProcessingException e) {
            handleJsonProcessingException(id, e);
            return;
        }

        if (response.statusCode() == 200) {
            bot.execute(new SendMessage(id, SUCCESS_MESSAGE));
        } else {
            bot.execute(new SendMessage(id, WRONG_REQUEST_MESSAGE));
        }
        stateOwner.putState(id, RESULT_STATE);
    }

    @Override
    public boolean supports(String message, ChatState state) {
        return TARGET_STATE.equals(state);
    }
}
