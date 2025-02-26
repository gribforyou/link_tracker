package backend.academy.scrapper.server;

import backend.academy.ErrorDto;
import backend.academy.scrapper.server.exceptions.ChatNotFoundException;
import backend.academy.scrapper.server.exceptions.UserLinkNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDto wrongTypeHandler(MethodArgumentTypeMismatchException e) {
        final String description = "Wrong argument type";
        final String code = "400";
        return ErrorDto.of(description, code, e);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDto wrongTypeHandler(HttpMessageNotReadableException e) {
        final String description = "Wrong request body";
        final String code = "400";
        return ErrorDto.of(description, code, e);
    }

    @ExceptionHandler(ChatNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorDto handleChatNotFoundException(ChatNotFoundException e) {
        final String description = "Chat with this id not found";
        final String code = "404";
        return ErrorDto.of(description, code, e);
    }

    @ExceptionHandler(UserLinkNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorDto handleUserLinkNotFoundException(UserLinkNotFoundException e) {
        final String description = "Link not found in tracked for this user";
        final String code = "404";
        return ErrorDto.of(description, code, e);
    }
}
