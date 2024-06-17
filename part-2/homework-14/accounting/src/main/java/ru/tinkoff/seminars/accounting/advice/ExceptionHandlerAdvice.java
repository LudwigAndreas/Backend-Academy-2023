package ru.tinkoff.seminars.accounting.advice;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataIntegrityViolationException;
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
import ru.tinkoff.seminars.accounting.dto.error.ApiErrorInfo;
import ru.tinkoff.seminars.accounting.dto.error.ApiSubErrorInfo;
import ru.tinkoff.seminars.accounting.dto.error.ApiValidationErrorInfo;
import ru.tinkoff.seminars.accounting.exception.AlreadyExistsException;
import ru.tinkoff.seminars.accounting.exception.MalformedJsonException;
import ru.tinkoff.seminars.accounting.exception.NotFoundException;

import java.util.List;
import java.util.stream.Collectors;

//@Order(Ordered.HIGHEST_PRECEDENCE)
//@RestControllerAdvice
public class ExceptionHandlerAdvice extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        ApiErrorInfo apiError = new ApiErrorInfo(HttpStatus.BAD_REQUEST);
        apiError.setMessage("Malformed JSON request");
        apiError.setDebugMessage(ex.getMessage());

        List<ApiSubErrorInfo> errors = ex.getBindingResult().getFieldErrors().stream()
                .map(fieldError -> new ApiValidationErrorInfo(
                        fieldError.getObjectName(),
                        fieldError.getField(),
                        fieldError.getRejectedValue(),
                        fieldError.getDefaultMessage()
                ))
                .collect(Collectors.toList());
        apiError.setSubErrors(errors);
        apiError.setPath(request.getDescription(false));
        return buildResponseEntity(apiError);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        ApiErrorInfo apiError = new ApiErrorInfo(HttpStatus.BAD_REQUEST);
        apiError.setMessage("Malformed JSON request");
        apiError.setDebugMessage(ex.getMessage());
        apiError.setPath(request.getDescription(false));
        return buildResponseEntity(apiError);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        ApiErrorInfo apiError = new ApiErrorInfo(HttpStatus.INTERNAL_SERVER_ERROR);
        apiError.setMessage("Unexpected error");
        apiError.setDebugMessage(ex.getMessage());
        apiError.setPath(request.getDescription(false));
        return buildResponseEntity(apiError);
    }

    @ExceptionHandler(NotFoundException.class)
    protected ResponseEntity<Object> handleNotFoundException(NotFoundException ex, WebRequest request) {
        ApiErrorInfo apiError = new ApiErrorInfo(HttpStatus.NOT_FOUND);
        apiError.setMessage("User was not found");
        apiError.setDebugMessage(ex.getMessage());
        apiError.setPath(request.getDescription(false));
        return buildResponseEntity(apiError);
    }

    @ExceptionHandler(MalformedJsonException.class)
    protected ResponseEntity<Object> handleNotFoundException(MalformedJsonException ex, WebRequest request) {
        ApiErrorInfo apiError = new ApiErrorInfo(HttpStatus.BAD_REQUEST);
        apiError.setMessage("Malformed JSON request");
        apiError.setDebugMessage(ex.getMessage());
        apiError.setPath(request.getDescription(false));
        return buildResponseEntity(apiError);
    }

    @ExceptionHandler(AlreadyExistsException.class)
    protected ResponseEntity<Object> handleAlreadyExistsException(AlreadyExistsException ex, WebRequest request) {
        ApiErrorInfo apiError = new ApiErrorInfo(HttpStatus.CONFLICT);
        apiError.setMessage("User already exists");
        apiError.setDebugMessage(ex.getMessage());
        apiError.setPath(request.getDescription(false));
        return buildResponseEntity(apiError);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    protected ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException ex, WebRequest request) {
        ApiErrorInfo apiError = new ApiErrorInfo(HttpStatus.BAD_REQUEST);
        apiError.setMessage("Illegal argument");
        apiError.setDebugMessage(ex.getMessage());
        apiError.setPath(request.getDescription(false));
        return buildResponseEntity(apiError);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Object> handleConflictException(DataIntegrityViolationException ex, WebRequest req) {
        ApiErrorInfo apiError = new ApiErrorInfo(HttpStatus.CONFLICT);
        apiError.setMessage("Data integrity violation");
        apiError.setDebugMessage(ex.getMessage());
        apiError.setPath(req.getDescription(false));
        List<ApiSubErrorInfo> errors = ex.getMessage().lines()
                .map(message -> new ApiValidationErrorInfo(
                        "Data integrity violation",
                        message
                ))
                .collect(Collectors.toList());
        apiError.setSubErrors(errors);
        return buildResponseEntity(apiError);
    }

    @ExceptionHandler(IllegalStateException.class)
    protected ResponseEntity<Object> handleIllegalStateException(IllegalStateException ex, WebRequest request) {
        ApiErrorInfo apiError = new ApiErrorInfo(HttpStatus.BAD_REQUEST);
        apiError.setMessage("Illegal state");
        apiError.setDebugMessage(ex.getMessage());
        apiError.setPath(request.getDescription(false));
        return buildResponseEntity(apiError);
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<Object> internalServerErrorException(Exception ex, WebRequest request) {
        ApiErrorInfo apiError = new ApiErrorInfo(HttpStatus.INTERNAL_SERVER_ERROR);
        apiError.setMessage("Unexpected error");
        apiError.setDebugMessage(ex.getMessage());
        apiError.setPath(request.getDescription(false));
        return buildResponseEntity(apiError);
    }

    private ResponseEntity<Object> buildResponseEntity(ApiErrorInfo apiError) {
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }
}
