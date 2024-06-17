package ru.seminar.homework.hw5.advice;

import io.reflectoring.model.ApiErrorInfo;
import io.reflectoring.model.ApiValidationError;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ru.seminar.homework.hw5.exception.NotFoundException;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
public class ExceptionHandlerAdvice extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        ApiErrorInfo apiError = new ApiErrorInfo();
        apiError.setStatus(HttpStatus.BAD_REQUEST.value());
        apiError.setMessage("Malformed JSON request");
        apiError.setDebugMessage(ex.getMessage());

        List<ApiValidationError> errors = ex.getBindingResult().getFieldErrors().stream()
                .map(fieldError -> new ApiValidationError()
                        .field(fieldError.getField())
                        .rejectedValue(Objects.requireNonNull(fieldError.getRejectedValue()).toString())
                        .message(fieldError.getDefaultMessage()))
                .collect(Collectors.toList());
        apiError.setSubErrors(errors);
        apiError.setPath(request.getDescription(false));
        return buildResponseEntity(apiError);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        ApiErrorInfo apiError = new ApiErrorInfo();
        apiError.setStatus(HttpStatus.BAD_REQUEST.value());
        apiError.setMessage("Malformed JSON request");
        apiError.setDebugMessage(ex.getMessage());
        apiError.setPath(request.getDescription(false));
        return buildResponseEntity(apiError);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        ApiErrorInfo apiError = new ApiErrorInfo();
        apiError.setStatus(status.value());
        apiError.setMessage("Unexpected error");
        apiError.setDebugMessage(ex.getMessage());
        apiError.setPath(request.getDescription(false));
        return buildResponseEntity(apiError);
    }

    @ExceptionHandler(NotFoundException.class)
    protected ResponseEntity<Object> handleNotFoundException(NotFoundException ex, WebRequest request) {
        ApiErrorInfo apiError = new ApiErrorInfo();
        apiError.setStatus(HttpStatus.NOT_FOUND.value());
        apiError.setMessage("Entity was not found");
        apiError.setDebugMessage(ex.getMessage());
        apiError.setPath(request.getDescription(false));
        return buildResponseEntity(apiError);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    protected ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException ex, WebRequest request) {
        ApiErrorInfo apiError = new ApiErrorInfo();
        apiError.setStatus(HttpStatus.BAD_REQUEST.value());
        apiError.setMessage("Illegal argument");
        apiError.setDebugMessage(ex.getMessage());
        apiError.setPath(request.getDescription(false));
        return buildResponseEntity(apiError);
    }

    @ExceptionHandler(IllegalStateException.class)
    protected ResponseEntity<Object> handleIllegalStateException(IllegalStateException ex, WebRequest request) {
        ApiErrorInfo apiError = new ApiErrorInfo();
        apiError.setStatus(HttpStatus.BAD_REQUEST.value());
        apiError.setMessage("Illegal state");
        apiError.setDebugMessage(ex.getMessage());
        apiError.setPath(request.getDescription(false));
        return buildResponseEntity(apiError);
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<Object> internalServerErrorException(Exception ex, WebRequest request) {
        ApiErrorInfo apiError = new ApiErrorInfo();
        apiError.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        apiError.setMessage("Unexpected error");
        apiError.setDebugMessage(ex.getMessage());
        apiError.setPath(request.getDescription(false));
        return buildResponseEntity(apiError);
    }


    private ResponseEntity<Object> buildResponseEntity(ApiErrorInfo apiError) {
        return new ResponseEntity<>(apiError, HttpStatusCode.valueOf(apiError.getStatus()));
    }
}
