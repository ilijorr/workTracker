package praksa.zadatak.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import praksa.zadatak.dto.CreateProjectRequestDTO;
import praksa.zadatak.dto.ProjectDTO;
import praksa.zadatak.model.Project;

@Mapper(componentModel = "spring")
public interface ProjectMapper {

    ProjectDTO toDTO(Project project);

    Project toEntity(ProjectDTO projectDTO);

    @Mapping(target = "id", ignore = true)
    Project toEntity(CreateProjectRequestDTO createProjectRequestDTO);
}