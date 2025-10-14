package praksa.zadatak.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import praksa.zadatak.dto.CreateProjectRequestDTO;
import praksa.zadatak.dto.ProjectDTO;
import praksa.zadatak.service.ProjectService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {
    public ProjectDTO create(CreateProjectRequestDTO request) {
        return null;
    }

    public List<ProjectDTO> get() {
        return null;
    }

    public ProjectDTO get(Long id) {
        return null;
    }
}
