package com.webapps.Focus.exceptions;

import org.apache.tomcat.websocket.AuthenticationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


/**
 * @author Nour-eddineAE
 *This class handles controller level exceptions, and not security  exceptions such as AuthenticationException*/
@ControllerAdvice
public class UserExceptionController {

//    @ExceptionHandler(value = AuthenticationException.class)
//    public ResponseEntity<Object> exception(AuthenticationException exception) {
//        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
//    }
}
