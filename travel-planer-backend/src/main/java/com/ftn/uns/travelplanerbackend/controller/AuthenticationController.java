package com.ftn.uns.travelplanerbackend.controller;

import com.ftn.uns.travelplanerbackend.model.User;
import com.ftn.uns.travelplanerbackend.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AuthenticationController {

    @Autowired
    private UserService userService;

    @PostMapping(value = "login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> login(@RequestBody User user) {

        if (user.getEmail() == null || user.getPassword() == null) {
            return ResponseEntity.unprocessableEntity().build();
        }

        String response = userService.login(user);
        if (response == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        return ResponseEntity.ok(response);
    }

    @PostMapping(value = "register", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> register(@RequestBody User user) {

        if (user.getEmail() == null || user.getPassword() == null) {
            return ResponseEntity.unprocessableEntity().build();
        }

        User response = userService.register(user);
        if (response == null) {
            return ResponseEntity.unprocessableEntity().build();
        }

        return ResponseEntity.ok(response);
    }

    @GetMapping(value = "users/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> getById(@PathVariable("id") Long id) {
        User user = userService.findOne(id);

        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(user);
    }

    @PutMapping(value = "users/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> edit(@PathVariable("id") Long id, @RequestBody User user) {
        User dbUser = userService.findOne(id);

        if(dbUser == null) {
            return ResponseEntity.notFound().build();
        }

        dbUser.setEmail(user.getEmail());
        dbUser.setPassword(user.getPassword());
        dbUser.setFirstName(user.getFirstName());
        dbUser.setLastName(user.getLastName());
        dbUser.setLocation(user.getLocation());

        return ResponseEntity.ok(userService.save(dbUser));
    }

    @GetMapping(value = "users", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<User> getUsers() {
        return userService.findAll();
    }

    @DeleteMapping(value = "users/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> deleteUser(@PathVariable("id") Long id) {
        return ResponseEntity.ok(userService.delete(id));
    }
    
    @PreAuthorize("isAuthenticated()")
    @GetMapping(value = "users/getCurrentUser")
    public ResponseEntity<User> getCurrentUser(Authentication authentication) {
    	User user = userService.findOne(Long.valueOf(authentication.getName()));
    	return new ResponseEntity<User>(user, HttpStatus.OK);
    }
}
