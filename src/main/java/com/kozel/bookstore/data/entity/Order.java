package com.kozel.bookstore.data.entity;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Converter;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "orders")

@Getter
@Setter
@ToString
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "date")
    private LocalDateTime date;

    @ManyToOne (fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany (cascade = CascadeType.ALL,
                fetch = FetchType.EAGER,
                orphanRemoval = true)
    @JoinColumn (name = "order_id")
    private List<OrderItem> items;

    @Convert(converter = StatusConverter.class)
    @Column(name = "status_id", nullable = false)
    private Status status;

    @Column(name = "price")
    private BigDecimal totalPrice;

    public enum Status {
        PENDING,
        PAID,
        CANCELLED,
        DELIVERED,
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Order order = (Order) o;
        return getId() != null && Objects.equals(getId(), order.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Converter
    public static class StatusConverter implements AttributeConverter<Order.Status, Long> {

        @Override
        public Long convertToDatabaseColumn(Order.Status status) {
            if (status == null) {
                return null;
            }
            return switch (status) {
                case PENDING -> 1L;
                case PAID -> 2L;
                case CANCELLED -> 3L;
                case DELIVERED -> 4L;
            };
        }

        @Override
        public Order.Status convertToEntityAttribute(Long dbData) {
            if (dbData == null) {
                return null;
            }
            if (dbData.equals(1L)) {
                return Status.PENDING;
            } else if (dbData.equals(2L)) {
                return Status.PAID;
            } else if (dbData.equals(3L)) {
                return Status.CANCELLED;
            } else if (dbData.equals(4L)) {
                return Status.DELIVERED;
            } else {
                throw new IllegalArgumentException("Not found status by id: " + dbData);
            }
        }
    }
}


