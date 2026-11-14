package com.example.odeme_sis_lab.order.service;

import com.example.odeme_sis_lab.order.dto.CreateOrderRequest;
import com.example.odeme_sis_lab.order.entity.Order;
import com.example.odeme_sis_lab.order.event.OrderCreatedEvent;
import com.example.odeme_sis_lab.order.producer.OrderEventProducer;
import com.example.odeme_sis_lab.order.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.UUID;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderEventProducer orderEventProducer;

    public OrderService(
            OrderRepository orderRepository,
            OrderEventProducer orderEventProducer
    ) {
        this.orderRepository = orderRepository;
        this.orderEventProducer = orderEventProducer;
    }

    @Transactional
    public Long createOrder(CreateOrderRequest request) {

        Order order = new Order(
                request.productId(),
                request.quantity(),
                request.amount()
        );

        Order savedOrder = orderRepository.save(order);

        OrderCreatedEvent event = new OrderCreatedEvent(
                UUID.randomUUID(),
                savedOrder.getId(),
                request.productId(),
                request.quantity(),
                request.amount(),
                Instant.now()
        );

        orderEventProducer.publish(event);

        return savedOrder.getId();
    }
}