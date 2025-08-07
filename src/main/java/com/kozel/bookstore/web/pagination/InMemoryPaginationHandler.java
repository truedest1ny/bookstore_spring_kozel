package com.kozel.bookstore.web.pagination;

import com.kozel.bookstore.service.dto.SortableItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public interface InMemoryPaginationHandler {

    default <T> Page<T> createPageFromList(List<T> list, Pageable pageable) {
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), list.size());

        List<T> sublist;
        if (start > end) {
            sublist = Collections.emptyList();
        } else {
            sublist = list.subList(start, end);
        }

        return new PageImpl<>(sublist, pageable, list.size());
    }

    default <T extends SortableItem> Comparator<T> getItemDtoComparator(Sort.Order order) {
        return switch (order.getProperty()) {
            case "book" -> Comparator.comparing(item -> item.getBook().getName());
            case "author" -> Comparator.comparing(item -> item.getBook().getAuthor());
            case "quantity" -> Comparator.comparing(T::getQuantity);
            case "price", "subtotal" -> Comparator.comparing(T::getPrice);
            default -> throw new IllegalArgumentException("Invalid sort property: " + order.getProperty());
        };
    }


    default <T extends SortableItem> void applySortToList(List<T> list, Sort sort) {
        for (Sort.Order order : sort) {
            Comparator<T> comparator = getItemDtoComparator(order);
            if (order.getDirection().isDescending()) {
                comparator = comparator.reversed();
            }
            list.sort(comparator);
        }
    }
}
