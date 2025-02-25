package backend.academy.scrapper.repository;

import backend.academy.LinkDto;
import backend.academy.LinksDto;
import backend.academy.RemoveLinkDto;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Component
public class RepositoryOwner {
    private final Map<Long, Set<LinkDto>> data;

    public RepositoryOwner() {
        this.data = new HashMap<>();
    }

    public void addChat(long id) {
        if (!data.containsKey(id)) {
            data.put(id, new HashSet<>());
        }
    }

    public void removeChat(long id) {
        data.remove(id);
    }

    public void addLink(long id, LinkDto link) {
        if (!data.containsKey(id)) {
            throw new IllegalArgumentException("Unknown id");
        }
        data.get(id).add(link);
    }

    public LinksDto getLinks(long id) {
        Set<LinkDto> links = data.get(id);
        if (links == null) {
            throw new IllegalArgumentException("Unknown id");
        }
        LinkDto[] array = links.toArray(LinkDto[]::new);
        int length = array.length;
        return new LinksDto(length, array);
    }

    public void removeLink(long id, RemoveLinkDto link) {
        if (!data.containsKey(id)) {
            throw new IllegalArgumentException("Unknown id");
        }
        data.get(id).removeIf(l -> l.url().equals(link.link()));
    }
}
