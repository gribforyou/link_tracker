package backend.academy.scrapper.server;

import backend.academy.LinkDto;
import backend.academy.LinksDto;
import backend.academy.RemoveLinkDto;
import backend.academy.SavedLinkDto;
import backend.academy.scrapper.repository.RepositoryOwner;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class BotServer {
    private final RepositoryOwner repositoryOwner;

    @PostMapping("/tg-chat/{id}")
    void registerChat(@PathVariable long id) {
        repositoryOwner.addChat(id);
    }

    @DeleteMapping("/tg-chat/{id}")
    void removeChat(@PathVariable long id) {
        repositoryOwner.removeChat(id);
    }

    @PostMapping("/links")
    SavedLinkDto addLink(@RequestParam("Tg-Chat-Id") long id, @RequestBody LinkDto linkDto) {
        return repositoryOwner.saveLink(id, linkDto);
    }

    @GetMapping("/links")
    LinksDto getLinks(@RequestParam("Tg-Chat-Id") long id) {
        return repositoryOwner.getLinks(id);
    }

    @DeleteMapping("/links")
    SavedLinkDto removeLink(@RequestParam("Tg-Chat-Id") long id, @RequestBody RemoveLinkDto removeLink) {
        return repositoryOwner.removeLink(id, removeLink);
    }
}
