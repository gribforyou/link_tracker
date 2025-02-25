package backend.academy.scrapper.server;

import backend.academy.ErrorDto;
import backend.academy.scrapper.repository.RepositoryOwner;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Arrays;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
public class BotServer {
    private final RepositoryOwner repositoryOwner;
    private final ObjectMapper objectMapper;

    @PostMapping("/tg-chat/{id}")
    ResponseEntity<String> registerChat(@PathVariable String id) throws JsonProcessingException {
        try {
            final int chatId = Integer.parseInt(id);
            repositoryOwner.addChatId(chatId);
            return ResponseEntity.ok().build();
        } catch (NumberFormatException e) {
            final String description = "Wrong id";
            ErrorDto dto = parseException(e, description, null);
            String json = writeObjectToJson(dto);
            return ResponseEntity.badRequest()
                .contentType(MediaType.APPLICATION_JSON)
                .body(json);
        }
    }


    private ErrorDto parseException(final Exception e, String description, String code) {
        String exceptionName = e.getClass().getSimpleName();
        String exceptionMessage = e.getMessage();
        String[] stackTrace = parseStackTrace(e.getStackTrace());
        return new ErrorDto(description, code, exceptionName, exceptionMessage, stackTrace);
    }

    private String[] parseStackTrace(StackTraceElement[] stackTrace) {
        return Arrays.stream(stackTrace)
            .map(StackTraceElement::toString)
            .toArray(String[]::new);
    }

    private String writeObjectToJson(final Object object) throws JsonProcessingException {
        return objectMapper.writeValueAsString(object);
    }
}
