package com.food.ordering.system.payment.service.domain.entity;

import com.food.ordering.system.domain.entity.BaseEntity;
import com.food.ordering.system.domain.valueobject.CustomerId;
import com.food.ordering.system.domain.valueobject.Money;
import com.food.ordering.system.payment.service.domain.valueobject.CreditEntityId;

public class CreditEntity extends BaseEntity<CreditEntityId> {

    private final CustomerId customerId;
    private Money totalCreditAmount;


    public void addCreditAmount(Money amount){
        totalCreditAmount = totalCreditAmount.add(amount);
    }

    public void subtractCreditAmount(Money amount){
        totalCreditAmount = totalCreditAmount.subtract(amount);
    }

    private CreditEntity(Builder builder) {
        setId(builder.creditEntityId);
        customerId = builder.customerId;
        totalCreditAmount = builder.totalCreditAmount;
    }

    public CustomerId getCustomerId() {
        return customerId;
    }

    public Money getTotalCreditAmount() {
        return totalCreditAmount;
    }

    public static final class Builder {
        private CreditEntityId creditEntityId;
        private CustomerId customerId;
        private Money totalCreditAmount;

        private Builder() {
        }

        public static Builder builder() {
            return new Builder();
        }

        public Builder id(CreditEntityId val) {
            creditEntityId = val;
            return this;
        }

        public Builder customerId(CustomerId val) {
            customerId = val;
            return this;
        }

        public Builder totalCreditAmount(Money val) {
            totalCreditAmount = val;
            return this;
        }

        public CreditEntity build() {
            return new CreditEntity(this);
        }
    }
}
