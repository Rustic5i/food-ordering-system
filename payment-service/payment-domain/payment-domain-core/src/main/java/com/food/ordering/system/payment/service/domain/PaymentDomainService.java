package com.food.ordering.system.payment.service.domain;

import com.food.ordering.system.payment.service.domain.entity.CreditEntry;
import com.food.ordering.system.payment.service.domain.entity.CreditHistory;
import com.food.ordering.system.payment.service.domain.entity.Payment;
import com.food.ordering.system.payment.service.domain.event.PaymentEvent;

import java.util.List;

public interface PaymentDomainService {

    /**
     * Проверка инициирования платежа
     * @param payment платеж
     * @param creditEntry кредит
     * @param creditHistories история кредита
     * @param failureMessages список сообщений об ошибках
     * @return
     */
    PaymentEvent validateAndInitiatePayment(Payment payment,
                                            CreditEntry creditEntry,
                                            List<CreditHistory> creditHistories,
                                            List<String> failureMessages);

    /**
     * Проверка отмены платежа
     * @param payment платеж
     * @param creditEntry кредит
     * @param creditHistories история кредита
     * @param failureMessages список сообщений об ошибках
     * @return
     */
    PaymentEvent validateAndCancelPayment(Payment payment,
                                          CreditEntry creditEntry,
                                          List<CreditHistory> creditHistories,
                                          List<String> failureMessages);
}
