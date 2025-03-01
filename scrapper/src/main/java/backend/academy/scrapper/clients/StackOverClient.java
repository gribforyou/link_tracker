package backend.academy.scrapper.clients;

public class StackOverClient implements LinkClient {
    @Override
    public String getLastUpdateTime(String link) {
        return "";
    }

    @Override
    public boolean supports(String link) {
        return false;
    }
}
