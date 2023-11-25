package com.food.ordering.system.order.service.domain.outbox.scheduler.payment;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.food.ordering.system.domain.valueobject.OrderStatus;
import com.food.ordering.system.order.service.domain.exception.OrderDomainException;
import com.food.ordering.system.order.service.domain.outbox.model.payment.OrderPaymentEventPayload;
import com.food.ordering.system.order.service.domain.outbox.model.payment.OrderPaymentOutboxMessage;
import com.food.ordering.system.order.service.domain.ports.output.repository.PaymentOutboxRepository;
import com.food.ordering.system.outbox.OutboxStatus;
import com.food.ordering.system.saga.SagaStatus;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.food.ordering.system.saga.order.SagaConstants.ORDER_SAGA_NAME;

@Slf4j
@AllArgsConstructor
@Component
public class PaymentOutboxHelper {

    private final PaymentOutboxRepository paymentOutboxRepository;
    private final ObjectMapper objectMapper;

    @Transactional(readOnly = true)
    public List<OrderPaymentOutboxMessage> getPaymentOutboxMessageByOutboxStatusAndSagaStatus(
            OutboxStatus outboxStatus, SagaStatus... sagaStatuses) {
        return paymentOutboxRepository.findByTypeAndOutboxStatusAndSagaStatus(ORDER_SAGA_NAME,
                outboxStatus, sagaStatuses);
    }

    @Transactional(readOnly = true)
    public Optional<OrderPaymentOutboxMessage> getPaymentOutboxMessageBySagaIdAndSagaStatus(UUID sagaId, SagaStatus... sagaStatus) {
        return paymentOutboxRepository.findByTypeAndSagaIdAndSagaStatus(ORDER_SAGA_NAME, sagaId, sagaStatus);
    }

    @Transactional
    public void save(OrderPaymentOutboxMessage orderPaymentOutboxMessage) {
        OrderPaymentOutboxMessage response = paymentOutboxRepository.save(orderPaymentOutboxMessage);
        if (response == null) {
            String errorMessage = "Could not save OrderPaymentOutboxMessage with outbox id: %s".formatted(orderPaymentOutboxMessage.getId());
            log.error(errorMessage);
            throw new OrderDomainException(errorMessage);
        }
        log.info("OrderPaymentOutboxMessage saved with outbox id: {}", orderPaymentOutboxMessage.getId());
    }

    @Transactional
    public void savePaymentOutboxMessage(OrderPaymentEventPayload paymentEvenPayload,
                                         OrderStatus orderStatus,
                                         SagaStatus sagaStatus,
                                         OutboxStatus outboxStatus,
                                         UUID sagaId) {
        save(OrderPaymentOutboxMessage.builder()
                .id(UUID.randomUUID())
                .sagaId(sagaId)
                .createdAt(paymentEvenPayload.getCreatedAt())
                .type(ORDER_SAGA_NAME)
                .payload(createPayload(paymentEvenPayload))
                .orderStatus(orderStatus)
                .sagaStatus(sagaStatus)
                .outboxStatus(outboxStatus)
                .build()
        );
    }

    private String createPayload(OrderPaymentEventPayload paymentEvenPayload) {
        try {
            return objectMapper.writeValueAsString(paymentEvenPayload);
        } catch (JsonProcessingException e) {
            String errorMessage = "Could not create OrderPaymentEventPayload object for order id: %s"
                    .formatted(paymentEvenPayload.getOrderId());
            log.error(errorMessage, e);
            throw new OrderDomainException(errorMessage, e);
        }
    }

    @Transactional
    public void deletePaymentOutboxMessageByOutboxStatusAndSagaStatus(OutboxStatus outboxStatus,
                                                                      SagaStatus... sagaStatuses) {
        paymentOutboxRepository.deleteByTypeAndOutboxStatusAndSagaStatus(ORDER_SAGA_NAME, outboxStatus, sagaStatuses);
    }
}
