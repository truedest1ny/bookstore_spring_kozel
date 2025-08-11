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

import java.util.Objects;

/**
 * Stores the security-sensitive password hash and salt for a {@link User}.
 * <p>
 * This entity has a one-to-one relationship with {@link User} and uses the
 * {@code @MapsId} annotation to share the same primary key. This design
 * ensures that password information is stored separately from general user data.
 * </p>
 *
 * @see User
 */
@Entity
@Table(name = "user_hash")

@Getter
@Setter
public class UserHash {

    /**
     * The unique identifier for this password hash entry, which is the same as the associated user's ID.
     */
    @Id
    @Column(name = "user_id")
    private Long id;

    /**
     * The parent {@link User} entity to which this password hash belongs.
     * The {@code @MapsId} annotation indicates a shared primary key relationship.
     */
    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "user_id")
    private User user;

    /**
     * The salt used to hash the user's password.
     */
    @Column(name = "salt")
    private String salt;

    /**
     * The hashed password of the user.
     */
    @Column(name = "hashed_password")
    private String hashedPassword;

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        UserHash hash = (UserHash) o;
        return getId() != null && Objects.equals(getId(), hash.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

}
