package praksa.zadatak.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;
import praksa.zadatak.dto.CreateProjectRequestDTO;
import praksa.zadatak.dto.ProjectDTO;
import praksa.zadatak.exception.ResourceNotFoundException;
import praksa.zadatak.mapper.ProjectMapper;
import praksa.zadatak.model.Project;
import praksa.zadatak.repository.ProjectRepository;
import praksa.zadatak.service.ProjectService;

import java.util.List;


@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {
    private final ProjectMapper projectMapper;
    private final ProjectRepository projectRepository;

    public ProjectDTO create(CreateProjectRequestDTO request) {
        Project project = projectMapper.toEntity(request);
        project = projectRepository.save(project);
        return projectMapper.toDTO(project);
    }

    public List<ProjectDTO> get() {
        List<Project> projects = projectRepository.findAll();
        return projectMapper.toDTOs(projects);
    }

    public ProjectDTO get(Long id) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Project", "id", id));
        return projectMapper.toDTO(project);

    }

}
