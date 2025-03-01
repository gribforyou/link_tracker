package backend.academy.scrapper.clients;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public interface LinkClient {
    ObjectMapper mapper = null;

    String getLastUpdateTime(String link);

    boolean supports(String link);

    @SneakyThrows
    default String sendGetRequest(String link) {
        try (HttpClient httpClient = HttpClient.newHttpClient()) {
            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(link))
                .GET()
                .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            return response.body();
        }
    }

    @SneakyThrows
    default String getJsonField(String json, String fieldName) {
        JsonNode jsonNode = mapper.readTree(json);
        return jsonNode.get(fieldName).asText();
    }
}
