package com.example.odeme_sis_lab.order.producer;

import com.example.odeme_sis_lab.order.event.OrderCreatedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

import static com.example.odeme_sis_lab.order.config.KafkaTopicConfig.ORDER_CREATED_TOPIC;

@Component
public class OrderEventProducer {

    private static final Logger log =
            LoggerFactory.getLogger(OrderEventProducer.class);

    private final KafkaTemplate<String, OrderCreatedEvent> kafkaTemplate;

    public OrderEventProducer(
            KafkaTemplate<String, OrderCreatedEvent> kafkaTemplate
    ) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public CompletableFuture<Void> publish(OrderCreatedEvent event) {

        String key = event.orderId().toString();

        return kafkaTemplate.send(
                ORDER_CREATED_TOPIC,
                key,
                event
        ).handle((result, exception) -> {

            if (exception != null) {
                log.error(
                        "Kafka event gönderimi başarısız. eventId={}, orderId={}",
                        event.eventId(),
                        event.orderId(),
                        exception
                );

                throw new java.util.concurrent.CompletionException(exception);
            }

            log.info(
                    "Kafka event gönderildi. eventId={}, orderId={}, partition={}, offset={}",
                    event.eventId(),
                    event.orderId(),
                    result.getRecordMetadata().partition(),
                    result.getRecordMetadata().offset()
            );

            return null;
        });
    }
}