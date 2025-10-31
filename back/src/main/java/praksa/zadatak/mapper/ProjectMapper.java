package praksa.zadatak.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import praksa.zadatak.dto.request.CreateProjectRequestDTO;
import praksa.zadatak.dto.ProjectDTO;
import praksa.zadatak.model.Project;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProjectMapper {

    ProjectDTO toDTO(Project project);
    List<ProjectDTO> toDTOs(List<Project> projects);

    Project toEntity(ProjectDTO projectDTO);

    @Mapping(target = "id", ignore = true)
    Project toEntity(CreateProjectRequestDTO createProjectRequestDTO);
}