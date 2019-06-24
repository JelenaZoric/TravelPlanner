package com.ftn.uns.travelplanerbackend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ftn.uns.travelplanerbackend.model.User;
import com.ftn.uns.travelplanerbackend.repository.UserRepository;

@Transactional
@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public User findOne(Long id) {
		return userRepository.getOne(id);
	}

	@Override
	public List<User> findAll() {
		return userRepository.findAll();
	}

	@Override
	public User save(User user) {
		return userRepository.save(user);
	}

	@Override
	public User delete(Long id) {
		User user = userRepository.getOne(id);
		if(user == null) {
			throw new IllegalArgumentException("Tried to delete non existing entity");
		}
		userRepository.delete(user);
		return user;
	}

	@Override
	public void delete(List<Long> ids) {
		for(Long id:ids) {
			this.delete(id);
		}
	}

}
