package backend.academy.scrapper.clients;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;

@AllArgsConstructor
public abstract class LinkClient {
    private final ObjectMapper mapper;

    public abstract String getLastUpdateTime(String link);

    public abstract boolean supports(String link);

    @SneakyThrows
    protected String sendGetRequest(String link) {
        try (HttpClient httpClient = HttpClient.newHttpClient()) {
            HttpRequest request =
                    HttpRequest.newBuilder().uri(URI.create(link)).GET().build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            return response.body();
        }
    }

    @SneakyThrows
    protected String getJsonField(String json, String... strings) {
        JsonNode jsonNode = mapper.readTree(json);
        for (String string : strings) {
            while (jsonNode.isArray()) {
                jsonNode = jsonNode.get(0);
            }
            jsonNode = jsonNode.get(string);
        }
        return jsonNode.asText();
    }
}
