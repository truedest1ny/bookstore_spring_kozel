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
import org.hibernate.Hibernate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Represents a complete order placed by a user.
 * An order is a record of a transaction at a specific point in time. It contains
 * details about the user, the date of the order, its current status, and a collection
 * of {@link OrderItem}s.
 *
 * @see OrderItem
 * @see User
 */
@Entity
@Table(name = "orders")

@Getter
@Setter
public class Order {

    /**
     * The unique identifier for the order.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * The date and time the order was created. This field is immutable.
     */
    @Column(name = "date", updatable = false)
    private LocalDateTime date;

    /**
     * The user who placed this order. This is a many-to-one relationship.
     */
    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    /**
     * A set of items included in this order. This is a one-to-many relationship
     * with {@link OrderItem}. The {@code orphanRemoval = true} setting ensures
     * that an {@link OrderItem} is automatically deleted from the database if
     * it is removed from this collection.
     */
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            orphanRemoval = true)
    private Set<OrderItem> items = new HashSet<>();

    /**
     * The current status of the order (e.g., PENDING, PAID, CANCELLED).
     * This enum is mapped to a numeric database column using the {@link StatusConverter}.
     */
    @Convert(converter = StatusConverter.class)
    @Column(name = "status_id", nullable = false)
    private Status status;

    /**
     * The total price of the entire order.
     */
    @Column(name = "price")
    private BigDecimal totalPrice = BigDecimal.ZERO;

    /**
     * An enumeration representing the possible states of an order.
     */
    public enum Status {
        PENDING,
        PAID,
        CANCELLED,
        DELIVERED,
        ARCHIVED,
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
        return Objects.hash(getId());
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", date=" + date +
                ", status=" + status +
                ", totalPrice=" + totalPrice +
                '}';
    }

    /**
     * Adds an {@link OrderItem} to the order. This method also handles
     * the bidirectional relationship by setting the item's order to this instance.
     *
     * @param item The item to add.
     */
    public void addItem(OrderItem item) {
        this.getItems().add(item);
        item.setOrder(this);
    }

    /**
     * Removes an {@link OrderItem} from the order. This method also handles
     * the bidirectional relationship by nullifying the item's order.
     *
     * @param item The item to remove.
     */
    public void removeItem(OrderItem item) {
        if (getItems().contains(item)) {
            getItems().remove(item);
            item.setOrder(null);
        }
    }

    /**
     * A JPA attribute converter for mapping the {@link Status} enum to a numeric database column.
     * This converter handles the translation between the Java enum type and the database's Long type.
     */
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
                case ARCHIVED -> 5L;
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
            } else if (dbData.equals(5L)) {
                return Status.ARCHIVED;
            } else {
                throw new IllegalArgumentException("Not found status by id: " + dbData);
            }
        }
    }
}


