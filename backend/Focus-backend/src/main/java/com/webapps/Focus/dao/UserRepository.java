package com.webapps.Focus.dao;

import com.webapps.Focus.entities.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;



@RepositoryRestResource(collectionResourceRel = "users", path = "users")
public interface UserRepository extends JpaRepository<AppUser,String> {
    @RestResource(path = "/searchUsername")
    AppUser findByUsername(@Param("username") String username);
}
