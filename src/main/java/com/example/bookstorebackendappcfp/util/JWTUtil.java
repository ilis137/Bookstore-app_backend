package com.example.bookstorebackendappcfp.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.auth0.jwt.interfaces.Verification;
import com.example.bookstorebackendappcfp.Model.User;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.security.SignatureException;
import java.util.Date;

@Component
@Slf4j
public class JWTUtil {
    private static final String TOKEN_SECRET = "Oracle";

    public String createToken(Long id, String email) {
        try {
            //to set algorithm
            Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);

            String token = JWT.create()
                    .withClaim("user_id", id)
                    .withClaim("email", email)
                    .withExpiresAt(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
                    .sign(algorithm);
            return token;

        } catch (JWTCreationException exception) {
            exception.printStackTrace();
            //log Token Signing Failed

        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    public Long decodeToken(String token) {
        Long userid;
        //for verification algorithm
        Verification verification = null;
        try {
            verification = JWT.require(Algorithm.HMAC256(TOKEN_SECRET));
        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        try {
            JWTVerifier jwtverifier = verification.build();
            //to decode token
            DecodedJWT decodedjwt = jwtverifier.verify(token);

            Claim claim = decodedjwt.getClaim("user_id");
            userid = claim.asLong();
            return userid;


        } catch (JWTVerificationException exception) {
            exception.printStackTrace();
        }

        return null;
    }

    public String getEmailFromToken(String token) {

        //for verification algorithm
        Verification verification = null;
        try {
            verification = JWT.require(Algorithm.HMAC256(TOKEN_SECRET));
        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        JWTVerifier jwtverifier = verification.build();
        //to decode token
        try {
            DecodedJWT decodedjwt = jwtverifier.verify(token);
            Claim claim = decodedjwt.getClaim("email");
            String email = claim.asString();
            return email;
        } catch (JWTVerificationException e) {
            throw e;
        } catch (IllegalArgumentException e) {
            log.error("JWT claims string is empty: {}", e.getMessage());
        }

        return null;

    }

    public String parseToken(String authHeader) {


        if (StringUtils.hasText(authHeader) && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7, authHeader.length());
        }
        return null;
    }
}
