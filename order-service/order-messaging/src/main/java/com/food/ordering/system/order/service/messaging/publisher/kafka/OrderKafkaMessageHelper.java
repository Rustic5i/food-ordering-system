package com.food.ordering.system.order.service.messaging.publisher.kafka;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import java.util.function.BiConsumer;

@Slf4j
@Component
public class OrderKafkaMessageHelper {

    public <V> BiConsumer<? super SendResult<String, V>, ? super Throwable> getKafkaCallback(String responseTopicName,
                                                                                             V requestAvroModel, String orderId,
                                                                                             String requestAvroModelName) {
        return (sendResult, exception) -> {
            if (exception != null) {
                log.error("Error while sending PaymentRequestAvroModel " + requestAvroModelName +
                                "message {} to topic {}",
                        requestAvroModel.toString(), responseTopicName, exception);
            } else {
                RecordMetadata metadata = sendResult.getRecordMetadata();
                log.info("Received successful response from Kafka for order id: {} " +
                                "Topic: {} Partition: {} Offset: {} Timestamp: {}",
                        orderId,
                        metadata.topic(),
                        metadata.partition(),
                        metadata.offset(),
                        metadata.timestamp());
            }
        };
    }
}
