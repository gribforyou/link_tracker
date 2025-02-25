package backend.academy.bot.app;

import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class StateOwner {
    private final Map<Long, ChatState> states;
    private final Map<Long, String> savedLinks;
    private final Map<Long, String> savedTags;

    public ChatState getState(Long chatId) {
        return states.getOrDefault(chatId, ChatState.NONE);
    }

    public void putState(Long chatId, ChatState state) {
        states.put(chatId, state);
    }

    public String getSavedLink(Long chatId) {
        return savedLinks.get(chatId);
    }

    public void putSavedLink(Long chatId, String link) {
        savedLinks.put(chatId, link);
    }

    public String getSavedTag(Long chatId) {
        return savedTags.get(chatId);
    }

    public void putSavedTag(Long chatId, String tag) {
        savedTags.put(chatId, tag);
    }

    public StateOwner() {
        states = new HashMap<>();
        savedLinks = new HashMap<>();
        savedTags = new HashMap<>();
    }
}
