package com.webapps.Focus.security;

import com.webapps.Focus.filters.JWTAuthenticationFilter;
import com.webapps.Focus.filters.JWTAuthorisationFilter;
import com.webapps.Focus.filters.JWTUtil;
import com.webapps.Focus.service.IUserService;
import com.webapps.Focus.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    private IUserService userService;
    private PasswordEncoder passwordEncoder;
    private UserDetailsService userDetailsService;
    private AuthenticationFailureHandler failureHandler;

    private AuthenticationEntryPoint authEntryPoint;
    public SecurityConfig(IUserService userService, UserDetailsServiceImpl userDetailsService,
                          PasswordEncoder passwordEncoder,
                          @Qualifier("delegatedAuthenticationEntryPoint") AuthenticationEntryPoint authEntryPoint,
                          @Qualifier("userAuthFailureHandler")AuthenticationFailureHandler failureHandler) {
        this.userDetailsService = userDetailsService;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.authEntryPoint = authEntryPoint;
        this.failureHandler = failureHandler;
    }



    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
//      configure the stateless authentication
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

//        fully permitted requests and those who need authentiction
        http.authorizeRequests().antMatchers(JWTUtil.REFRESH_TOKEN_ENDPOINT + "/**", "/auth/**","/api/auth/**", "/api/test/**").permitAll();
        http.authorizeRequests().anyRequest().authenticated();

//      make the authentication pass through a filter
        JWTAuthenticationFilter authenticationFilter = new JWTAuthenticationFilter(authenticationManagerBean(), userService, passwordEncoder);
        authenticationFilter.setFilterProcessesUrl("/auth/login");
        authenticationFilter.setAuthenticationFailureHandler(this.failureHandler);
        http.addFilter(authenticationFilter);
        http.addFilterBefore(new JWTAuthorisationFilter(), UsernamePasswordAuthenticationFilter.class);

        http.cors().configurationSource(request -> {
            var cors = new CorsConfiguration();
            cors.setAllowedOrigins(List.of("http://localhost:4200", "http://127.0.0.1:80"));
            cors.setAllowedMethods(List.of("GET","POST", "PUT", "DELETE", "OPTIONS"));
            cors.setAllowedHeaders(Arrays.asList("Origin", "Access-Control-Allow-Origin",
                    "Content-Type","Accept", "Authorization", "Origin, Accept", "X-Requested-With",
                    "Access-Control-Request-Method", "Access-Control-Request-Headers"));
            cors.setExposedHeaders(Arrays.asList("Origin", "Content-Type", "Accept",
                    "Authorization","Access-Control-Allow-Origin", "Access-Control-Allow-Origin",
                    "Access-Control-Allow-Credentials"));
            UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
            urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", cors);
            return cors;
        });
//        allow security exceptions handling to component with qualifier delegatedAuthenticationEntryPoint
        http.exceptionHandling().authenticationEntryPoint(authEntryPoint);
    /*
        //give some endpoint a specified role & remove permitAll()
        http.authorizeRequests().antMatchers(HttpMethod.POST, "/user/**").hasAnyAuthority("STUDENT");
        //but this mthd is classic, we can use annotations instead(check out the application)
   */

    }

    //we have an object of type AuthenticationManager in the context, we can then inject it wherever we need it
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
