package praksa.zadatak.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class NotVacationAuthorized extends RuntimeException {
    public NotVacationAuthorized(String message) {
        super(message);
    }
    public NotVacationAuthorized() {
        super("Vacation not found or the user isn't the requestor.");
    }
}
