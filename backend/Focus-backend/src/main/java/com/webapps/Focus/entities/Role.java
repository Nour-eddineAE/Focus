package com.webapps.Focus.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.Collection;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Role {
    @Id
    private String role;
    @ManyToMany(mappedBy = "roles")
    private Collection<AppUser> users;

    public Role(String role) {
        this.role = role;
    }
}
