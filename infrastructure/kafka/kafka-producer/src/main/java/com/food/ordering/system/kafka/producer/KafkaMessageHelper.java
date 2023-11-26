package com.food.ordering.system.kafka.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.food.ordering.system.order.service.domain.exception.OrderDomainException;
import com.food.ordering.system.outbox.OutboxStatus;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import java.util.function.BiConsumer;

@Slf4j
@AllArgsConstructor
@Component
public class KafkaMessageHelper {

    private final ObjectMapper objectMapper;

    public <T> T getOrderEventPayload(String payload, Class<T> outputType) {
        try {
            return objectMapper.readValue(payload, outputType);
        } catch (JsonProcessingException e) {
            log.error("Could not read {} object!", outputType.getName(), e);
            throw new OrderDomainException("Could not read " + outputType.getName() + " object!", e);
        }
    }

    public <V, U> BiConsumer<? super SendResult<String, V>, ? super Throwable> getKafkaCallback(String responseTopicName,
                                                                                                V avroModel, U outboxMessage,
                                                                                                BiConsumer<U, OutboxStatus> outboxCallback,
                                                                                                String orderId, String avroModelName) {
        return (sendResult, exception) -> {
            if (exception != null) {
                log.error("Error while sending {} with message: {} and outbox type: {} to topic {}",
                        avroModelName, avroModel.toString(), outboxMessage.getClass().getName(), responseTopicName, exception);
                outboxCallback.accept(outboxMessage, OutboxStatus.FAILED);
            } else {
                RecordMetadata metadata = sendResult.getRecordMetadata();
                log.info("Received successful response from Kafka for order id: {}" +
                                " Topic: {} Partition: {} Offset: {} Timestamp: {}",
                        orderId,
                        metadata.topic(),
                        metadata.partition(),
                        metadata.offset(),
                        metadata.timestamp());
                outboxCallback.accept(outboxMessage, OutboxStatus.COMPLETED);
            }
        };
    }
}
