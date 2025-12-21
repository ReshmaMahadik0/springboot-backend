package com.example.springboot_backend.controller;

import com.example.springboot_backend.entities.User;

import com.example.springboot_backend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;
//    @Autowired
//    private FirstProgrammaticApproach firstProgrammaticApproach;
//    @Autowired
//    private SecondProgrammaticApproach secondProgrammaticApproach;

//    @PostMapping("/show")
//    public void show(){
//         System.out.println("Hello world");
//    }

    @PostMapping
    public ResponseEntity<User> addUser(@RequestBody User user) {
        User user1 = userService.createUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(user1);
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.status(HttpStatus.OK).body(users);

    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") int id) {
         User user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable int id, @RequestBody User user) {
        User updatedUser = userService.updateUser(id, user);
        return ResponseEntity.status(HttpStatus.CREATED).body(updatedUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable int id) {
         userService.deleteUser(id);
         return ResponseEntity.ok("User deleted successfully");
    }

    @DeleteMapping("/all")
    public ResponseEntity<?> deleteAllUser(){
        userService.deleteAllUsers();
        return ResponseEntity.ok("all user deleted successfully");
    }
}
