package backend.academy.clients;

import backend.academy.scrapper.clients.GithubRepoClient;
import backend.academy.scrapper.clients.LinkClient;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.lang.reflect.Method;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class GithubClientTest {
    private static LinkClient client;

    @BeforeAll
    public static void setUp() {
        client = new GithubRepoClient(new ObjectMapper());
    }

    @ParameterizedTest
    @CsvSource({
        "https://github.com/some_user/rep-rep_232323, true",
        "http://github.com/some_user/rep/something_else, false",
        "https://github.com/some_user/rep/, true",
        "github.com/user/user-repo-2xk, true",
        "github.com/user/user repo, false"
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
        "https://github.com/user/rep, https://api.github.com/repos/user/rep",
        "github.com/user/rep, https://api.github.com/repos/user/rep",
        "http://github.com/user/rep/, https://api.github.com/repos/user/rep"
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
