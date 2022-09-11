package com.webapps.Focus.api;

import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.webapps.Focus.dto.user.UserRequestDTO;
import com.webapps.Focus.dto.user.UserResponseDTO;
import com.webapps.Focus.entities.AppUser;
import com.webapps.Focus.exceptions.UserIssueException;
import com.webapps.Focus.filters.JWTUtil;
import com.webapps.Focus.service.IUserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.util.*;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:8081")
//only the users with USER authority can use mthds under this endpoint
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

    @GetMapping(path = "/user/findById/{userId}")
    public UserResponseDTO getUserById(@PathVariable String userId){
        return userService.getUserById(userId);
    }

    @DeleteMapping(path = "/user/{userId}")
    @PostAuthorize("hasAuthority('ADMIN')")
    public void removeUser(@PathVariable String userId) {
//        then remove the user
    }

    //adding a new user
    @PostMapping(path = "/auth/signup")
    public UserResponseDTO addNewUser(@RequestBody UserRequestDTO user) {
        return userService.save(user);
    }

    @GetMapping(path = "test")
    public AppUser getUser(@RequestBody String username) throws UserIssueException {
         AppUser user = userService.loadUserByUsername(username);
         if (user == null) {
             throw new UserIssueException("user not found");
         }
        return user;
    }

    //get a new access token
    @GetMapping(path = "/refreshToken")
    public void refreshAccessToken(HttpServletRequest request, HttpServletResponse response) throws Exception {
//        receives the refresh token in the header
        String authorizationToken = request.getHeader(JWTUtil.AUTH_HEADER);
//        Bearer should be placed before the token
        if(authorizationToken != null && authorizationToken.startsWith(JWTUtil.HEADER_PREFIX)) {
            try {
                Algorithm algorithm = Algorithm.HMAC256(JWTUtil.SIGNATURE_SECRET);

                String refreshToken = authorizationToken.substring(JWTUtil.HEADER_PREFIX.length());

                DecodedJWT decodedJWT = JWTUtil.getDecodedJWT(authorizationToken, algorithm);

                String username = decodedJWT.getSubject();

                AppUser appUser = this.userService.loadUserByUsername(username);

                List<String> authorities = appUser.getRoles()
                        .stream()
                        .map(role -> role.getRole())
                        .collect(Collectors.toList());

                String jwtAccessToken = JWTUtil.generateAccessToken(appUser.getUsername(), request, authorities, algorithm);

                Map<String, String> idToken = new HashMap<>();
                idToken.put("access-token", jwtAccessToken);
                idToken.put("refresh-token", refreshToken);

                response.setContentType("application/json");

                new ObjectMapper().writeValue(response.getOutputStream(), idToken);

            }catch (Exception e) {
                throw  e;
            }
        } else {
          throw new RuntimeException("Required refresh token");
        }
    }

//    consult my own  profile (requires authentication)
    @GetMapping(path = "/user/profile")
    public UserResponseDTO profile(Principal principal) {
        return userService.getUserByUsername(principal.getName());
    }
}

