package com.webapps.Focus;

import com.webapps.Focus.dto.user.UserRequestDTO;
import com.webapps.Focus.entities.Role;
import com.webapps.Focus.service.IUserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

@SpringBootApplication
public class FocusApplication {

	public static void main(String[] args) {
		SpringApplication.run(FocusApplication.class, args);
	}


	@Bean
	CommandLineRunner commandLineRunner(IUserService userService) {
		return args -> {
			/*Collection<Role> roles = new ArrayList<>();
			roles.add(new Role("ADMIN"));
			roles.add(new Role("USER"));
			String userId = UUID.randomUUID().toString();
			UserRequestDTO user = new UserRequestDTO(userId,"addi","benAddi","email",
					"masha masha","password", roles);
			userService.save(user);*/
		};
	}


}
