package com.food.ordering.system.restaurant.service.domain;

import com.food.ordering.system.domain.event.publisher.DomainEventPublisher;
import com.food.ordering.system.restaurant.service.domain.dto.RestaurantApprovalRequest;
import com.food.ordering.system.restaurant.service.domain.event.OrderApprovalEvent;
import com.food.ordering.system.restaurant.service.domain.event.OrderApprovedEvent;
import com.food.ordering.system.restaurant.service.domain.event.OrderRejectedEvent;
import com.food.ordering.system.restaurant.service.domain.ports.input.message.listener.RestaurantApprovalRequestMessageListener;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@AllArgsConstructor
@Service
public class RestaurantApprovalRequestMessageListenerImpl implements RestaurantApprovalRequestMessageListener {

    private final RestaurantApprovalRequestHelper restaurantApprovalRequestHelper;
    private final DomainEventPublisher<OrderRejectedEvent> orderRejectedEventDomainEventPublisher;
    private final DomainEventPublisher<OrderApprovedEvent> orderApprovedEventDomainEventPublisher;

    @Override
    public void approveOrder(RestaurantApprovalRequest restaurantApprovalRequest) {
        OrderApprovalEvent orderApprovalEvent =
                restaurantApprovalRequestHelper.persistOrderApproval(restaurantApprovalRequest);
        fireEvent(orderApprovalEvent);
    }

    private void fireEvent(OrderApprovalEvent orderApprovalEvent) {
        log.info("Publishing restaurant event with OrderApproval id: {} and order id: {}",
                orderApprovalEvent.getOrderApproval().getId().getValue(),
                orderApprovalEvent.getOrderApproval().getOrderId().getValue());
        if (orderApprovalEvent instanceof OrderRejectedEvent) { //todo сделать без if
            orderRejectedEventDomainEventPublisher.publish((OrderRejectedEvent) orderApprovalEvent);
        } else if (orderApprovalEvent instanceof OrderApprovedEvent) {
            orderApprovedEventDomainEventPublisher.publish((OrderApprovedEvent) orderApprovalEvent);
        }
    }
}
