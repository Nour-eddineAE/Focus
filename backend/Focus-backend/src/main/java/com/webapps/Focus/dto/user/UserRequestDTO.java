package com.webapps.Focus.dto.user;

import com.webapps.Focus.entities.role.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.Collection;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestDTO {
    private String userId;
    private String firstName;
    private String lastName;
    private String email;
    private String username;
    private String password;
    private String photoName;
    private Collection<Role> roles;
}
