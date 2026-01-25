package com.example.springboot_backend.services;

import com.example.springboot_backend.dto.OrderDto;
import com.example.springboot_backend.entities.Order;
import com.example.springboot_backend.entities.User;
import com.example.springboot_backend.exception.OrderNotFoundException;
import com.example.springboot_backend.exception.UserNotFoundException;
import com.example.springboot_backend.repository.OrderRepository;
import com.example.springboot_backend.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class OrderServiceImp implements OrderService{

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @CacheEvict(value = "order_by_id", allEntries = true)
    @Override
    public OrderDto createOrder(OrderDto orderDto) {
        log.info("Creating order for userId={}", orderDto.getUserId());

        User user = userRepository.findById(orderDto.getUserId())
                .orElseThrow(() -> {
                    log.error("User not found during creating order, userId={}", orderDto.getUserId());
                     return new UserNotFoundException("User not found with id : " + orderDto.getUserId());
                });

        Order order = modelMapper.map(orderDto, Order.class);
        order.setUser(user);

        Order savedOrder = orderRepository.save(order);
        log.info("Order created successfully : orderId={}", savedOrder.getId());
        return modelMapper.map(savedOrder, OrderDto.class);
    }

    @Override
    @Cacheable(value = "orders_page", key = "#page + '_' + #size")
    public List<OrderDto> getAllOrders(int page, int size) {

        log.info("DB HIT : fetching order");
        log.info("Fetching order by page={} size={}", page ,size);
        System.out.println("DB HIT : fetching orders");
        Pageable pageable = PageRequest.of(page, size);
        Page<Order> orderPage = orderRepository.findAll(pageable);
        return  orderPage.stream()
                .map(order -> modelMapper.map(order, OrderDto.class))
                .collect(Collectors.toList());
    }

    @Cacheable(value = "orders_by_id", key = "#id")
    @Override
    public OrderDto getOrderById(Long id) {
        log.info("DB HIT : fetching order by id={}", id);
        log.info("fetching order by id:{}", id);
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Order not found with id:{}", id);
                   return new OrderNotFoundException(
                            "Order not found with id : " + id);
                });
        return modelMapper.map(order, OrderDto.class);
    }

    @CacheEvict(value = "orders",allEntries = true)
    @Override
    public OrderDto updateOrder(Long id, OrderDto orderDto) {
        log.info("Updating order id={}", id);

        Order existingOrder = orderRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Order not found for update, id:{}" , id);
                    return new OrderNotFoundException(
                            "Order not found with id : " + id);
                });
        existingOrder.setOrderNumber(orderDto.getOrderNumber());
        existingOrder.setOrderDate(orderDto.getOrderDate());
        existingOrder.setTotalAmount(orderDto.getTotalAmount());

        Order updatedOrder = orderRepository.save(existingOrder);
        log.info("Order updated successfully id={}", id);

        return modelMapper.map(updatedOrder, OrderDto.class);
    }

    @CacheEvict(value = "orders", key = "#id")
    @Override
    public void deleteOrder(Long id) {
        log.info("Deleting order id={}", id);

        Order order = orderRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Order not found for deletion, id={}", id);
                    return new OrderNotFoundException(
                            "Order not found with id : " + id);
                });
        orderRepository.delete(order);
        log.info("Order deleted successfully id={}", id);

    }
}
