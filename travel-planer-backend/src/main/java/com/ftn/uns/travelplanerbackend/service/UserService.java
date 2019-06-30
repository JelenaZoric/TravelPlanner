package com.ftn.uns.travelplanerbackend.service;

import com.ftn.uns.travelplanerbackend.model.User;

import java.util.List;

public interface UserService {

    User findOne(Long id);

    List<User> findAll();

    User save(User user);

    User delete(Long id);

    void delete(List<Long> ids);

    String login(User user);

    User register(User user);
}
