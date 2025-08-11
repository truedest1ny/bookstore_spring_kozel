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
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;
import org.hibernate.annotations.Filter;

import java.util.Objects;

/**
 * Represents a user in the application.
 * This entity stores the user's personal details and role information. It supports
 * soft deletion via the "isDeletedFilter". The user's sensitive password hash
 * is stored separately in the {@link UserHash} entity for security and data separation.
 *
 * @see UserHash
 */
@Entity
@Table(name = "users")

@Filter(name = "isDeletedFilter", condition = "is_deleted = :isDeleted")

@Getter
@Setter
public class User {

    /**
     * The unique identifier for the user.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * The first name of the user.
     */
    @Column(name = "first_name")
    private String firstName;

    /**
     * The last name of the user.
     */
    @Column(name = "last_name")
    private String lastName;

    /**
     * The user's email address.
     */
    @Column (name = "email")
    private String email;

    /**
     * The user's unique login name. This field is immutable once set.
     */
    @Column(name = "login", updatable = false)
    private String login;

    /**
     * The associated password hash and salt for this user. This is a one-to-one
     * relationship with a shared primary key, separating the user's core data
     * from their security credentials.
     * The {@code optional = false} setting ensures that every user must have a password hash.
     */
    @OneToOne(mappedBy = "user",
            cascade = {CascadeType.PERSIST},
            fetch = FetchType.LAZY,
            optional = false)
    private UserHash hash;

    /**
     * The role assigned to the user (e.g., ADMIN, CUSTOMER). This enum is mapped
     * to a numeric database column using the {@link RoleConverter}.
     */
    @Convert(converter = RoleConverter.class)
    @Column(name = "role_id", nullable = false)
    private Role role;

    /**
     * A flag indicating whether the user has been soft-deleted.
     * When {@code true}, the user is hidden from regular queries
     * through the "isDeletedFilter".
     */
    @Column(name = "is_deleted")
    private boolean isDeleted = false;

    /**
     * An enumeration representing the possible roles a user can have.
     */
    public enum Role {
        SUPER_ADMIN,
        ADMIN,
        MANAGER,
        CUSTOMER,
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        User user = (User) o;
        return getId() != null && Objects.equals(getId(), user.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", login='" + login + '\'' +
                ", role=" + role +
                ", isDeleted=" + isDeleted +
                '}';
    }

    /**
     * A JPA attribute converter for mapping the {@link Role} enum to a numeric database column.
     * This converter handles the translation between the Java enum type and the database's Long type.
     */
    @Converter
    public static class RoleConverter implements AttributeConverter<User.Role, Long> {

        @Override
        public Long convertToDatabaseColumn(User.Role role) {
            if (role == null) {
                return null;
            }
            return switch (role) {
                case SUPER_ADMIN -> 1L;
                case ADMIN -> 2L;
                case MANAGER -> 3L;
                case CUSTOMER -> 4L;
            };
        }

        @Override
        public User.Role convertToEntityAttribute(Long dbData) {
            if (dbData == null) {
                return null;
            }
            if (dbData.equals(1L)) {
                return Role.SUPER_ADMIN;
            } else if (dbData.equals(2L)) {
                return Role.ADMIN;
            } else if (dbData.equals(3L)) {
                return Role.MANAGER;
            } else if (dbData.equals(4L)) {
                return Role.CUSTOMER;
            } else {
                throw new IllegalArgumentException("Not found role by id: " + dbData);
            }
        }
    }
}
