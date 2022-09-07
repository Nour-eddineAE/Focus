package com.webapps.Focus.entities;

import com.webapps.Focus.entities.role.Role;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.Collection;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppUser {
    @Id
    private String userId;
    private String firstName;
    private String lastName;
    private String email;
    private String username;
    private String password;
    @ManyToMany
    private Collection<Role> roles;
}
