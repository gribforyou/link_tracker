package backend.academy.scrapper.clients;

import java.util.regex.Pattern;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class StackOverClient implements LinkClient {
    private static final String STACK_QUESTION_URL_REGEX = "";

    @Override
    public String getLastUpdateTime(String link) {
        return "";
    }

    @Override
    public boolean supports(String link) {
        return Pattern.matches(STACK_QUESTION_URL_REGEX, link);
    }
}
