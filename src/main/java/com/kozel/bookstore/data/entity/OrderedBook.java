package com.kozel.bookstore.data.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * Represents a snapshot of a {@link Book} at the time an order was placed.
 * <p>
 * This entity is designed to store the book's details (name, author, price, etc.)
 * as they were when the order was created. This prevents historical order records
 * from being affected by future changes to the original {@link Book} entity.
 * It has a one-to-one shared primary key with the corresponding {@link OrderItem}.
 * </p>
 *
 * @see Book
 * @see OrderItem
 */
@Entity
@Table(name = "ordered_books")

@Getter
@Setter
public class OrderedBook {

    /**
     * The unique identifier for this ordered book snapshot.
     * This ID is shared with the corresponding {@link OrderItem}.
     */
    @Id
    @Column(name = "order_item_id")
    private Long orderItemId;

    /**
     * The parent {@link OrderItem} that this snapshot belongs to.
     * The {@code @MapsId} annotation indicates that this one-to-one relationship
     * shares the primary key of the parent entity.
     */
    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "order_item_id")
    private OrderItem orderItem;

    /**
     * The ID of the original {@link Book} from which this snapshot was created.
     */
    @Column(name = "original_book_id", nullable = false)
    private Long originalBookId;

    /**
     * The name of the book at the time the order was placed.
     */
    @Column(name = "name", nullable = false)
    private String name;

    /**
     * The author of the book at the time the order was placed.
     */
    @Column(name = "author", nullable = false)
    private String author;

    /**
     * The ISBN of the book at the time the order was placed.
     */
    @Column(name = "isbn", nullable = false)
    private String isbn;

    /**
     * The published year of the book at the time the order was placed.
     */
    @Column(name = "published_year", nullable = false)
    private Integer publishedYear;

    /**
     * The price of the book at the time the order was placed.
     */
    @Column(name = "price_at_order", nullable = false)
    private BigDecimal priceAtOrder;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        OrderedBook that = (OrderedBook) o;
        return getOrderItemId() != null && Objects.equals(getOrderItemId(), that.getOrderItemId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getOrderItemId());
    }
}
