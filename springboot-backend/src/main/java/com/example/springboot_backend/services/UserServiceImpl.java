package com.example.springboot_backend.services;

import com.example.springboot_backend.dto.OrderDto;
import com.example.springboot_backend.dto.UserDto;
import com.example.springboot_backend.entities.User;
import com.example.springboot_backend.exception.DuplicateUserException;
import com.example.springboot_backend.exception.UserNotFoundException;
import com.example.springboot_backend.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    @CacheEvict(value = "users", allEntries = true)
    @Override
    public UserDto createUser(UserDto userDto) {
        log.info("Creating user with email: {}", userDto.getEmail());
        User user = modelMapper.map(userDto, User.class);
         userRepository.findByEmail(user.getEmail()).ifPresent( i -> {
             log.warn("Duplicate user attempt with email: {}" , user.getEmail());
            throw new  DuplicateUserException("user already exists with email : " + user.getEmail()
            );
        });
        User saveUser = userRepository.save(user);
        log.info("User created successfully with id : {}" ,saveUser.getId());
        return modelMapper.map(saveUser, UserDto.class);
    }

    @Cacheable(value = "users", key = "#page + '_' + #size")
    @Override
    public List<UserDto> getAllUsers(int page, int size) {
       // List<User> users = userRepository.findAll();
//        List<UserDto> userDtos = users.stream().map(user -> modelMapper.map(user, UserDto.class))
//                .collect(Collectors.toList());

        log.info("DB HIT : fetching user");
        log.info("fetching users page={} size={}", page, size);
        Pageable pageable = PageRequest.of(page, size);
        Page<User> pageUser = userRepository.findAll(pageable);
        return pageUser.stream().map(user -> {

            UserDto userDto = modelMapper.map(user, UserDto.class);

            if (user.getOrders() != null) {
                List<OrderDto> orderDtos = user.getOrders().stream()
                        .map(order -> modelMapper.map(order, OrderDto.class))
                        .collect(Collectors.toList());
                userDto.setOrderDto(orderDtos);
            }
            return userDto;

        }).collect(Collectors.toList());
    }
    @Cacheable(value = "users",key = "#id")
    @Override
    public UserDto getUserById(Long id) {
        log.info("DB HIT : fetching user by Id={}" , id);
        log.info("fetching user by id={}", id);
      User user = userRepository.findById(id).orElseThrow(() ->{
          log.error("User not found with id={}", id);
             return new UserNotFoundException("User not found with id : " +id);
      });

//      return modelMapper.map(user, UserDto.class);
        UserDto userDto = modelMapper.map(user, UserDto.class);

        if (user.getOrders() != null) {
            List<OrderDto> orderDtos = user.getOrders().stream()
                    .map(order -> modelMapper.map(order, OrderDto.class))
                    .collect(Collectors.toList());

            userDto.setOrderDto(orderDtos);
        }
        return userDto;
    }

    @CacheEvict(value = "users", allEntries = true)
    @Override
    public UserDto updateUser(Long id, UserDto updateUserDto) {
        log.info("Update the user with id={}", id);
        User existingUser = userRepository.findById(id)
                .orElseThrow(()-> new UserNotFoundException("User not found with id : " +id));
        existingUser.setName(updateUserDto.getName());
        existingUser.setEmail(updateUserDto.getEmail());
        existingUser.setPassword(updateUserDto.getPassword());
        existingUser.setCity(updateUserDto.getCity());
       // existingUser.setRole(updated
        // User.getRole());
        User updateUser = userRepository.save(existingUser);
        log.info("User update successfully with id: {}", id);
        return modelMapper.map(updateUser, UserDto.class);
    }



    @CacheEvict(value = "users", allEntries = true)
    @Override
    public void deleteUser(Long id) {
        log.info("deleting user id:{}", id);
     User user = userRepository.findById(id).orElseThrow(()
             -> new UserNotFoundException("User not found with id : " + id));
      userRepository.delete(user);
        log.info("User deleted successfully with id:{}", id);


    }

    @CacheEvict(value = "users", allEntries = true)
    @Override
    public void deleteAllUsers() {
        userRepository.deleteAll();
        log.info("All users deleted successfully");
    }
}
