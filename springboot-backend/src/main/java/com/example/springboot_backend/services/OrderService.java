package com.example.springboot_backend.services;

import com.example.springboot_backend.dto.OrderDto;

import java.util.List;

public interface OrderService {

    OrderDto createOrder(OrderDto orderDto);

    List<OrderDto> getAllOrders(int page, int size);

    OrderDto getOrderById(Long id);

    OrderDto updateOrder(Long id, OrderDto orderDto);

    void deleteOrder(Long id);
}
