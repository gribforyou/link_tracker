package backend.academy;

public record ErrorDto(
    String description, String code, String exceptionName, String exceptionMessage, String[] stacktrace) {
}
