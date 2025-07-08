package ru.yandex.practicum.catsgram.errorHandler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.yandex.practicum.catsgram.exception.DuplicatedDataException;
import ru.yandex.practicum.catsgram.exception.NotFoundException;
import ru.yandex.practicum.catsgram.exception.ParameterNotValidException;

@Slf4j
@RestControllerAdvice
public class ErrorHandler {
    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleIllegalParameterException(final ParameterNotValidException ex) {
        log.warn("Параметр sort указан неверно: {}", ex.getParameter());
        return new ErrorResponse("Некорректное значение параметра " + ex.getParameter() + ": " + ex.getReason());
    }

    @ExceptionHandler
    public ErrorResponse handleNotFoundException(final NotFoundException ex) {
        log.warn("Данные не найдены. Сообщение: {}", ex.getMessage());
        return new ErrorResponse(ex.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleDuplicateException(final DuplicatedDataException ex) {
        log.warn("Данные дублируются. Сообщение: {}", ex.getMessage());
        return new ErrorResponse(ex.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleOtherException(final Throwable ex) {
        log.warn("Непредвиденная ошибка. Сообщение: {}", ex.getMessage());

        return new ErrorResponse("Произошла непредвиденная ошибка.");
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleValidException(final MethodArgumentNotValidException ex) {
        log.warn("Ошибка валидации данных. Сообщение: {}", ex.getMessage());

        return new ErrorResponse("Ошибка валидации данных. Убедитесь, что данные введены корректно. " +
                "\nСообщение ошибки: " + ex.getMessage());
    }
}
