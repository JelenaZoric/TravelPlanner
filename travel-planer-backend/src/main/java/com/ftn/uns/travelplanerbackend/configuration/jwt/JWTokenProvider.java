package com.ftn.uns.travelplanerbackend.configuration.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JWTokenProvider {

    public String createToken( String user){
        return "{ \"token\": \"" + JWT.create()
                .withSubject(user)
                .withExpiresAt(new Date(System.currentTimeMillis() + JWTConstants.EXPIRATION_TIME))
                .sign(Algorithm.HMAC512(JWTConstants.SECRET.getBytes())) + "\"}";
    }

}