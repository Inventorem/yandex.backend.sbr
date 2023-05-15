package ru.yandex.yandexlavka.exception;

import io.github.resilience4j.ratelimiter.RequestNotPermitted;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionController {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(EntityNotFoundException.class)
    private ExceptionResponse notFound(EntityNotFoundException ex) {
        return new ExceptionResponse(ex.getMessage());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(RuntimeException.class)
    private ExceptionResponse error(RuntimeException ex) {
        return new ExceptionResponse(ex.getMessage());
    }

    @ExceptionHandler(RequestNotPermitted.class)
    @ResponseStatus(HttpStatus.TOO_MANY_REQUESTS)
    public ExceptionResponse handleRequestNotPermitted() {
        System.out.println("Niggers");
        return new ExceptionResponse("Too many requests");
    }
}