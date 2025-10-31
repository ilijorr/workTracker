package praksa.zadatak.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class UsernameTakenException extends RuntimeException {
    public UsernameTakenException(String username) {
        super(String.format("Username '%s' is taken", username));
    }
}
