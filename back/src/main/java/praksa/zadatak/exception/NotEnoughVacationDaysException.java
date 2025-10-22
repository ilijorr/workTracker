package praksa.zadatak.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class NotEnoughVacationDaysException extends RuntimeException {
    public NotEnoughVacationDaysException(String message) {
        super(message);
    }

    public NotEnoughVacationDaysException() {
        super("Employee does not have sufficient vacation days");
    }
}
