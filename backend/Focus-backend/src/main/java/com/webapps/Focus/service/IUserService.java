package com.webapps.Focus.service;

import com.webapps.Focus.dto.user.UserRequestDTO;
import com.webapps.Focus.dto.user.UserResponseDTO;
import com.webapps.Focus.entities.AppUser;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
@Service
public interface IUserService {
    UserResponseDTO getUserByUsername(String username);
    AppUser loadUserByUsername(String username);
    UserResponseDTO getUserById(String userId);
    List<UserResponseDTO> getAllUsers();
    UserResponseDTO save(UserRequestDTO userRequestDTO);
    boolean hasRole(String userId, String serchedRole);

    void login(String username, String password);
//    AppUser removeAccount(String userId); // in case we want t remove a user
    void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException;
}
