package com.webapps.Focus.mappers;

import com.webapps.Focus.dto.user.UserRequestDTO;
import com.webapps.Focus.dto.user.UserResponseDTO;
import com.webapps.Focus.entities.AppUser;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    AppUser UserRequestDTOToAppUser(UserRequestDTO userRquestDTO);
    UserResponseDTO AppUserToUserResponseDTO(AppUser appUser);
}
