package praksa.zadatak.exception;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({
            InvalidDateRangeException.class,
            InvalidRequestException.class,
            ResourceNotFoundException.class,
            UnsupportedOperationException.class,
            UsernameTakenException.class,
            AuthorizationDeniedException.class, // TODO: throws 500 instead of 401/403
            NotVacationAuthorized.class,
            NotEnoughVacationDaysException.class,
            Exception.class,
    })
    public ResponseEntity<ErrorResponse> handleException(
            @NotNull Exception exception, HttpServletRequest request
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
