package com.example.springboot_backend.controller;

import com.example.springboot_backend.dto.UserDto;
import com.example.springboot_backend.entities.User;

import com.example.springboot_backend.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

//    @PostMapping(produces = {"application/xml", "application/json"},
//                 consumes = {"application/json", "application/xml"})
    @PostMapping()
    public ResponseEntity<UserDto> addUser(@RequestBody @Valid UserDto userDto) {
        UserDto userDto1 = userService.createUser(userDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(userDto1);
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<UserDto> userDtos = userService.getAllUsers();
        return ResponseEntity.status(HttpStatus.OK).body(userDtos);

    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable("id") Long id) {
         UserDto userDto = userService.getUserById(id);
        return ResponseEntity.ok(userDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable Long id, @RequestBody UserDto userDto) {
        UserDto updatedUser = userService.updateUser(id, userDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(updatedUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
         userService.deleteUser(id);
         return ResponseEntity.ok("User deleted successfully");
    }

    @DeleteMapping("/all")
    public ResponseEntity<?> deleteAllUser(){
        userService.deleteAllUsers();
        return ResponseEntity.ok("all user deleted successfully");
    }
}
