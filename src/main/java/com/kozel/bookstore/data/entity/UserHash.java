package com.kozel.bookstore.data.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import java.util.Objects;

@Entity
@Table(name = "user_hash")

@Getter
@Setter
@ToString
public class UserHash {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "salt", updatable = false)
    private String salt;

    @Column(name = "hashed_password", updatable = false)
    private String hashedPassword;

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        UserHash hash = (UserHash) o;
        return getId() != null && Objects.equals(getId(), hash.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
