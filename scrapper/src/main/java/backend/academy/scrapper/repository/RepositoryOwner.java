package backend.academy.scrapper.repository;

import org.springframework.stereotype.Component;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Component
public class RepositoryOwner {
    private final Set<Integer> chatIds;
    private final Map<String, Set<Integer>> links;

    public RepositoryOwner() {
        this.chatIds = new HashSet<>();
        this.links = new HashMap<>();
    }

    public void addChatId(int id){
        this.chatIds.add(id);
    }

    public void addLink(int id, String link){
        if(!chatIds.contains(id)){
            throw new IllegalArgumentException("Unknown chat id");
        }

        if(!links.containsKey(link)){
            links.put(link, new HashSet<>(id));
        }
        else{
            links.get(link).add(id);
        }
    }
}
