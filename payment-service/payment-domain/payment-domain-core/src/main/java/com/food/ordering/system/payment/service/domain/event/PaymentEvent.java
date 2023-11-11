package com.food.ordering.system.payment.service.domain.event;

import com.food.ordering.system.domain.event.DomainEvent;
import com.food.ordering.system.payment.service.domain.entity.Payment;

import java.time.ZonedDateTime;
import java.util.List;

public abstract class PaymentEvent implements DomainEvent<Payment> {

    private final Payment payment;
    private final ZonedDateTime createAt;
    private final List<String> failureMessage;

    public PaymentEvent(Payment payment, ZonedDateTime createAt, List<String> failureMessage) {
        this.payment = payment;
        this.createAt = createAt;
        this.failureMessage = failureMessage;
    }

    public Payment getPayment() {
        return payment;
    }

    public ZonedDateTime getCreateAt() {
        return createAt;
    }

    public List<String> getFailureMessage() {
        return failureMessage;
    }
}
