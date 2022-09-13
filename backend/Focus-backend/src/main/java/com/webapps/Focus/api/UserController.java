package com.webapps.Focus.api;

import com.webapps.Focus.dto.user.UserRequestDTO;
import com.webapps.Focus.dto.user.UserResponseDTO;
import com.webapps.Focus.entities.AppUser;
import com.webapps.Focus.service.IUserService;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.Principal;


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

//    adding a new user
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

//  get profile picture
    @GetMapping(path = "/user/profilePicture/{username}", produces = {MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_JPEG_VALUE } )
    public byte[] getProfilePicture(@PathVariable("username") String username) throws Exception {
        AppUser user = userService.loadUserByUsername(username);
        return Files.readAllBytes(Paths.get(System
                    .getProperty("user.home") +
                    "/my-projects-assets/Focus/users/" +
                "" +
                    user.getPhotoName())
        );
    }
}

