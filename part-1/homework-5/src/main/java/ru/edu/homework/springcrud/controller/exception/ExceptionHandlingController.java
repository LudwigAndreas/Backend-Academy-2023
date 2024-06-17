package ru.edu.homework.springcrud.controller.exception;

import org.jetbrains.annotations.ApiStatus;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ru.edu.homework.springcrud.dto.error.ApiError;
import ru.edu.homework.springcrud.dto.error.ApiSubError;
import ru.edu.homework.springcrud.dto.error.ApiValidationError;
import ru.edu.homework.springcrud.exception.repository.AlreadyExistsException;
import ru.edu.homework.springcrud.exception.repository.NotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
public class ExceptionHandlingController extends ResponseEntityExceptionHandler {


    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST);
        apiError.setMessage("Malformed JSON request");
        apiError.setDebugMessage(ex.getMessage());

        List<ApiSubError> errors = ex.getBindingResult().getFieldErrors().stream()
                .map(fieldError -> new ApiValidationError(
                        fieldError.getObjectName(),
                        fieldError.getField(),
                        fieldError.getRejectedValue(),
                        fieldError.getDefaultMessage()
                ))
                .collect(Collectors.toList());

        apiError.setSubErrors(errors);
        apiError.setPath(request.getDescription(false).substring(4));
        return buildResponseEntity(apiError);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST);
        apiError.setMessage("Malformed JSON request");
        apiError.setDebugMessage(ex.getMessage());
        apiError.setPath(request.getDescription(false).substring(4));
        return buildResponseEntity(apiError);
    }

    @ExceptionHandler(AlreadyExistsException.class)
    protected ResponseEntity<Object> handleEntityAlreadyExists(AlreadyExistsException ex, WebRequest request) {
        ApiError apiError = new ApiError(HttpStatus.CONFLICT);
        apiError.setMessage("Entity already exists");
        apiError.setDebugMessage(ex.getMessage());
        apiError.setPath(request.getDescription(false).substring(4));
        return buildResponseEntity(apiError);
    }

    @ExceptionHandler(NotFoundException.class)
    protected ResponseEntity<Object> handleEntityNotFound(NotFoundException ex, WebRequest request) {
        ApiError apiError = new ApiError(HttpStatus.NOT_FOUND);
        apiError.setMessage("Entity not found");
        apiError.setDebugMessage(ex.getMessage());
        apiError.setPath(request.getDescription(false).substring(4));
        return buildResponseEntity(apiError);
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<Object> internalServerError(Exception ex, WebRequest request) {
        ApiError apiError = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR);
        apiError.setMessage("Internal server error");
        apiError.setDebugMessage(ex.getMessage());
        apiError.setPath(request.getDescription(false).substring(4));
        return buildResponseEntity(apiError);
    }

    private ResponseEntity<Object> buildResponseEntity(ApiError apiError) {
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }
}
