package com.example.springboot_backend.services;

import com.example.springboot_backend.entities.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {

    User createUser(User user);

    List<User> getAllUsers();

    User getUserById(int id);

   User updateUser(int id, User user);

    void deleteUser(int id);

    void deleteAllUsers();


}
