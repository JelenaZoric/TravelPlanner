package com.ftn.uns.travelplanerbackend.configuration.jwt;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;

public class JWToken {

    private UsernamePasswordAuthenticationToken userToken;

    public JWToken(String user){
        this.userToken = new UsernamePasswordAuthenticationToken(user, null, (Collection<? extends GrantedAuthority>) new ArrayList<>());
    }

    public UsernamePasswordAuthenticationToken getUserToken(){
        return userToken;
    }
}
