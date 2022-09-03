package com.webapps.Focus.api;

import com.webapps.Focus.dto.user.UserRequestDTO;
import com.webapps.Focus.dto.user.UserResponseDTO;
import com.webapps.Focus.entities.AppUser;
import com.webapps.Focus.service.IUserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
//@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping(path = "/api")
public class UserController {
    private IUserService userService;
    public UserController(IUserService userService){
        super();
        this.userService = userService;
    }

    @GetMapping(path = "/user/hasRole/{userId}/{role}")
    public ResponseEntity<Boolean> hasRole(@PathVariable("userId") String userId, @PathVariable("role") String role) {
        return new ResponseEntity<Boolean>(userService.hasRole(userId, role), HttpStatus.OK);
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

    @PostMapping(path = "/user/addUser/")
    public UserResponseDTO addNewUser(@RequestBody UserRequestDTO user) {
        return userService.save(user);
    }
}
