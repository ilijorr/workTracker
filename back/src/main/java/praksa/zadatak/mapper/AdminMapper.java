package praksa.zadatak.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import praksa.zadatak.dto.AdminDTO;
import praksa.zadatak.model.Admin;

@Mapper(componentModel = "spring")
public interface AdminMapper {

    @Mapping(target = "password", ignore = true)
    AdminDTO toDTO(Admin admin);

    Admin toEntity(AdminDTO adminDTO);
}