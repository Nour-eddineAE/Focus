package com.webapps.Focus.api;

import com.webapps.Focus.dto.user.UserRequestDTO;
import com.webapps.Focus.dto.user.UserResponseDTO;
import com.webapps.Focus.service.IUserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.util.*;

//only the users with USER authority can reach this endpoint
//@PostAuthorize("hasAuthority('USER')")
@RestController
@RequestMapping(path = "/api")
public class UserController {
    private IUserService userService;

    AuthenticationManager authenticationManager;
    public UserController(IUserService userService, AuthenticationManager authenticationManager){
        super();
        this.userService = userService;
        this.authenticationManager = authenticationManager;
    }

    @GetMapping(path = "/user/allOnlineUsers")
    public List<UserResponseDTO> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping(path = "/user/findByEmail/{email}")
    public UserResponseDTO getUserByEmail(@PathVariable String email){
        return userService.getUserByEmail(email);
    }

    @GetMapping(path = "/user/findById/{userId}")
    public UserResponseDTO getUserById(@PathVariable String userId){
        return userService.getUserById(userId);
    }

  /*  @DeleteMapping(path = "/user/{userId}")
    @PostAuthorize("hasAuthority('ADMIN')")
    public void removeUser(@PathVariable String userId) {
//        then remove the user
    }*/

    //adding a new user
    @PostMapping(path = "/auth/signup")
    public UserResponseDTO addNewUser(@RequestBody UserRequestDTO user) {
        return userService.save(user);
    }

//    get a new access token
    @GetMapping(path = "/refreshToken")
    public void refreshAccessToken(HttpServletRequest request, HttpServletResponse response) throws Exception {
        this.userService.refreshToken(request, response);
    }

//    consulting profile (requires authentication)
    @GetMapping(path = "/user/profile")
    public UserResponseDTO profile(Principal principal) {
        return userService.getUserByUsername(principal.getName());
    }
}

