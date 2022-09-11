package com.webapps.Focus;

import com.webapps.Focus.dto.user.UserRequestDTO;
import com.webapps.Focus.entities.role.Role;
import com.webapps.Focus.entities.role.Roles;
import com.webapps.Focus.service.IUserService;
import com.webapps.Focus.service.RoleService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.*;

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
	CommandLineRunner commandLineRunner(IUserService userService, RoleService roleService) {
		return args -> {
			/*//TODO do this when you want to create a fresh db for test concerns, comment it otherwise
			String[] rolesNames = Arrays.stream(Roles.values()).map(Enum::name).toArray(String[]::new );
			for(String role: rolesNames){
				roleService.saveRole(new Role(role));
			}

			Collection<Role> roles = new ArrayList<>();
			roles.add(new Role("STUDENT"));
			String userId = UUID.randomUUID().toString();
			UserRequestDTO user = new UserRequestDTO(userId,"addi","benAddi","email",
					"root","root",roles );
			userService.save(user);*/
		};
	}


	//ignore CORS filters when we send http requests from the frontend
	/*@Bean
	public CorsFilter corsFilter() {
		CorsConfiguration corsConfiguration = new CorsConfiguration();
		corsConfiguration.setAllowCredentials(true);
		corsConfiguration.setAllowedOrigins(Arrays.asList("http://localhost:4200"));
		corsConfiguration.setAllowedHeaders(Arrays.asList("Origin", "Access-Control-Allow-Origin",
				"Content-Type","Accept", "Authorization", "Origin, Accept", "X-Requested-With",
				"Access-Control-Request-Method", "Access-Control-Request-Headers"));
		corsConfiguration.setExposedHeaders(Arrays.asList("Origin", "Content-Type", "Accept",
				"Authorization","Access-Control-Allow-Origin", "Access-Control-Allow-Origin",
				"Access-Control-Allow-Credentials"));
		corsConfiguration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
		UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
		urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);
		return new CorsFilter(urlBasedCorsConfigurationSource);
	}*/

}
