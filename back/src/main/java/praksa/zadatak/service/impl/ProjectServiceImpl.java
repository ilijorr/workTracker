package praksa.zadatak.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import praksa.zadatak.dto.request.CreateProjectRequestDTO;
import praksa.zadatak.dto.ProjectDTO;
import praksa.zadatak.exception.ResourceNotFoundException;
import praksa.zadatak.mapper.ProjectMapper;
import praksa.zadatak.model.Project;
import praksa.zadatak.repository.ProjectRepository;
import praksa.zadatak.service.ProjectService;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {
    private final ProjectMapper projectMapper;
    private final ProjectRepository projectRepository;

    @Transactional
    public ProjectDTO create(CreateProjectRequestDTO request) {
        Project project = projectMapper.toEntity(request);
        project = projectRepository.save(project);
        return projectMapper.toDTO(project);
    }

    public Page<ProjectDTO> get(Integer page, Integer size) {
        Page<Project> projects = projectRepository.findAll(
                PageRequest.of(page, size)
        );
        return projects.map(projectMapper::toDTO);
    }

    public ProjectDTO get(Long id) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Project", "id", id));
        return projectMapper.toDTO(project);
    }

    public Project getReference(Long id) {
        return projectRepository.getReferenceById(id);
    }

}
