
package com.example.odeme_sis_lab.order.controller;

import com.example.odeme_sis_lab.order.dto.CreateOrderRequest;
import com.example.odeme_sis_lab.order.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<Map<String, Long>> createOrder(
            @Valid @RequestBody CreateOrderRequest request) {

        Long orderId = orderService.createOrder(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(Map.of("orderId", orderId));
    }
}
