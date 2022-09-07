package com.webapps.Focus.mappers;

import com.webapps.Focus.dto.role.RoleResponseDTO;
import com.webapps.Focus.entities.role.Role;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    RoleResponseDTO RoleToRoleResponseDTO(Role role);
}
