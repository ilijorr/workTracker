package praksa.zadatak.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {
    private Integer status;
    private String message;
    private Long timestamp;
    private String path;

    public ErrorResponse(Integer status, String message, String path) {
        this.status = status;
        this.message = message;
        this.timestamp = System.currentTimeMillis();
        this.path = path;
    }
}
