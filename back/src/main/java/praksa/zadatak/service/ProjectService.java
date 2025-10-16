package praksa.zadatak.service;

import praksa.zadatak.dto.CreateProjectRequestDTO;
import praksa.zadatak.dto.ProjectDTO;
import praksa.zadatak.model.Project;

import java.util.List;

public interface ProjectService {
    ProjectDTO create(CreateProjectRequestDTO request);

    List<ProjectDTO> get();
    ProjectDTO get(Long id);
    Project getReference(Long id);

    List<ProjectDTO> getEmployeeProjects(Long employeeId);
}
