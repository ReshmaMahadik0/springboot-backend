package com.example.springboot_backend.entities; // Java package names should be lowercase

import jakarta.persistence.*;
import lombok.*;

import java.util.List;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User  {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "password",nullable = false)
    private String password;

    @Column(name = "city", nullable = false)
    private String city;

//    @Column(name = "role", nullable = false)
//    private String role;

    @OneToMany(mappedBy = "user")
    private List<Order> orders;

}

