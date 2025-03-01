package backend.academy.repository;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import backend.academy.LinkDto;
import backend.academy.LinksDto;
import backend.academy.RemoveLinkDto;
import backend.academy.scrapper.repository.RepositoryOwner;
import backend.academy.scrapper.server.exceptions.ChatNotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.function.Executable;

public class RepositoryOwnerTest {
    private final long id = 1;
    private final LinkDto testLink = new LinkDto("example.com", null, null);
    private RepositoryOwner repositoryOwner;

    @Before
    public void setUp() {
        repositoryOwner = new RepositoryOwner();
    }

    @Test
    public void testAddingAndRemovingChat() {
        // When
        repositoryOwner.addChat(id);
        Executable executable = () -> {
            repositoryOwner.removeChat(id);
        };

        // Then
        assertDoesNotThrow(executable);
        assertThrows(ChatNotFoundException.class, executable);
    }

    @Test
    public void testAddingLinks() {
        // When
        repositoryOwner.addChat(id);
        repositoryOwner.saveLink(id, testLink);
        repositoryOwner.saveLink(id, testLink);
        LinksDto links = repositoryOwner.getLinks(id);

        // Then
        assertEquals(1, links.size());
        assertEquals(testLink.link(), links.links()[0].link());
    }

    @Test
    public void testRemovingLinks() {
        // When
        repositoryOwner.addChat(id);
        repositoryOwner.saveLink(id, testLink);
        repositoryOwner.removeLink(id, new RemoveLinkDto(testLink.link()));
        LinksDto links = repositoryOwner.getLinks(id);

        // Then
        assertEquals(0, links.size());
    }
}
