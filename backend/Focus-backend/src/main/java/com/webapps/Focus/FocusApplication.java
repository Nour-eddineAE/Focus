package com.webapps.Focus;

import com.webapps.Focus.dto.user.UserRequestDTO;
import com.webapps.Focus.entities.Role;
import com.webapps.Focus.service.IUserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.ArrayList;
import java.util.Arrays;
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

	@Bean
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
	}

}
