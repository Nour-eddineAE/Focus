package com.webapps.Focus.api;

import com.webapps.Focus.dto.user.UserResponseDTO;
import com.webapps.Focus.entities.AppUser;
import com.webapps.Focus.service.IUserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/api")
public class UserController {
    private IUserService userService;
    public UserController(IUserService userService){
        super();
        this.userService = userService;
    }

    @GetMapping(path = "/user/hasRole/{userId}/{role}")
    public boolean hasRole(@PathVariable("userId") String userId,@PathVariable("role") String role) {
        return userService.hasRole(userId, role);
    }
    @GetMapping(path = "/user/allUsers")
    public List<UserResponseDTO> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping(path = "/user/findByEmail/{email}")
    public UserResponseDTO getUserByEmail(@PathVariable String email){
        return userService.getUserByEmail(email);
    }

    @GetMapping(path = "/user/findByUsername/{username}")
    public UserResponseDTO getUserByUsername(@PathVariable String username){
        return userService.getUserByUsername(username);
    }

    @GetMapping(path = "/user/findById/{userId}")
    public UserResponseDTO getUserById(@PathVariable String userId){
        return userService.getUserById(userId);
    }
}
