package com.food.ordering.system.domain.valueobject;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Builder
@Getter
@AllArgsConstructor
public class PaymentResponse {

    private String id;

    private String sagaId;

    private String orderId;

    private String paymentId;

    private String customerId;

    private BigDecimal price;

    private Instant createAt;

    private PaymentStatus paymentStatus;

    private List<String> failureMessage;
}
