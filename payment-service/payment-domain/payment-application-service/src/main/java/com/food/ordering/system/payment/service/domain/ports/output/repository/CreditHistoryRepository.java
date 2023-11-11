package com.food.ordering.system.payment.service.domain.ports.output.repository;

import com.food.ordering.system.domain.valueobject.CustomerId;
import com.food.ordering.system.payment.service.domain.entity.CreditHistory;

import java.util.List;

public interface CreditHistoryRepository {

    CreditHistory save(CreditHistory creditHistory);

    List<CreditHistory> findByCustomerId(CustomerId customerId);
}
