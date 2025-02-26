package backend.academy;

import java.util.Arrays;

public record ErrorDto(
        String description,
        String code,
        String exceptionName,
        String exceptionMessage,
        String[] stacktrace
) {
    public static ErrorDto of(String description, String code, Exception e) {
        final String exceptionName = e.getClass().getSimpleName();
        final String exceptionMessage = e.getMessage();
        final String[] stacktrace = Arrays.stream(e.getStackTrace())
            .map(StackTraceElement::toString)
            .toArray(String[]::new);
        return new ErrorDto(description, code, exceptionName, exceptionMessage, stacktrace);
    }
}
