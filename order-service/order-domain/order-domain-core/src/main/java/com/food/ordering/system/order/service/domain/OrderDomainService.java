package com.food.ordering.system.order.service.domain;

import com.food.ordering.system.order.service.domain.entity.Order;
import com.food.ordering.system.order.service.domain.entity.Restaurant;
import com.food.ordering.system.order.service.domain.event.OrderCancelledEvent;
import com.food.ordering.system.order.service.domain.event.OrderCreatedEvent;
import com.food.ordering.system.order.service.domain.event.OrderPaidEvent;

import java.util.List;

public interface OrderDomainService {

    /**
     * Валидировать и инициализировать заказ.
     *
     * @param order      заказ.
     * @param restaurant ресторан.
     * @return событие о создания заказа.
     */
    OrderCreatedEvent validateAndInitiateOrder(Order order, Restaurant restaurant);

    /**
     * Оплатить заказ.
     *
     * @param order заказ.
     * @return Событие об оплате заказа.
     */
    OrderPaidEvent payOrder(Order order);

    /**
     * Одобрить заказ
     *
     * @param order заказ
     */
    void approveOrder(Order order);

    /**
     * Отмена оплаты заказа.
     *
     * @param order           заказ
     * @param failureMessages сообщения об ошибках
     * @return событие об отмене оплаты заказа
     */
    OrderCancelledEvent cancelOrderPayment(Order order, List<String> failureMessages);

    /**
     * Отменить заказ
     *
     * @param order           заказ
     * @param failureMessages сообщения об ошибках
     */
    void cancelOrder(Order order, List<String> failureMessages);
}
