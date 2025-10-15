package praksa.zadatak.service;

import praksa.zadatak.dto.CreateProjectRequestDTO;
import praksa.zadatak.dto.ProjectDTO;

import java.util.List;

public interface ProjectService {
    ProjectDTO create(CreateProjectRequestDTO request);

    List<ProjectDTO> get();
    ProjectDTO get(Long id);

    List<ProjectDTO> getEmployeeProjects(Long employeeId);
}
