package com.example.odeme_sis_lab.order.producer;

import com.example.odeme_sis_lab.order.event.OrderCreatedEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import static com.example.odeme_sis_lab.order.config.KafkaTopicConfig.ORDER_CREATED_TOPIC;

@Component
public class OrderEventProducer {

    private final KafkaTemplate<String, OrderCreatedEvent> kafkaTemplate;

    public OrderEventProducer(
            KafkaTemplate<String, OrderCreatedEvent> kafkaTemplate
    ) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publish(OrderCreatedEvent event) {

        kafkaTemplate.send(
                ORDER_CREATED_TOPIC,
                event.orderId().toString(),
                event
        ).whenComplete((result, exception) -> {

            if (exception != null) {
                System.err.println(
                        "Kafka event gönderimi başarısız: "
                                + exception.getMessage()
                );
                return;
            }

            System.out.println(
                    "Event Kafka'ya gönderildi. Partition: "
                            + result.getRecordMetadata().partition()
                            + ", Offset: "
                            + result.getRecordMetadata().offset()
            );
        });
    }
}