package com.food.ordering.system.order.service.domain;

import com.food.ordering.system.domain.valueobject.PaymentResponse;
import com.food.ordering.system.order.service.domain.ports.input.message.listener.payment.PaymentResponseMessageListener;
import com.food.ordering.system.order.service.domain.saga.OrderPaymentSaga;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import static com.food.ordering.system.order.service.domain.entity.Order.FAILURE_MESSAGE_DELIMITER;

@Slf4j
@AllArgsConstructor
@Validated
@Service
public class PaymentResponseMessageListenerImpl implements PaymentResponseMessageListener {

    private final OrderPaymentSaga orderPaymentSaga;

    @Override
    public void paymentCompleted(PaymentResponse paymentResponse) {
        orderPaymentSaga.process(paymentResponse);
        log.info("Order Payment Saga process operation is completed for order id: {}", paymentResponse.getOrderId());
    }

    @Override
    public void paymentCancelled(PaymentResponse paymentResponse) {
        orderPaymentSaga.rollback(paymentResponse);
        log.info("Order is roll backend for id: {} with failure messages: {}",
                paymentResponse.getOrderId(), String.join(FAILURE_MESSAGE_DELIMITER,
                        paymentResponse.getFailureMessage()));
    }
}
