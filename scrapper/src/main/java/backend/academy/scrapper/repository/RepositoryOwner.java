package backend.academy.scrapper.repository;

import backend.academy.LinkDto;
import backend.academy.LinksDto;
import backend.academy.RemoveLinkDto;
import backend.academy.SavedLinkDto;
import backend.academy.scrapper.server.exceptions.ChatNotFoundException;
import backend.academy.scrapper.server.exceptions.UserLinkNotFoundException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import org.springframework.stereotype.Component;

@Component
public class RepositoryOwner {
    private final static long MOCK_ID = 1;
    private final Map<Long, Set<SavedLinkDto>> data;

    public RepositoryOwner() {
        this.data = new HashMap<>();
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
        Optional<SavedLinkDto> first = data.get(id).stream()
            .filter(l -> l.link().equals(removed.link()))
            .findFirst();

        if (first.isEmpty()) {
            throw new UserLinkNotFoundException(removed.link());
        }

        data.get(id).remove(first.get());
        return first.get();
    }
}
