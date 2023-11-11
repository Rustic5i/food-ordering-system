package com.food.ordering.system.payment.service.domain.event;

import com.food.ordering.system.payment.service.domain.entity.Payment;

import java.time.ZonedDateTime;
import java.util.List;

public class PaymentCancelEvent extends PaymentEvent{
    public PaymentCancelEvent(Payment payment, ZonedDateTime createAt, List<String> failureMessage) {
        super(payment, createAt, failureMessage);
    }
}
