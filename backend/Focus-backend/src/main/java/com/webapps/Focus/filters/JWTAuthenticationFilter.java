package com.webapps.Focus.filters;

import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.webapps.Focus.service.IUserService;

import lombok.AllArgsConstructor;
import lombok.Data;

import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@AllArgsConstructor
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authenticationManager;
    private IUserService userService;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws RuntimeException {
//        JSON body authentication
        try {
            LoginBody loginBody = new ObjectMapper().readValue(request.getInputStream(), LoginBody.class);
            //TODO remove
            System.err.println("Attempting authentication with username: [" + loginBody.username +
                    "] and password [" + loginBody.password + "]");

            this.userService.login(loginBody.username, loginBody.password);

            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginBody.getUsername(),loginBody.getPassword()));
        } catch (Exception e) {
            System.err.println(e.getMessage());
            //TODO test this
            response.setHeader("error-message", e.getMessage());
            throw new AuthenticationException(e.getMessage()) {
            } ;
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {

        //TODO remove
        System.err.println("Authentication succeded");
        User user = (User) authResult.getPrincipal();
        List<String> authorities = user.getAuthorities()
                .stream()
                .map(grantedAuthority -> grantedAuthority.getAuthority())
                .collect(Collectors.toList());

//      Encryption algorithm
        Algorithm algorithm = Algorithm.HMAC256(JWTUtil.SIGNATURE_SECRET);

//      Generating the JWT tokens
        String jwtAccessToken = JWTUtil.generateAccessToken(user.getUsername(),request, authorities, algorithm);

//      Generating a new access token every time the old one expires
        String jwtRefreshToken = JWTUtil.generateRefreshToken(user.getUsername(), request, algorithm);

        Map<String, String> idToken = new HashMap<>();
        idToken.put("accessToken", jwtAccessToken);
        idToken.put("refreshToken", jwtRefreshToken);


//      Setting the body content type to JSON
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

//      Adding both tokens to response body
        new ObjectMapper().writeValue(response.getOutputStream(), idToken);

    }
}
@Data
class LoginBody {
    String username;
    String password;
}