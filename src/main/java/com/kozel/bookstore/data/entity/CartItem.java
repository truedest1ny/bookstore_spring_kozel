package com.kozel.bookstore.data.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * Represents a single line item within a shopping cart.
 * A cart item links a specific {@link Book} to a {@link Cart}, storing the quantity
 * and the calculated price for that particular line.
 *
 * @see Cart
 * @see Book
 */
@Entity
@Table(name = "cart_items")

@Getter
@Setter
@ToString
public class CartItem {

    /**
     * The unique identifier for the cart item.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * The book associated with this cart item. This is a many-to-one relationship.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private Book book;

    /**
     * The quantity of the book in this cart item.
     */
    @Column(name = "quantity")
    private int quantity;

    /**
     * The calculated price for this line item (quantity * book price).
     */
    @Column(name = "price")
    private BigDecimal price;

    /**
     * The shopping cart to which this item belongs. This is a many-to-one relationship
     * and a required field.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id", nullable = false)
    private Cart cart;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        CartItem item = (CartItem) o;
        return getId() != null && Objects.equals(getId(), item.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

}
