package com.food.ordering.system.order.service.domain.saga;

import com.food.ordering.system.domain.valueobject.OrderId;
import com.food.ordering.system.order.service.domain.entity.Order;
import com.food.ordering.system.order.service.domain.exception.OrderNotFoundException;
import com.food.ordering.system.order.service.domain.ports.output.repository.OrderRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@AllArgsConstructor
@Component
public class OrderSagaHelper {

    private final OrderRepository orderRepository;

    public Order findOrder(String orderId) {
        Optional<Order> order = orderRepository.findById(new OrderId(UUID.fromString(orderId)));
        if (order.isEmpty()) {
            String errorMessage = "Order with id: %s could not be found!".formatted(orderId);
            log.error(errorMessage);
            throw new OrderNotFoundException(errorMessage);
        }
        return order.get();
    }

    public void saveOrder(Order order){
        orderRepository.save(order);
    }
}
