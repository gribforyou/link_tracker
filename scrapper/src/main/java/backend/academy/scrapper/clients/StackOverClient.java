package backend.academy.scrapper.clients;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;
import org.springframework.stereotype.Component;

@Component
public class StackOverClient extends LinkClient {
    private static final String STACK_QUESTION_URL_REGEX = "(https?://)?stackoverflow\\.com/questions/[1-9]\\d*(/.*)?";
    private static final String STACK_API_TEMPLATE = "https://api.stackexchange.com/2.3/questions/%s?site=stackoverflow";
    public static final String OUTER_FIELD_NAME = "items";
    public static final String INNER_FIELD_NAME = "last_activity_date";

    public StackOverClient(ObjectMapper mapper) {
        super(mapper);
    }

    @Override
    public String getLastUpdateTime(String link) {
        if (!supports(link)) {
            throw new IllegalArgumentException("Link doesn't support pattern");
        }

        String url = getApiUrl(link);
        String json = sendGetRequest(url);
        String notFormatted = getJsonField(json, OUTER_FIELD_NAME, INNER_FIELD_NAME);
        return parseDate(notFormatted);
    }

    @Override
    public boolean supports(String link) {
        return Pattern.matches(STACK_QUESTION_URL_REGEX, link);
    }

    private String getApiUrl(String link) {
        final String regex = "/";
        final String beforeUser = "questions";
        String[] split = link.split(regex);
        int index = -1;
        for (int i = 0; i < split.length; i++) {
            if (split[i].equals(beforeUser)) {
                index = i;
                break;
            }
        }
        if (index == -1 || index > split.length - 2) {
            throw new IllegalArgumentException("Link doesn't support pattern");
        }
        String chatId = split[index + 1];
        return String.format(STACK_API_TEMPLATE.formatted(chatId), link);
    }

    private String parseDate(String date) {
        long seconds = Long.parseLong(date);
        Date d = new Date(seconds * 1000);
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return df.format(d);
    }
}
