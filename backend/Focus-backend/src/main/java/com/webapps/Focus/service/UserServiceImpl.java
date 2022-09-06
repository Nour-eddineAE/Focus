package com.webapps.Focus.service;

import com.webapps.Focus.dao.UserRepository;
import com.webapps.Focus.dto.role.RoleResponseDTO;
import com.webapps.Focus.dto.user.UserRequestDTO;
import com.webapps.Focus.dto.user.UserResponseDTO;
import com.webapps.Focus.entities.AppUser;
import com.webapps.Focus.entities.Role;
import com.webapps.Focus.mappers.UserMapper;
import org.springframework.stereotype.Service;


import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements IUserService {
    private UserRepository userRepository;
    private UserMapper userMapper;
    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        super();
        this.userRepository = userRepository;
        this.userMapper = userMapper;
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

}
