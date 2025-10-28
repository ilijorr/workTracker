package praksa.zadatak.service;

import org.springframework.data.domain.Page;
import praksa.zadatak.dto.request.CreateProjectRequestDTO;
import praksa.zadatak.dto.ProjectDTO;
import praksa.zadatak.model.Project;

import java.util.List;

public interface ProjectService {
    ProjectDTO create(CreateProjectRequestDTO request);

    Page<ProjectDTO> get(Integer page, Integer size);
    ProjectDTO get(Long id);
    Project getReference(Long id);

}
