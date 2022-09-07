package com.webapps.Focus.dao;

import com.webapps.Focus.entities.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository

public interface UserRepository extends JpaRepository<AppUser,String> {
    @Query("select user from AppUser user where user.email like :email")
    AppUser getUserByEmail(@Param("email") String email);
    @Query("select user from AppUser user where user.username like :username")
    AppUser getUserByUsername(@Param("username") String email);
}
