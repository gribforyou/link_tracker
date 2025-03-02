package backend.academy.scrapper.repository;

import backend.academy.LinkDto;
import backend.academy.LinksDto;
import backend.academy.RemoveLinkDto;
import backend.academy.SavedLinkDto;
import backend.academy.scrapper.server.exceptions.ChatNotFoundException;
import backend.academy.scrapper.server.exceptions.UserLinkNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import lombok.Getter;
import org.springframework.stereotype.Component;

@Getter
@Component
public class RepositoryOwner {
    private static final long MOCK_ID = 1;

    private final Map<Long, Set<SavedLinkDto>> data;
    private final Map<String, String> updates;

    public RepositoryOwner() {
        this.data = new HashMap<>();
        this.updates = new HashMap<>();
    }

    public void addChat(long id) {
        if (!data.containsKey(id)) {
            data.put(id, new HashSet<>());
        }
    }

    public void removeChat(long id) {
        if (!data.containsKey(id)) {
            throw new ChatNotFoundException(id);
        }
        data.remove(id);
    }

    public SavedLinkDto saveLink(long id, LinkDto link) throws ChatNotFoundException {
        if (!data.containsKey(id)) {
            throw new ChatNotFoundException(id);
        }

        SavedLinkDto savedLink = SavedLinkDto.of(MOCK_ID, link);
        data.get(id).add(savedLink);
        return savedLink;
    }

    public LinksDto getLinks(long id) {
        Set<SavedLinkDto> links = data.get(id);
        if (links == null) {
            throw new ChatNotFoundException(id);
        }
        SavedLinkDto[] array = links.toArray(SavedLinkDto[]::new);
        int length = array.length;
        return new LinksDto(length, array);
    }

    public SavedLinkDto removeLink(long id, RemoveLinkDto removed) {
        if (!data.containsKey(id)) {
            throw new ChatNotFoundException(id);
        }
        SavedLinkDto linkToRemove = data.get(id).stream()
                .filter(l -> l.link().equals(removed.link()))
                .findFirst()
                .orElseThrow(() -> new UserLinkNotFoundException(removed.link()));

        data.get(id).remove(linkToRemove);
        return linkToRemove;
    }

    public boolean isUpdated(String link, String update) {
        String curUpdate = updates.getOrDefault(link, null);
        if (curUpdate == null) {
            updates.put(link, update);
            return false;
        }

        if (!curUpdate.equals(update)) {
            updates.put(link, update);
            return true;
        }
        return false;
    }

    public List<PairLinkId> getPairs() {
        List<PairLinkId> pairs = new ArrayList<>();
        for (var entry : data.entrySet()) {
            Set<SavedLinkDto> links = entry.getValue();
            var key = entry.getKey();
            for (var link : links) {
                pairs.add(new PairLinkId(link, key));
            }
        }
        return pairs;
    }
}
