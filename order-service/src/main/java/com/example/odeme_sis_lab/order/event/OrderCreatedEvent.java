package com.example.odeme_sis_lab.order.event;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record OrderCreatedEvent(
        UUID eventId,
        Long orderId,
        Long productId,
        Integer quantity,
        BigDecimal amount,
        Instant occurredAt
) {
}