
package com.example.odeme_sis_lab.order.service;

import com.example.odeme_sis_lab.order.dto.CreateOrderRequest;
import com.example.odeme_sis_lab.order.entity.Order;
import com.example.odeme_sis_lab.order.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Transactional
    public Long createOrder(CreateOrderRequest request) {

        Order order = new Order(
                request.productId(),
                request.quantity(),
                request.amount()
        );

        Order savedOrder = orderRepository.save(order);

        return savedOrder.getId();
    }
}
