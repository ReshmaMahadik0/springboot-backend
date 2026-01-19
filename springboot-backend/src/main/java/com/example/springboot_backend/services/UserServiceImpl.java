package com.example.springboot_backend.services;

import com.example.springboot_backend.dto.UserDto;
import com.example.springboot_backend.entities.User;
import com.example.springboot_backend.exception.DuplicateUserException;
import com.example.springboot_backend.exception.UserNotFoundException;
import com.example.springboot_backend.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;


@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    ModelMapper modelMapper;
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Override
    public UserDto createUser(UserDto userDto) {
        User user = modelMapper.map(userDto, User.class);
         userRepository.findByEmail(user.getEmail()).ifPresent( i -> {
            throw new  DuplicateUserException("user already exists with email : " + user.getEmail()
            );
        });
        System.out.println(user);
        User saveUser = userRepository.save(user);
        return modelMapper.map(saveUser, UserDto.class);
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        List<UserDto> userDtos = users.stream().map(user -> modelMapper.map(user, UserDto.class))
                .collect(Collectors.toList());
        return userDtos;
    }

    @Override
    public UserDto getUserById(Long id) {
      User user = userRepository.findById(id).orElseThrow(() ->
              new UserNotFoundException("User not found with id : " +id));
      return modelMapper.map(user, UserDto.class);
    }

    @Override
    public UserDto updateUser(Long id, UserDto updateUserDto) {
        User existingUser = userRepository.findById(id).orElseThrow(()-> new UserNotFoundException("User not found with id : " +id));
        existingUser.setName(updateUserDto.getName());
        existingUser.setEmail(updateUserDto.getEmail());
        existingUser.setPassword(updateUserDto.getPassword());
        existingUser.setCity(updateUserDto.getCity());
       // existingUser.setRole(updatedUser.getRole());
        User updateUser = userRepository.save(existingUser);
        return modelMapper.map(updateUser, UserDto.class);
    }



    @Override
    public void deleteUser(Long id) {
     User user = userRepository.findById(id).orElseThrow(()
             -> new UserNotFoundException("User not found with id : " + id));
      userRepository.delete(user);

    }

    @Override
    public void deleteAllUsers() {
        userRepository.deleteAll();
    }
}
