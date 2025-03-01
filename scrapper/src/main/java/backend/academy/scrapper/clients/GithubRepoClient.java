package backend.academy.scrapper.clients;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.regex.Pattern;
import org.springframework.stereotype.Component;

@Component
public class GithubRepoClient extends LinkClient {
    private static final String GITHUB_REPO_URL_REGEX = "(https?://)?github\\.com/\\w+/[\\w-]+/?";
    private static final String API_URL_TEMPLATE = "https://api.github.com/repos/%s/%s";
    private static final String FIELD_NAME = "updated_at";

    public GithubRepoClient(ObjectMapper mapper) {
        super(mapper);
    }

    @Override
    public String getLastUpdateTime(String link) {
        if (!supports(link)) {
            throw new IllegalArgumentException("Link doesn't support pattern");
        }

        String url = getApiUrl(link);
        String json = sendGetRequest(url);
        return getJsonField(json, FIELD_NAME);
    }

    @Override
    public boolean supports(String link) {
        return Pattern.matches(GITHUB_REPO_URL_REGEX, link);
    }

    private String getApiUrl(String link) {
        final String regex = "/";
        final String beforeUser = "github.com";
        String[] split = link.split(regex);
        int index = -1;
        for (int i = 0; i < split.length; i++) {
            if (split[i].equals(beforeUser)) {
                index = i;
                break;
            }
        }
        if (index == -1 || index > split.length - 3) {
            throw new IllegalArgumentException("Link doesn't support pattern");
        }
        String user = split[index + 1];
        String repo = split[index + 2];
        return String.format(API_URL_TEMPLATE, user, repo);
    }
}
