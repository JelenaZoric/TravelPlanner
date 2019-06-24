package com.ftn.uns.travelplanerbackend.service;

import java.util.List;

import com.ftn.uns.travelplanerbackend.model.User;

public interface UserService {

	User findOne(Long id);
	List<User> findAll();
	User save(User user);
	User delete(Long id);
	void delete(List<Long> ids);
}
