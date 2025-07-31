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

@Entity
@Table(name = "ordered_books")

@Getter
@Setter
public class OrderedBook {

    @Id // Это ID OrderedBook
    @Column(name = "order_item_id")
    private Long orderItemId;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "order_item_id")
    private OrderItem orderItem;

    @Column(name = "original_book_id", nullable = false)
    private Long originalBookId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "author", nullable = false)
    private String author;

    @Column(name = "isbn", nullable = false)
    private String isbn;

    @Column(name = "published_year", nullable = false)
    private Integer publishedYear;

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
