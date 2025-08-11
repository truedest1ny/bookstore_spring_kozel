
package com.kozel.bookstore.data.entity;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Converter;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;
import org.hibernate.annotations.Filter;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * Represents a book in the bookstore's inventory.
 * This entity stores detailed information about a book, including its title, author,
 * ISBN, cover type, and price. It also supports soft deletion through the {@code isDeleted} flag,
 * which is managed by a Hibernate filter named "isDeletedFilter".
 *
 */
@Entity
@Table(name = "books")

@Filter(name = "isDeletedFilter", condition = "is_deleted = :isDeleted")

@Getter
@Setter
@ToString
public class Book {

    /**
     * The unique identifier for the book.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * The title of the book.
     */
    @Column(name = "name")
    private String name;

    /**
     * The unique ISBN of the book.
     * This field is immutable once set, as indicated by {@code updatable = false}.
     */
    @Column(name = "isbn", updatable = false)
    private String isbn;

    /**
     * The type of book cover (e.g., HARD, SOFT). This is stored in the database as a numeric ID
     * and converted to a Java enum using the {@link CoverConverter}.
     */
    @Convert(converter = CoverConverter.class)
    @Column(name = "cover_id", nullable = false)
    private Cover cover;

    /**
     * The name of the book's author.
     */
    @Column(name = "author")
    private String author;

    /**
     * The year the book was published.
     */
    @Column(name = "published_year")
    private Integer publishedYear;

    /**
     * The price of the book.
     */
    @Column(name = "price")
    private BigDecimal price;

    /**
     * A flag indicating whether the book has been soft-deleted.
     * When {@code true}, the book is hidden from regular queries
     * through the use of the "isDeletedFilter".
     */
    @Column(name = "is_deleted")
    private boolean isDeleted = false;

    /**
     * An enumeration representing the available types of book covers.
     */
    public enum Cover
    {
        HARD,
        SOFT,
        SPECIAL,
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Book book = (Book) o;
        return getId() != null && Objects.equals(getId(), book.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    /**
     * A JPA attribute converter for mapping the {@link Cover} enum to a numeric database column.
     * This converter ensures type safety and handles the translation between the Java enum type
     * and the database's Long type.
     */
    @Converter
    public static class CoverConverter implements AttributeConverter<Book.Cover, Long> {

        @Override
        public Long convertToDatabaseColumn(Book.Cover cover) {
            if (cover == null) {
                return null;
            }
            return switch (cover) {
                case HARD -> 1L;
                case SOFT -> 2L;
                case SPECIAL -> 3L;
            };
        }

        @Override
        public Book.Cover convertToEntityAttribute(Long dbData) {
            if (dbData == null) {
                return null;
            }
            if (dbData.equals(1L)) {
                return Book.Cover.HARD;
            } else if (dbData.equals(2L)) {
                return Book.Cover.SOFT;
            } else if (dbData.equals(3L)) {
                return Book.Cover.SPECIAL;
            } else {
                throw new IllegalArgumentException("Not found cover by id: " + dbData);
            }
        }
    }
}
