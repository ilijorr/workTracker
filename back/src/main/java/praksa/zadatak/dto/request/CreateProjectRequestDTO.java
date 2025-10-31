package praksa.zadatak.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateProjectRequestDTO {
    @NotBlank
    @Size(max = 100)
    private String name;

    @Size(max = 500)
    private String description;
}