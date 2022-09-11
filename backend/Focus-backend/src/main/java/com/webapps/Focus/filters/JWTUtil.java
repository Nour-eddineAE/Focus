package com.webapps.Focus.filters;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class JWTUtil {
   public static final String  SIGNATURE_SECRET = "SecretKeyToGenerateSignature254Fzù*4Fze77f5rfz77FEZ@z//:fzer;kvzvr454rfvzv";
    public static final String AUTH_HEADER = "Authorization";
//    Access token expires after 5 minutes
    public static final Date ACCESS_TOKEN_EXPIRES_AT = new Date(System.currentTimeMillis() + 5 * 60 * 1000);
//    Refresh token expires after 30 days
    public static final Date REFRESH_TOKEN_EXPIRES_AT = new Date(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(30));

    public static  final String REFRESH_TOKEN_ENDPOINT = "/api/refreshToken";

    public static final String HEADER_PREFIX = "Bearer ";

    public static String generateAccessToken(String username, HttpServletRequest request, List<String> authorities, Algorithm algorithm) {
        return JWT.create()
                .withSubject(username)
                .withExpiresAt(JWTUtil.ACCESS_TOKEN_EXPIRES_AT)
//                .withIssuer(request.getRequestURL().toString())
                .withClaim("roles",authorities)
                .sign(algorithm);
    }

    public static String generateRefreshToken(String username, HttpServletRequest request, Algorithm algorithm) {
        return JWT.create()
                .withSubject(username)
                .withExpiresAt(JWTUtil.REFRESH_TOKEN_EXPIRES_AT)
                .withIssuer(request.getRequestURL().toString())
                .sign(algorithm);
    }

    public static DecodedJWT getDecodedJWT(String authorizationToken, Algorithm algorithm) {
        String jwt = authorizationToken.substring(JWTUtil.HEADER_PREFIX.length());

        JWTVerifier verifier = JWT.require(algorithm).build();

        DecodedJWT decodedJWT = verifier.verify(jwt);

        return decodedJWT;
    }



}
