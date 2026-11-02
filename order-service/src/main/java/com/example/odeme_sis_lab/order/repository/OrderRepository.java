package com.example.odeme_sis_lab.order.repository;

import com.example.odeme_sis_lab.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}