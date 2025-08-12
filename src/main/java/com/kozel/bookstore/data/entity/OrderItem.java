package com.kozel.bookstore.data.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * Represents a single line item within an order.
 * An order item links a specific ordered book snapshot to an {@link Order},
 * storing the quantity and the calculated price for that particular line.
 * It has a one-to-one shared-key relationship with {@link OrderedBook}.
 *
 * @see Order
 * @see OrderedBook
 */
@Entity
@Table(name = "order_items")

@Getter
@Setter
public class OrderItem {

    /**
     * The unique identifier for the order item.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * The snapshot of the book at the time of the order. This is a one-to-one
     * relationship with a shared primary key.
     */
    @OneToOne(mappedBy = "orderItem",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY)
    private OrderedBook book;

    /**
     * The quantity of the book in this order item.
     */
    @Column(name = "quantity")
    private int quantity;

    /**
     * The price for this line item (quantity * the book's price at the time of order).
     */
    @Column(name = "price")
    private BigDecimal price;

    /**
     * The order to which this item belongs. This is a many-to-one relationship
     * and a required field.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        OrderItem item = (OrderItem) o;
        return getId() != null && Objects.equals(getId(), item.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    @Override
    public String toString() {
        return "OrderItem{" +
                "id=" + id +
                ", quantity=" + quantity +
                ", price=" + price +
                '}';
    }
}


