package com.webapps.Focus.exceptions;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component("userAuthFailureHandler")
public class UserAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request,
                                        HttpServletResponse response,
                                        AuthenticationException exception)
                                        throws IOException, ServletException {
        try {

            Map<String, String> status = new HashMap<>();
            status.put("status", HttpStatus.UNAUTHORIZED.toString());
            status.put("value", HttpStatus.UNAUTHORIZED.value() + "");
            status.put("reason", HttpStatus.UNAUTHORIZED.getReasonPhrase());
            status.put("error", exception.getMessage());

            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            new ObjectMapper().writeValue(response.getOutputStream(), status);
        }catch (Exception e) {
            throw e;
        }
    }

}
