package ru.edu.homework.springcrud.controller.exception;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.context.request.WebRequest;
import ru.edu.homework.springcrud.dto.error.ApiError;
import ru.edu.homework.springcrud.exception.repository.AlreadyExistsException;
import ru.edu.homework.springcrud.exception.repository.NotFoundException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ExceptionHandlingControllerTest {

    @Mock
    private WebRequest request;

    @InjectMocks
    private ExceptionHandlingController exceptionHandlingController;

    @BeforeEach
    void setUp() {

    }

    @Test
    void handleMethodArgumentNotValid_shouldReturnBadRequestWithApiError() {

//        MethodArgumentNotValidException ex = mock(MethodArgumentNotValidException.class);
//        when(ex.getMessage()).thenReturn("Validation failed");
//        when(ex.getBindingResult().getFieldErrors()).thenReturn();
//
//        HttpHeaders headers = new HttpHeaders();
//
//
//        ResponseEntity<Object> responseEntity = exceptionHandlingController.handleMethodArgumentNotValid(ex, headers, HttpStatus.BAD_REQUEST, request);
//
//        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
//        ApiError apiError = (ApiError) responseEntity.getBody();
//        assertEquals("Malformed JSON request", apiError.getMessage());
//        assertEquals("Validation failed", apiError.getDebugMessage());
//        assertEquals(request.getDescription(false).substring(4), apiError.getPath());
    }

    @Test
    void handleHttpMessageNotReadable_shouldReturnBadRequestWithApiError() {
        // Arrange
        HttpMessageNotReadableException ex = mock(HttpMessageNotReadableException.class);
        when(ex.getMessage()).thenReturn("Malformed JSON");
        when(request.getDescription(false)).thenReturn("uri=/api/v1/users");

        HttpHeaders headers = new HttpHeaders();

        // Act
        ResponseEntity<Object> responseEntity = exceptionHandlingController.handleHttpMessageNotReadable(ex, headers, HttpStatus.BAD_REQUEST, request);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        ApiError apiError = (ApiError) responseEntity.getBody();
        assertEquals("Malformed JSON request", apiError.getMessage());
        assertEquals("Malformed JSON", apiError.getDebugMessage());
        assertEquals(request.getDescription(false).substring(4), apiError.getPath());
        // Add more assertions based on your implementation
    }

    @Test
    void handleEntityAlreadyExists_shouldReturnConflictWithApiError() {
        // Arrange
        AlreadyExistsException ex = new AlreadyExistsException("Entity already exists");
        when(request.getDescription(false)).thenReturn("uri=/api/v1/users");

        // Act
        ResponseEntity<Object> responseEntity = exceptionHandlingController.handleEntityAlreadyExists(ex, request);

        // Assert
        assertEquals(HttpStatus.CONFLICT, responseEntity.getStatusCode());
        ApiError apiError = (ApiError) responseEntity.getBody();
        assertEquals("Entity already exists", apiError.getMessage());
        assertEquals(request.getDescription(false).substring(4), apiError.getPath());
        // Add more assertions based on your implementation
    }

    @Test
    void handleEntityNotFound_shouldReturnNotFoundWithApiError() {
        // Arrange
        when(request.getDescription(false)).thenReturn("uri=/api/v1/users");
        NotFoundException ex = new NotFoundException("Entity not found");

        // Act
        ResponseEntity<Object> responseEntity = exceptionHandlingController.handleEntityNotFound(ex, request);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        ApiError apiError = (ApiError) responseEntity.getBody();
        assertEquals("Entity not found", apiError.getMessage());
        assertEquals(request.getDescription(false).substring(4), apiError.getPath());
        // Add more assertions based on your implementation
    }

    @Test
    void internalServerError_shouldReturnInternalServerErrorWithApiError() {
        // Arrange
        Exception ex = new Exception("Internal server error");
        when(request.getDescription(false)).thenReturn("uri=/api/v1/users");

        // Act
        ResponseEntity<Object> responseEntity = exceptionHandlingController.internalServerError(ex, request);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        ApiError apiError = (ApiError) responseEntity.getBody();
        assertEquals("Internal server error", apiError.getMessage());
        assertEquals(request.getDescription(false).substring(4), apiError.getPath());
        // Add more assertions based on your implementation
    }
}