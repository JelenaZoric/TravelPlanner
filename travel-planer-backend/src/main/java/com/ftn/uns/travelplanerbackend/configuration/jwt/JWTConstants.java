package com.ftn.uns.travelplanerbackend.configuration.jwt;

public class JWTConstants {

    public static final String SECRET = "SecretKeyToGenJWTs";
    
    public static final long EXPIRATION_TIME = 20*60*1000;

    public static final String TOKEN_PREFIX = "Bearer ";

    public static final String HEADER_STRING = "Authorization";
}
