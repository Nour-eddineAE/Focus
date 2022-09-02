package com.webapps.Focus.dto.user;

import com.webapps.Focus.dto.role.RoleResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.ManyToMany;
import java.util.Collection;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDTO {
    private String userId;
    private String firstName;
    private String lastName;
    private String email;
    private String username;
    private String password;
    private Collection<RoleResponseDTO> roles;

}
