package com.ftn.uns.travelplanerbackend.service;

import com.ftn.uns.travelplanerbackend.configuration.jwt.JWTokenProvider;
import com.ftn.uns.travelplanerbackend.model.User;
import com.ftn.uns.travelplanerbackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private JWTokenProvider tokenProvider;

    @Autowired
    private UserRepository userRepository;

    @Override
    public User findOne(Long id) {
        return userRepository.findById(id).orElse(null);
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
        if (user == null) {
            throw new IllegalArgumentException("Tried to delete non existing entity");
        }
        userRepository.delete(user);
        return user;
    }

    @Override
    public void delete(List<Long> ids) {
        for (Long id : ids) {
            this.delete(id);
        }
    }

    @Override
    public String login(User user) {
        Optional<User> dbUser = userRepository.findByEmail(user.getEmail());

        if (!dbUser.isPresent()) {
            return null;
        }

        String userID = dbUser.get().getId().toString();
        return credentialsValid(user, dbUser.get()) ? tokenProvider.createToken(userID) : null;
    }

    @Override
    public User register(User user) {
        Optional<User> dbUser = userRepository.findByEmail(user.getEmail());

        if (dbUser.isPresent()) {
            return null;
        }

        return userRepository.save(user);
    }

    private boolean credentialsValid(User user, User dbUser) {
        return user.getEmail().equals(dbUser.getEmail())
                && user.getPassword().equals(dbUser.getPassword());
    }
}
