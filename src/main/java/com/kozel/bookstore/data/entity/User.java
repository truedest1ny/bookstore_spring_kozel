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


@Entity
@Table(name = "users")

@Filter(name = "isDeletedFilter", condition = "is_deleted = :isDeleted")

@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column (name = "email")
    private String email;

    @Column(name = "login", updatable = false)
    private String login;

    @OneToOne(mappedBy = "user",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE},
            fetch = FetchType.LAZY,
            orphanRemoval = true)
    private UserHash hash;

    @Convert(converter = RoleConverter.class)
    @Column(name = "role_id", nullable = false)
    private Role role;

    @Column(name = "is_deleted")
    private boolean isDeleted = false;

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
        return getClass().hashCode();
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
