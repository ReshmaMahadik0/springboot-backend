package com.example.springboot_backend.services;

import com.example.springboot_backend.dto.OrderDto;
import com.example.springboot_backend.entities.Order;
import com.example.springboot_backend.entities.User;
import com.example.springboot_backend.exception.OrderNotFoundException;
import com.example.springboot_backend.exception.UserNotFoundException;
import com.example.springboot_backend.repository.OrderRepository;
import com.example.springboot_backend.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

public class OrderServiceImp implements OrderService{

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public OrderDto createOrder(OrderDto orderDto) {

        User user = userRepository.findById(orderDto.getUserId())
                .orElseThrow(() -> new UserNotFoundException(
                        "User not found with id : " + orderDto.getUserId()));

        Order order = modelMapper.map(orderDto, Order.class);
        order.setUser(user);

        Order savedOrder = orderRepository.save(order);
        return modelMapper.map(savedOrder, OrderDto.class);
    }

    @Override
    public List<OrderDto> getAllOrders() {
        return orderRepository.findAll()
                .stream()
                .map(order -> modelMapper.map(order, OrderDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public OrderDto getOrderById(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(
                        "Order not found with id : " + id));
        return modelMapper.map(order, OrderDto.class);
    }

    @Override
    public OrderDto updateOrder(Long id, OrderDto orderDto) {

        Order existingOrder = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(
                        "Order not found with id : " + id));
        existingOrder.setOrderNumber(orderDto.getOrderNumber());
        existingOrder.setOrderDate(orderDto.getOrderDate());
        existingOrder.setTotalAmount(orderDto.getTotalAmount());

        Order updatedOrder = orderRepository.save(existingOrder);
        return modelMapper.map(updatedOrder, OrderDto.class);
    }

    @Override
    public void deleteOrder(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(
                        "Order not found with id : " + id));
        orderRepository.delete(order);
    }
}
