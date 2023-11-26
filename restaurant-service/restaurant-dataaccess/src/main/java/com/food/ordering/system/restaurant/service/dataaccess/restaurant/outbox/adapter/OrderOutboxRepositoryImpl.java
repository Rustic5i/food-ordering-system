package com.food.ordering.system.restaurant.service.dataaccess.restaurant.outbox.adapter;

import com.food.ordering.system.outbox.OutboxStatus;
import com.food.ordering.system.restaurant.service.dataaccess.restaurant.outbox.entity.OrderOutboxEntity;
import com.food.ordering.system.restaurant.service.dataaccess.restaurant.outbox.exception.OrderOutboxNotFoundException;
import com.food.ordering.system.restaurant.service.dataaccess.restaurant.outbox.mapper.OrderOutboxDataAccessMapper;
import com.food.ordering.system.restaurant.service.dataaccess.restaurant.outbox.repository.OrderOutboxJpaRepository;
import com.food.ordering.system.restaurant.service.domain.outbox.model.OrderOutboxMessage;
import com.food.ordering.system.restaurant.service.domain.ports.output.repository.OrderOutboxRepository;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class OrderOutboxRepositoryImpl implements OrderOutboxRepository {

    private final OrderOutboxJpaRepository orderOutboxJpaRepository;
    private final OrderOutboxDataAccessMapper orderOutboxDataAccessMapper;

    public OrderOutboxRepositoryImpl(OrderOutboxJpaRepository orderOutboxJpaRepository,
                                     OrderOutboxDataAccessMapper orderOutboxDataAccessMapper) {
        this.orderOutboxJpaRepository = orderOutboxJpaRepository;
        this.orderOutboxDataAccessMapper = orderOutboxDataAccessMapper;
    }

    @Override
    public OrderOutboxMessage save(OrderOutboxMessage orderPaymentOutboxMessage) {
        return orderOutboxDataAccessMapper
                .orderOutboxEntityToOrderOutboxMessage(orderOutboxJpaRepository
                        .save(orderOutboxDataAccessMapper
                                .orderOutboxMessageToOutboxEntity(orderPaymentOutboxMessage)));
    }

    @Override
    public List<OrderOutboxMessage> findByTypeAndOutboxStatus(String sagaType, OutboxStatus outboxStatus) {
        List<OrderOutboxEntity> orderOutboxEntities = orderOutboxJpaRepository.findByTypeAndOutboxStatus(sagaType, outboxStatus);
        if (CollectionUtils.isEmpty(orderOutboxEntities)) {
            String errorMessage = "Approval outbox object cannot be found for saga type {}."
                    .formatted(sagaType);
            throw new OrderOutboxNotFoundException(errorMessage);
        }
        return orderOutboxEntities
                .stream()
                .map(orderOutboxDataAccessMapper::orderOutboxEntityToOrderOutboxMessage)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<OrderOutboxMessage> findByTypeAndSagaIdAndOutboxStatus(String type, UUID sagaId,
                                                                           OutboxStatus outboxStatus) {
        return orderOutboxJpaRepository.findByTypeAndSagaIdAndOutboxStatus(type, sagaId, outboxStatus)
                .map(orderOutboxDataAccessMapper::orderOutboxEntityToOrderOutboxMessage);
    }

    @Override
    public void deleteByTypeAndOutboxStatus(String type, OutboxStatus outboxStatus) {
        orderOutboxJpaRepository.deleteByTypeAndOutboxStatus(type, outboxStatus);
    }
}
