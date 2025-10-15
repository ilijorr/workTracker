package praksa.zadatak.exception;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(
            @NotNull Exception exception, HttpServletRequest request
    ) {
        return buildErrorResponse(exception, request);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFound(
            @NotNull ResourceNotFoundException exception, HttpServletRequest request
    ) {
        return buildErrorResponse(exception, request);
    }

    @ExceptionHandler(NotAssignedException.class)
    public ResponseEntity<ErrorResponse> handleNotAssigned(
            @NotNull NotAssignedException exception, HttpServletRequest request
    ) {
        return buildErrorResponse(exception, request);
    }

    @ExceptionHandler(InvalidDateRangeException.class)
    public ResponseEntity<ErrorResponse> handleInvalidDateRange(
            @NotNull InvalidDateRangeException exception, HttpServletRequest request
    ) {
        return buildErrorResponse(exception, request);
    }

    @ExceptionHandler(InvalidRequestException.class)
    public ResponseEntity<ErrorResponse> handleInvalidRequest(
            @NotNull InvalidRequestException exception, HttpServletRequest request
    ) {
        return buildErrorResponse(exception, request);
    }

    @ExceptionHandler(UnsupportedOperationException.class)
    public ResponseEntity<ErrorResponse> handleUnimplementedMethod(
            @NotNull UnsupportedOperationException exception,
            HttpServletRequest request
    ) {
        return buildErrorResponse(exception, request);
    }

    private ResponseEntity<ErrorResponse> buildErrorResponse(Exception exception, HttpServletRequest request) {
        HttpStatus status = extractHttpStatus(exception);
        ErrorResponse errorResponse = new ErrorResponse(
                status.value(), exception.getMessage(), request.getRequestURI()
        );
        return new ResponseEntity<>(errorResponse, status);
    }

    private HttpStatus extractHttpStatus(Exception exception) {
        Class<?> exceptionClass = exception.getClass();
        while (exceptionClass != null) {
            ResponseStatus responseStatus = exceptionClass.getAnnotation(ResponseStatus.class);
            if (responseStatus != null) {
                return responseStatus.value();
            }
            exceptionClass = exceptionClass.getSuperclass();
        }

        return HttpStatus.INTERNAL_SERVER_ERROR;
    }
}
