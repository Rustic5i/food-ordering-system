package com.food.ordering.system.order.service.domain.ports.input.message.listener.restaurantapproval;

import com.food.ordering.system.order.service.domain.dto.message.RestaurantApprovalResponse;

/**
 * Служба подтверждения заказа рестораном
 */
public interface RestaurantApprovalResponseMessagesListener {

    void orderApproved(RestaurantApprovalResponse restaurantApprovalResponse);

    void orderRejected(RestaurantApprovalResponse restaurantApprovalResponse);
}
