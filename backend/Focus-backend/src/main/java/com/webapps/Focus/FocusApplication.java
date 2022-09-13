package com.webapps.Focus;

import com.webapps.Focus.entities.AppUser;
import com.webapps.Focus.service.IUserService;
import com.webapps.Focus.service.RoleService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


//@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
@SpringBootApplication
public class FocusApplication {

	public static void main(String[] args) {
		SpringApplication.run(FocusApplication.class, args);
	}


	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	@Bean
	CommandLineRunner commandLineRunner(IUserService userService, RoleService roleService, RepositoryRestConfiguration restConfiguration) {
		return args -> {
			/*//TODO do this when you want to create a fresh db for test concerns, comment it otherwise
			String[] rolesNames = Arrays.stream(Roles.values()).map(Enum::name).toArray(String[]::new );
			for(String role: rolesNames){
				roleService.saveRole(new Role(role));
			}
			for(String roleName: rolesNames) {
				Collection<Role> roles = new ArrayList<>();
				roles.add(new Role(roleName));
				String userId = UUID.randomUUID().toString();
				UserRequestDTO user = new UserRequestDTO(userId, RandomString.make(10),RandomString.make(10),
						RandomString.make(10) + "@gmail.com",
						roleName.toLowerCase(),roleName.toLowerCase(),"unknown.png",roles );
				userService.save(user);
			}*/
			//expose ids when sending a response of JSON format
			restConfiguration.exposeIdsFor(AppUser.class);
		};
	}
}
