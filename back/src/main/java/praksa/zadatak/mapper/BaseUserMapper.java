package praksa.zadatak.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import praksa.zadatak.dto.BaseUserDTO;
import praksa.zadatak.model.BaseUser;

@Mapper(componentModel = "spring")
public interface BaseUserMapper {

    BaseUserDTO toDTO(BaseUser baseUser);

    BaseUser toEntity(BaseUserDTO baseUserDTO);
}