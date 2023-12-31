package com.food.ordering.system.payment.service.domain.event;

import com.food.ordering.system.payment.service.domain.entity.Payment;

import java.time.ZonedDateTime;
import java.util.Collections;

public class PaymentCancelEvent extends PaymentEvent {
    public PaymentCancelEvent(Payment payment, ZonedDateTime createAt) {
        super(payment, createAt, Collections.emptyList());
    }
}
