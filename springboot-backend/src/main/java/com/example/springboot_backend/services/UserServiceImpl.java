package com.example.springboot_backend.services;

import com.example.springboot_backend.entities.User;
import com.example.springboot_backend.exception.DuplicateUserException;
import com.example.springboot_backend.exception.UserNotFoundException;
import com.example.springboot_backend.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    //private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Override
    public User createUser(User user) {
       // logger.info("user is created");
        //System.out.println("user is created");
        //log.info("user is create");
        userRepository.existsByEmail(user.getEmail()).ifPresent( i -> {
            throw new  DuplicateUserException("user already exists with email : " + user.getEmail()
            );
        });
        return  userRepository.save(user);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(int id) {
      User user = userRepository.findById(id).orElseThrow(() ->
              new UserNotFoundException("User not found with id : " +id));
      return user;
    }

    @Override
    public User updateUser(int id, User updatedUser) {
        User existingUser = userRepository.findById(id).orElseThrow(()-> new UserNotFoundException("User not found with id : " +id));
        existingUser.setName(updatedUser.getName());
        existingUser.setEmail(updatedUser.getEmail());
        existingUser.setPassword(updatedUser.getPassword());
        existingUser.setCity(updatedUser.getCity());
        existingUser.setRole(updatedUser.getRole());
        return userRepository.save(existingUser);
    }

    @Override
    public void deleteUser(int id) {
     User user = userRepository.findById(id).orElseThrow(()
             -> new UserNotFoundException("User not found with id : " + id));
      userRepository.delete(user);

    }

    @Override
    public void deleteAllUsers() {
        userRepository.deleteAll();
    }
}
