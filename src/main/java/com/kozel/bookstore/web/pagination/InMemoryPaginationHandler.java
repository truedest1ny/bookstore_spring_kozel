package com.kozel.bookstore.web.pagination;

import com.kozel.bookstore.service.dto.SortableItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * An interface for handling in-memory pagination and sorting of lists.
 * This is particularly useful for paginating and sorting collections that
 * are not managed by a database repository, such as items within an order.
 */
public interface InMemoryPaginationHandler {

    /**
     * Creates a {@link Page} object from a given list based on {@link Pageable} parameters.
     * This method efficiently extracts a sublist corresponding to the requested page
     * and returns it wrapped in a Page object.
     *
     * @param list The source list to be paginated.
     * @param pageable The pagination information.
     * @param <T> The type of elements in the list.
     * @return A Page object containing the requested sublist and pagination metadata.
     */
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

    /**
     * Returns a {@link Comparator} for an item based on a given sort order.
     * The method uses a switch statement to dynamically choose the correct comparator
     * based on the property specified in the sort order.
     *
     * @param order The sort order containing the property to sort by.
     * @param <T> The type of the sortable item.
     * @return A Comparator for the specified property.
     * @throws IllegalArgumentException if the sort property is invalid.
     */
    default <T extends SortableItem> Comparator<T> getItemDtoComparator(Sort.Order order) {
        return switch (order.getProperty()) {
            case "book" -> Comparator.comparing(item -> item.getBook().getName());
            case "author" -> Comparator.comparing(item -> item.getBook().getAuthor());
            case "quantity" -> Comparator.comparing(T::getQuantity);
            case "price", "subtotal" -> Comparator.comparing(T::getPrice);
            default -> throw new IllegalArgumentException("Invalid sort property: " + order.getProperty());
        };
    }

    /**
     * Applies sorting to a given list in-place based on the provided {@link Sort} object.
     * This method iterates through the sort orders and applies the appropriate comparator,
     * reversing it for descending directions.
     *
     * @param list The list to be sorted.
     * @param sort The Sort object defining the sorting criteria.
     * @param <T> The type of the sortable item.
     */
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
