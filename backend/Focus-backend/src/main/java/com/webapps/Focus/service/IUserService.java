package com.webapps.Focus.service;

import com.webapps.Focus.dto.user.UserRequestDTO;
import com.webapps.Focus.dto.user.UserResponseDTO;

import java.util.List;

public interface IUserService {
    UserResponseDTO getUserByEmail(String email);
    UserResponseDTO getUserByUsername(String username);
    UserResponseDTO getUserById(String userId);
    List<UserResponseDTO> getAllUsers();
    UserResponseDTO save(UserRequestDTO userRequestDTO);
    boolean hasRole(String userId, String serchedRole);
//    AppUser removeAccount(String userId); // in case we want t remove a user
}
