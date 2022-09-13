package com.webapps.Focus.exceptions;

import org.springframework.web.bind.annotation.ControllerAdvice;



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
