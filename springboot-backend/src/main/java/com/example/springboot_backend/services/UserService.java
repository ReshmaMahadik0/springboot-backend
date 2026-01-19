package com.example.springboot_backend.services;

import com.example.springboot_backend.dto.UserDto;
import com.example.springboot_backend.entities.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {

    UserDto createUser(UserDto userDto);

    List<UserDto> getAllUsers();

    UserDto getUserById(Long id);

   UserDto updateUser(Long id, UserDto userDto);

    void deleteUser(Long id);

    void deleteAllUsers();


}
