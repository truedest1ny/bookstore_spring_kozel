
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


@Entity
@Table(name = "books")

@Filter(name = "isDeletedFilter", condition = "is_deleted = :isDeleted")

@Getter
@Setter
@ToString
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "isbn", updatable = false)
    private String isbn;

    @Convert(converter = CoverConverter.class)
    @Column(name = "cover_id", nullable = false)
    private Cover cover;

    @Column(name = "author")
    private String author;

    @Column(name = "published_year")
    private Integer publishedYear;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "is_deleted")
    private boolean isDeleted = false;

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
        return getClass().hashCode();
    }

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
