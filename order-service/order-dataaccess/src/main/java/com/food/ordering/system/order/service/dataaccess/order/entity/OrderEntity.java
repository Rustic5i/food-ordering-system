package com.food.ordering.system.order.service.dataaccess.order.entity;

import com.food.ordering.system.domain.valueobject.OrderStatus;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "orders")
@Entity
public class OrderEntity {

    @Id
    private UUID id;

    private UUID customerId;

    private UUID restaurantId;

    private UUID trackingId;

    private BigDecimal price;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    private String failureMessages;

    @OneToOne(mappedBy = "order", cascade = CascadeType.ALL)
    private OrderAddressEntity address;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "order")
    private List<OrderItemEntity> items;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrderEntity that)) return false;
        return Objects.equals(getId(), that.getId()) && Objects.equals(getCustomerId(), that.getCustomerId()) && Objects.equals(getRestaurantId(), that.getRestaurantId()) && Objects.equals(getTrackingId(), that.getTrackingId()) && Objects.equals(getPrice(), that.getPrice()) && getOrderStatus() == that.getOrderStatus() && Objects.equals(getFailureMessages(), that.getFailureMessages()) && Objects.equals(getAddress(), that.getAddress()) && Objects.equals(getItems(), that.getItems());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getCustomerId(), getRestaurantId(), getTrackingId(), getPrice(), getOrderStatus(), getFailureMessages(), getAddress(), getItems());
    }
}
