package com.food.ordering.system.domain.valueobject;

/**
 * Статусы заказа
 */
public enum OrderStatus {

    /**
     * В ожидании
     */
    PENDING,

    /**
     * Оплаченный
     */
    PAID,

    /**
     * Одобренный
     */
    APPROVED,

    /**
     * Отмена заказа
     */
    CANCELLING,

    /**
     * Заказ отменен
     */
    CANCELLED;
}
