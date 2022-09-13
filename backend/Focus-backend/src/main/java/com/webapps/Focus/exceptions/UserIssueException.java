package com.webapps.Focus.exceptions;

import org.springframework.security.core.AuthenticationException;

public class UserIssueException extends  AuthenticationException{
    public UserIssueException(String msg) {
        super(msg);
    }
}
