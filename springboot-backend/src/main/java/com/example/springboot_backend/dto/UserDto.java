package com.example.springboot_backend.dto;

import com.example.springboot_backend.entities.Order;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
//package com.example.springboot_backend.dto;


@Getter
@Setter
//@JacksonXmlRootElement(localName = "user")
public class UserDto {

    private Long id;

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @Size(min = 3, message = "password must be at least 3 characters")
    private String password;

    private String city;

    @NotBlank(message = "Role is required")
    private String role;

    private List<OrderDto> orderDto;
}
