package com.kozel.bookstore.data.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Represents a user's shopping cart.
 * A cart is a temporary collection of books a user intends to purchase.
 * It has a one-to-one relationship with a {@link User} and contains a set of
 * {@link CartItem}s. The {@code totalPrice} field reflects the total cost of
 * all items in the cart.
 *
 * @see CartItem
 * @see User
 */
@Entity
@Table(name = "carts")

@Getter
@Setter
public class Cart {

    /**
     * The unique identifier for the cart.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * The user who owns this shopping cart. This is a one-to-one relationship
     * established through a foreign key on the 'carts' table.
     */
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    /**
     * A set of items currently in the cart. This is a one-to-many relationship
     * with {@link CartItem}. The {@code orphanRemoval = true} setting ensures that
     * a {@link CartItem} is automatically deleted from the database if it is
     * removed from this collection.
     */
    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            orphanRemoval = true)
    private Set<CartItem> items = new HashSet<>();

    /**
     * The total price of all items in the cart. This value is updated
     * whenever an item is added, removed, or its quantity is changed.
     */
    @Column(name = "total_price")
    private BigDecimal totalPrice;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Cart cart = (Cart) o;
        return getId() != null && Objects.equals(getId(), cart.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    /**
     * Adds a {@link CartItem} to the cart. This method also handles
     * the bidirectional relationship by setting the item's cart to this instance.
     *
     * @param item The item to add.
     */
    public void addItem(CartItem item) {
        this.getItems().add(item);
        item.setCart(this);
    }

    /**
     * Removes a {@link CartItem} from the cart. This method also handles
     * the bidirectional relationship by nullifying the item's cart.
     *
     * @param item The item to remove.
     */
    public void removeItem(CartItem item) {
        if (item == null) {
            return;
        }
        if (getItems().contains(item)) {
            getItems().remove(item);
            item.setCart(null);
        }
    }
}


