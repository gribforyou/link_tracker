package backend.academy;

public record SavedLinkDto(
    long id,
    String link,
    String[] tags,
    String[] filters
) {
    public static SavedLinkDto of(long id, LinkDto link) {
        return new SavedLinkDto(id, link.link(), link.tags(), link.filters());
    }
}
