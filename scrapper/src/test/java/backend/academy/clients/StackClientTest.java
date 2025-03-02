package backend.academy.clients;

import backend.academy.scrapper.clients.LinkClient;
import backend.academy.scrapper.clients.StackOverClient;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.lang.reflect.Method;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class StackClientTest {
    private static LinkClient client;

    @BeforeAll
    public static void setUp() {
        client = new StackOverClient(new ObjectMapper());
    }

    @ParameterizedTest
    @CsvSource({
        "https://stackoverflow.com/questions/123, true",
        "http://stackoverflow.com/questions/123/, true",
        "https://stackoverflow.com/questions/123/blabla, true",
        "stackoverflow.com/questions/123, true",
        "stackoverflow.com/questions/123/blalba bla, false",
        "https://stackoverflow.com/questions/79479448/how-to-safely-inspect-flutter-dependency-tree, true"
    })
    public void testSupportMethod(String link, boolean expected) {
        // When
        boolean result = client.supports(link);

        // Then
        Assertions.assertEquals(expected, result);
    }

    @SneakyThrows
    @ParameterizedTest
    @CsvSource({
        "stackoverflow.com/questions/123, https://api.stackexchange.com/2.3/questions/123?site=stackoverflow",
        "https://stackoverflow.com/questions/123/blabla, https://api.stackexchange.com/2.3/questions/123?site=stackoverflow",
    })
    public void testApiUrl(String link, String expected) {
        // Given
        Method getApiUrl = client.getClass().getDeclaredMethod("getApiUrl", String.class);
        getApiUrl.setAccessible(true);

        // When
        String result = (String) getApiUrl.invoke(client, link);

        // Then
        Assertions.assertEquals(expected, result);
    }
}
