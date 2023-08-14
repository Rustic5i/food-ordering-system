package com.food.ordering.system.domain.valueobject;

/**
 * Статус платежа
 */
public enum PaymentStatus {

    /**
     * Завершенный
     */
    COMPLETED,

    /**
     * Отмененный
     */
    CANCELLED,

    /**
     * Неуспешный
     */
    FAILED
}
