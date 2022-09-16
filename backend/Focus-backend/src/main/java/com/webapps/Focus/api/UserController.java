package com.webapps.Focus.api;

import com.webapps.Focus.dto.user.UserRequestDTO;
import com.webapps.Focus.dto.user.UserResponseDTO;
import com.webapps.Focus.entities.AppUser;
import com.webapps.Focus.mappers.UserMapper;
import com.webapps.Focus.service.IUserService;

import lombok.AllArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.Principal;


//only the users with USER authority have access to this endpoint
//@PostAuthorize("hasAuthority('USER')")
@RestController
@RequestMapping(path = "/api")
@AllArgsConstructor
public class UserController {

    private IUserService userService;
    private AuthenticationManager authenticationManager;
    private UserMapper userMapper;


//    adding a new user
    @PostMapping(path = "/auth/signup")
    public UserResponseDTO addNewUser(@RequestBody UserRequestDTO user) {
        AppUser appUser = userMapper.UserRequestDTOToAppUser(user);
        return userService.save(appUser);
    }

//    getting a new access token
    @GetMapping(path = "/refreshToken")
    public void refreshAccessToken(HttpServletRequest request, HttpServletResponse response) throws Exception {
        this.userService.refreshToken(request, response);
    }

//  consulting profile (requires authentication)
    @GetMapping(path = "/user/profile")
    public UserResponseDTO profile(Principal principal) {
        return userService.getUserByUsername(principal.getName());
    }

//  getting user avatar
    @GetMapping(path = "/user/profilePicture/{username}", produces = {MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_JPEG_VALUE } )
    public byte[] getProfilePicture(@PathVariable("username") String username) throws Exception {
        AppUser user = userService.loadUserByUsername(username);
        System.err.println("avatar consulted");

        //It is recommended to put all your assets in users directory
        return Files.readAllBytes(Paths.get(System
                    .getProperty("user.home") +
                    "/my-projects-assets/Focus/users/" +
                    user.getPhotoName())
        );
    }

//  Adding a new avatar
    @PostMapping(path = "user/uploadPhoto/{username}")
    public void handleAvatarUpload(@PathVariable("username") String username, MultipartFile file) throws Exception {
        System.err.println("inside upload photo");
        AppUser user = this.userService.loadUserByUsername(username);
        System.err.println(file + "              1");
        user.setPhotoName(username + "_" +file.getOriginalFilename());
        System.err.println(file.toString() + "          2");
        Files.write(Paths.get(System
                .getProperty("user.home") +
                "/my-projects-assets/Focus/users/" +
                        user.getPhotoName()),
                file.getBytes());
        userService.update(user);
        System.err.println("photo uploaded");
    }
}

