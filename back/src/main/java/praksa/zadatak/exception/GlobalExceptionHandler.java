package praksa.zadatak.exception;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.NotNull;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Map<Class<? extends Exception>, HttpStatus> EXCEPTION_STATUS_MAP = Map.of(
            InvalidDateRangeException.class,      HttpStatus.BAD_REQUEST,
            InvalidRequestException.class,        HttpStatus.BAD_REQUEST,
            UnsupportedOperationException.class,  HttpStatus.BAD_REQUEST,
            NotEnoughVacationDaysException.class, HttpStatus.BAD_REQUEST,
            BadCredentialsException.class,        HttpStatus.UNAUTHORIZED,
            AuthorizationDeniedException.class,   HttpStatus.FORBIDDEN,
            NotVacationAuthorized.class,          HttpStatus.FORBIDDEN,
            ResourceNotFoundException.class,      HttpStatus.NOT_FOUND,
            UsernameTakenException.class,         HttpStatus.CONFLICT
    );
    private static final Logger log = LogManager.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(
            @NotNull Exception exception, HttpServletRequest request
    ) {
        HttpStatus status = EXCEPTION_STATUS_MAP.getOrDefault(
                exception.getClass(),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
        return buildErrorResponse(exception, request, status);
    }

    private ResponseEntity<ErrorResponse> buildErrorResponse(
            Exception exception,
            HttpServletRequest request,
            HttpStatus status
    ) {
        ErrorResponse errorResponse = new ErrorResponse(
                status.value(), exception.getMessage(), request.getRequestURI()
        );
        log.error(errorResponse);
        return new ResponseEntity<>(errorResponse, status);
    }

}
