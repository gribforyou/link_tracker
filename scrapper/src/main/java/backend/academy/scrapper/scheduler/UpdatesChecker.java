package backend.academy.scrapper.scheduler;

import backend.academy.UpdateDto;
import backend.academy.scrapper.clients.LinkClient;
import backend.academy.scrapper.repository.PairLinkId;
import backend.academy.scrapper.repository.RepositoryOwner;
import backend.academy.scrapper.update_sender.UpdateSender;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class UpdatesChecker {
    private static final String UPDATE_DESCRIPTION = "New update from %s on %s";
    private List<LinkClient> clients;
    private RepositoryOwner repository;
    private UpdateSender updateSender;

    @Scheduled(fixedDelay = 15000)
    public void checkUpdates() {
        repository.getPairs().forEach(pair -> {
            String update = getUpdate(pair.savedLink().link());
            if (!(update == null)) {
                UpdateDto updateDto = getUpdateDto(pair, update);
                if (repository.isUpdated(pair.savedLink().link(), update)) {
                    updateSender.send(updateDto);
                }
            }
        });
    }

    private UpdateDto getUpdateDto(PairLinkId pair, String dataString) {
        final String description = UPDATE_DESCRIPTION.formatted(pair.savedLink().link(), dataString);
        return new UpdateDto(
                (int) pair.savedLink().id(), pair.savedLink().link(), description, new long[] {pair.chatId()});
    }

    private String getUpdate(String link) {
        for (LinkClient client : clients) {
            if (client.supports(link)) {
                return client.getLastUpdateTime(link);
            }
        }
        return null;
    }
}
