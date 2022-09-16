package com.webapps.Focus.service;

import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.webapps.Focus.dao.UserRepository;
import com.webapps.Focus.dto.user.UserResponseDTO;
import com.webapps.Focus.entities.AppUser;
import com.webapps.Focus.entities.role.Role;
import com.webapps.Focus.exceptions.UserIssueException;
import com.webapps.Focus.filters.JWTUtil;
import com.webapps.Focus.mappers.UserMapper;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class UserServiceImpl implements IUserService {

    private UserRepository userRepository;
    private UserMapper userMapper;
    private PasswordEncoder passwordEncoder;

    @Override
    public UserResponseDTO getUserByUsername(String username) {

        AppUser user = userRepository.findByUsername(username);

        if (user == null){
            throw new RuntimeException("No user with this username");
        }

        UserResponseDTO userResponseDTO = userMapper.AppUserToUserResponseDTO(user);
        return userResponseDTO;
    }

    @Override
    public AppUser loadUserByUsername(String username) {

        return userRepository.findByUsername(username);
    }

    @Override
    public UserResponseDTO getUserById(String userId) {

        AppUser user = userRepository.findById(userId).orElse(null);

        if (user == null){
            throw new RuntimeException("No user with this id");
        }

        UserResponseDTO userResponseDTO = userMapper.AppUserToUserResponseDTO(user);
        return userResponseDTO;
    }

    @Override
    public List<UserResponseDTO> getAllUsers() {

        List<AppUser> users = userRepository.findAll();

        List<UserResponseDTO> userResponseDTOList = users
                .stream()
                .map(user -> userMapper.AppUserToUserResponseDTO(user))
                .collect(Collectors.toList());
        return userResponseDTOList;
    }

    @Override
    public UserResponseDTO save(AppUser appUser) {

        String password = appUser.getPassword();

        String encodedPassword = this.passwordEncoder.encode(password);
        appUser.setPassword(encodedPassword);

        // Default user avatar
        if (appUser.getPhotoName().equals(null)) {
            appUser.setPhotoName("unknown.png");
        }

        this.userRepository.save(appUser);

        UserResponseDTO userResponseDTO = userMapper.AppUserToUserResponseDTO(appUser);

        return userResponseDTO;
    }
    @Override
    public UserResponseDTO update(AppUser appUser) {

        this.userRepository.save(appUser);

        return userMapper.AppUserToUserResponseDTO(appUser);
    }




    @Override
    public boolean hasRole(String userId, String searchedRole) {

        AppUser user = userRepository.findById(userId).orElse(null);

        Collection<Role> roles = user.getRoles();

        for(Role role: roles){
            if (role.getRole().equals(searchedRole))
                return true;
        }

        return false;
    }
     @Override
    public void login(String username, String password) {
        AppUser user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UserIssueException("Incorrect username or password") {
            };
        }
        if(!passwordEncoder.matches(password,user.getPassword())) {
            throw new UserIssueException("Incorrect username or password") {
            };
        }
    }

    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {

        //Receives the refresh token as a header
        String authorizationToken = request.getHeader(JWTUtil.AUTH_HEADER);
        //Token should start with the word 'Bearer '
        if(authorizationToken != null && authorizationToken.startsWith(JWTUtil.HEADER_PREFIX)) {
            try {
                Algorithm algorithm = Algorithm.HMAC256(JWTUtil.SIGNATURE_SECRET);

                String refreshToken = authorizationToken.substring(JWTUtil.HEADER_PREFIX.length());

                DecodedJWT decodedJWT = JWTUtil.getDecodedJWT(authorizationToken, algorithm);

                String username = decodedJWT.getSubject();

                AppUser appUser = this.loadUserByUsername(username);

                List<String> authorities = appUser.getRoles()
                        .stream()
                        .map(role -> role.getRole())
                        .collect(Collectors.toList());

                String jwtAccessToken = JWTUtil.generateAccessToken(appUser.getUsername(), request, authorities, algorithm);

                Map<String, String> idToken = new HashMap<>();
                idToken.put("accessToken", jwtAccessToken);
                idToken.put("refreshToken", refreshToken);

                response.setContentType("application/json");

                new ObjectMapper().writeValue(response.getOutputStream(), idToken);

            }catch (Exception e) {
                throw  e;
            }
        } else {
            throw new RuntimeException("Required refresh token");
        }
    }
}
