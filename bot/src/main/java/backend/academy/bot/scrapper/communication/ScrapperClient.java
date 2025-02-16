package backend.academy.bot.scrapper.communication;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import backend.academy.RemoveLinkDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ScrapperClient {
    private final String baseUrl;
    private final HttpClient httpClient;
    private final ObjectMapper mapper;

    @Autowired
    public ScrapperClient(ScrapperConfig config, @Autowired ObjectMapper mapper) {
        baseUrl = "http://%s:%d".formatted(config.scrapperHost(), config.scrapperPort());
        httpClient = HttpClient.newHttpClient();
        this.mapper = mapper;
    }

    public HttpResponse<String> registerChat(long id) throws ScrapperConnectionFailedException {
        final String uri = String.format(baseUrl + "/tg-chat/%d", id);

        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(uri))
            .POST(HttpRequest.BodyPublishers.noBody())
            .build();

        try {
            return httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new ScrapperConnectionFailedException(e.getMessage());
        }
    }

    public HttpResponse<String> getLinks(long chatId) throws ScrapperConnectionFailedException {
        final String uri = String.format(baseUrl + "/links?Tg-Chat-Id=%d", chatId);

        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(uri))
            .GET()
            .build();

        try {
            return httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new ScrapperConnectionFailedException(e.getMessage());
        }
    }

    public HttpResponse<String> removeLink(long chatId, RemoveLinkDto removeLinkDto)
        throws ScrapperConnectionFailedException, JsonProcessingException {

        final String uri = String.format(baseUrl + "/links?Tg-Chat-Id=%d", chatId);

        String json = mapper.writeValueAsString(removeLinkDto);

        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(uri))
            .header("Content-Type", "application/json")
            .method("DELETE", HttpRequest.BodyPublishers.ofString(json))
            .build();

        try {
            return httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new ScrapperConnectionFailedException(e.getMessage());
        }
    }
}
