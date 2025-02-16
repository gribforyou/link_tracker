package backend.academy.bot.scrapper.communication;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ScrapperClient {
    private final String baseUrl;
    private final HttpClient httpClient;

    @Autowired
    public ScrapperClient(ScrapperConfig config) {
        baseUrl = "http://" + config.scrapperHost() + ":" + config.scrapperPort();
        httpClient = HttpClient.newHttpClient();
    }

    public HttpResponse<String> registerChat(Long id) {
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(baseUrl + "/" + id))
            .POST(HttpRequest.BodyPublishers.noBody())
            .build();

        try {
            return httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new ScrapperConnectionFailedException(e.getMessage());
        }
    }
}
