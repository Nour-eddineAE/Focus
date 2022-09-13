package com.webapps.Focus.filters;

import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

public class JWTAuthorisationFilter extends OncePerRequestFilter {


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if(request.getServletPath().equals(JWTUtil.REFRESH_TOKEN_ENDPOINT)
                || request.getServletPath().equals("/auth/login")
                ||  request.getServletPath().equals("/api/auth/signup")
                || request.getServletPath().equals("/api/test")){
//            if the path equals /api/refreshToken", no need to continue, because the token sent has no "roles" attribute
            filterChain.doFilter(request, response);
        } else {
            String authorizationToken = request.getHeader(JWTUtil.AUTH_HEADER);
//        Bearer should be placed before the token
            if(authorizationToken != null && authorizationToken.startsWith(JWTUtil.HEADER_PREFIX)) {
                try {
                    Algorithm algorithm = Algorithm.HMAC256(JWTUtil.SIGNATURE_SECRET);


                    DecodedJWT decodedJWT = JWTUtil.getDecodedJWT(authorizationToken, algorithm);

                    String username = decodedJWT.getSubject();

                    String[] roles = decodedJWT.getClaim("roles").asArray(String.class);
                    Collection<GrantedAuthority> authorities = new ArrayList<>();
                    for (String role: roles) {
                        authorities.add(new SimpleGrantedAuthority(role));
                    }

//             no need for credentials here since the user is already authenticated
                    UsernamePasswordAuthenticationToken authenticationToken =
                            new UsernamePasswordAuthenticationToken(username, null, authorities);
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);

                    filterChain.doFilter(request, response);

                }catch (Exception e) {
                    response.setHeader("error-message", e.getMessage());
                    response.sendError(HttpServletResponse.SC_FORBIDDEN);
                }
            }
            else {
                filterChain.doFilter(request, response);
            }
        }
    }
}
