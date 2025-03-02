package backend.academy;

public record UpdateDto(int id, String url, String description, long[] chatIds) {}
