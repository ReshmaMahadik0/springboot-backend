package com.example.springboot_backend.dto;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class OrderDto {

    private Long id;

    @NotBlank(message = "order number is required")
    private String orderNumber;

    @NotNull(message = "order date is required")
    private LocalDate orderDate;

    @NotNull(message = "total amount is required")
    private Double totalAmount;


    private Long userId;

}
