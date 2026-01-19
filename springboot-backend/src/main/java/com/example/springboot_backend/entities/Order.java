package com.example.springboot_backend.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "orderNumber", nullable = false, unique = true)
    private String orderNumber;

    @Column(name = "orderDate", nullable = false)
    private LocalDate orderDate;

    @Column(name = "totalAmount", nullable = false)
    private Double totalAmount;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;


}
