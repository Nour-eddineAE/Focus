package com.webapps.Focus.service;

import com.webapps.Focus.dao.UserRepository;
import com.webapps.Focus.dto.user.UserRequestDTO;
import com.webapps.Focus.dto.user.UserResponseDTO;
import com.webapps.Focus.entities.AppUser;
import com.webapps.Focus.entities.role.Role;
import com.webapps.Focus.exceptions.UserIssueException;
import com.webapps.Focus.mappers.UserMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements IUserService {
    private UserRepository userRepository;
    private UserMapper userMapper;

    private PasswordEncoder passwordEncoder;
    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper, PasswordEncoder passwordEncoder) {
        super();
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public UserResponseDTO getUserByEmail(String email) {
        AppUser user = userRepository.getUserByEmail(email);
      /*  if (user == null){
            throw new RuntimeException("No user with this email");
        }*/
        UserResponseDTO userResponseDTO = userMapper.AppUserToUserResponseDTO(user);
        return userResponseDTO;
    }

    @Override
    public UserResponseDTO getUserByUsername(String username) {
        AppUser user = userRepository.getUserByUsername(username);
       /* if (user == null){
            throw new RuntimeException("No user with this username");
        }*/
        UserResponseDTO userResponseDTO = userMapper.AppUserToUserResponseDTO(user);
        return userResponseDTO;
    }

    @Override
    public AppUser loadUserByUsername(String username) {
        return userRepository.getUserByUsername(username);
    }

    @Override
    public UserResponseDTO getUserById(String userId) {
        AppUser user = userRepository.findById(userId).orElse(null);
      /*  if (user == null){
            throw new RuntimeException("No user with this id");
        }*/
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
    public UserResponseDTO save(UserRequestDTO userRequestDTO) {

        String password = userRequestDTO.getPassword();
        String encodedPassword = this.passwordEncoder.encode(password);
        userRequestDTO.setPassword(encodedPassword);

        AppUser user = userMapper.UserRequestDTOToAppUser(userRequestDTO);
        this.userRepository.save(user);

        UserResponseDTO userResponseDTO = userMapper.AppUserToUserResponseDTO(user);

        return userResponseDTO;
    }

    @Override
    public boolean hasRole(String userId, String serchedRole) {
        AppUser user = userRepository.findById(userId).orElse(null);
        Collection<Role> roles = user.getRoles();
        for(Role role: roles){
            if (role.getRole().equals(serchedRole))
                return true;
        }
        return false;
    }
     @Override
    public void login(String username, String password) {
        AppUser user = userRepository.getUserByUsername(username);
        if (user == null) {
            throw new UserIssueException("Incorrect username or password") {
            };
        }
        if(!passwordEncoder.matches(password,user.getPassword())) {
            throw new UserIssueException("Incorrect username or password") {
            };
        }
    }
}
