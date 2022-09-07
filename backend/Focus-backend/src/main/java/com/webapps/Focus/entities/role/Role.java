package com.webapps.Focus.entities.role;

import com.webapps.Focus.entities.AppUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Transient;
import java.util.Collection;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Role {
    @Id
    private String role;
    @ManyToMany(mappedBy = "roles")
    @Transient
    private Collection<AppUser> users;

    public Role(String role) {
        this.role = role;
    }
}
