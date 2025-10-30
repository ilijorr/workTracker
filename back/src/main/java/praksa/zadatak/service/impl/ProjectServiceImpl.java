package praksa.zadatak.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import praksa.zadatak.dto.request.CreateProjectRequestDTO;
import praksa.zadatak.dto.ProjectDTO;
import praksa.zadatak.exception.ResourceNotFoundException;
import praksa.zadatak.mapper.ProjectMapper;
import praksa.zadatak.model.Project;
import praksa.zadatak.repository.AssignmentRepository;
import praksa.zadatak.repository.ProjectRepository;
import praksa.zadatak.service.ProjectService;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {
    private static final Logger log = LogManager.getLogger(ProjectServiceImpl.class);
    private final ProjectMapper projectMapper;
    private final ProjectRepository projectRepository;

    private final AssignmentRepository assignmentRepository;

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

    public ProjectDTO getWithAuthorization(Long id, Long userId, boolean isAdmin) {
        if (!isAdmin && !assignmentRepository.existsByEmployeeIdAndProjectIdAndIsActiveTrue(userId, id)) {
            //TODO: create a repository with shared logic for assignments and projects
            log.warn("user {} tried to access project {}", userId, id);
            throw new ResourceNotFoundException("Project", "id", id);
        }

        return get(id);
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
