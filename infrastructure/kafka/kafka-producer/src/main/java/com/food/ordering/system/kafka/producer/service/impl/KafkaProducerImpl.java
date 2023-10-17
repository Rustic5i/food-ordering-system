package com.food.ordering.system.kafka.producer.service.impl;

import com.food.ordering.system.kafka.producer.exception.KafkaProducerException;
import com.food.ordering.system.kafka.producer.service.KafkaProducer;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.apache.avro.specific.SpecificRecordBase;
import org.springframework.kafka.KafkaException;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.io.Serializable;

@Slf4j
@Component
public class KafkaProducerImpl<K extends Serializable, V extends SpecificRecordBase> implements KafkaProducer<K, V> {

    private final KafkaTemplate<K, V> kafkaTemplate;

    public KafkaProducerImpl(KafkaTemplate<K, V> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void send(String topicName, K key, V message, ListenableFutureCallback<SendResult<K, V>> callback) {
        log.info("Отправка сообщения={} в топик={}", message, topicName);
        try {
            ListenableFuture<SendResult<K, V>> kafkaResultFuture = kafkaTemplate.send(topicName, key, message);
            kafkaResultFuture.addCallback(callback);
        } catch (KafkaException e) {
            log.error("Ошибка продюсера Kafka с ключом: {}, сообщением: {} и исключением: {}", key, message,
                    e.getMessage());
            throw new KafkaProducerException("Ошибка продюсера Kafka с ключом: " + key + " и сообщение: " + message);
        }
    }

    @PreDestroy
    public void close() {
        if (kafkaTemplate != null) {
            log.info("Закрытие продюсера Кафки!");
            kafkaTemplate.destroy();
        }
    }
}
