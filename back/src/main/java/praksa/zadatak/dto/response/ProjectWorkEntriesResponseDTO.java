package praksa.zadatak.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;
import praksa.zadatak.dto.ProjectDTO;
import praksa.zadatak.dto.WorkEntryWithoutProject;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProjectWorkEntriesResponseDTO {
    private ProjectDTO project;
    private Page<WorkEntryWithoutProject> workEntries;
}
