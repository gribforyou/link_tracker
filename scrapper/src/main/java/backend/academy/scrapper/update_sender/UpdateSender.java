package backend.academy.scrapper.update_sender;

import backend.academy.UpdateDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor
public class UpdateSender {
    private static final String TARGET_URL = "http://%s:%s/updates";
    private UpdateSenderConfig config;
    private ObjectMapper mapper;

    @SneakyThrows
    public void send(UpdateDto update) {
        String json = mapper.writeValueAsString(update);

        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(TARGET_URL.formatted(config.botUrl, config.port)))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();

        var response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() == 400) {
            String description =
                    mapper.readTree(response.body()).get("description").asText();
            log.error("Error sending update. Description: {}", description);
        }
    }
}
