package com.kozel.bookstore.web.pagination;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static com.kozel.bookstore.util.WebConstants.*;

/**
 * An interface providing default methods for validating and correcting pagination parameters.
 * These methods ensure that the page size is within limits, sort properties are valid,
 * and that pagination requests do not exceed the total number of available pages.
 */
public interface PaginationValidator {

    Logger log = LoggerFactory.getLogger(PaginationValidator.class);

    /**
     * Validates and adjusts the requested page size to prevent excessively large pages.
     * The page size is capped at {@code MAX_PAGE_SIZE}.
     *
     * @param pageable The original Pageable object from the request.
     * @return A new Pageable object with a corrected page size.
     */
    default Pageable validatePageSize(Pageable pageable) {
        if (pageable.getPageSize() > MAX_PAGE_SIZE) {
            return PageRequest.of(pageable.getPageNumber(), MAX_PAGE_SIZE, pageable.getSort());
        }
        return pageable;
    }

    /**
     * Corrects the pagination parameters by validating the page size and sort properties.
     * It ensures that only allowed sort properties are used.
     *
     * @param pageable The original Pageable object from the request.
     * @param sortProperties A collection of valid sortable property names.
     * @return A new Pageable object with corrected page size and valid sort parameters.
     */
    default Pageable correctPageableParams(Pageable pageable, Collection<String> sortProperties) {
        Pageable correctedPageable = validatePageSize(pageable);
        Sort correctedSort = getValidatedSort(correctedPageable.getSort(), sortProperties);
        return PageRequest.of(
                correctedPageable.getPageNumber(), correctedPageable.getPageSize(), correctedSort);
    }

    /**
     * Validates the requested page number against the total number of pages.
     * If the requested page is out of bounds, this method returns an Optional containing
     * a redirect URL to the last valid page. Otherwise, it returns an empty Optional.
     *
     * @param page The Page object containing the data and pagination metadata.
     * @param pageable The original Pageable object from the request.
     * @param baseUrl The base URL to redirect to in case of invalid page numbers.
     * @param attributes The RedirectAttributes to add pagination parameters for redirection.
     * @param <T> The type of content in the Page.
     * @return An Optional containing a redirect URL if the page is invalid, otherwise empty.
     */
    default <T> Optional<String> validateAndRedirectPage(
            Page<T> page, Pageable pageable, String baseUrl, RedirectAttributes attributes) {

        if (page.getTotalPages() > 0 && pageable.getPageNumber() >= page.getTotalPages()) {
            attributes.addAttribute(PAGE_ATTRIBUTE_KEY, page.getTotalPages() - 1);
            attributes.addAttribute(SIZE_ATTRIBUTE_KEY, pageable.getPageSize());

            pageable.getSort().forEach(order ->
                    attributes.addAttribute(
                            SORT_ATTRIBUTE_KEY,
                            order.getProperty() + "," + order.getDirection().name())
            );

            return Optional.of("redirect:" + baseUrl);
        }

        return Optional.empty();
    }

    /**
     * Adds the current sort parameters to the model for use in the view.
     * This is useful for building dynamic sort links in templates.
     *
     * @param model The Spring Model to add sort parameters to.
     * @param page The Page object containing the current sort information.
     * @param <T> The type of content in the Page.
     */
    default <T> void addSortParamsToModel(Model model, Page<T> page) {
        List<String> sortParams = new ArrayList<>();
        if (page.getSort().isSorted()) {
            for (Sort.Order order : page.getSort()) {
                sortParams.add(order.getProperty() + "," + order.getDirection().name().toLowerCase());
            }
        }
        model.addAttribute(SORT_PARAMETERS_ATTRIBUTE_KEY, sortParams);
    }

    /**
     * Validates the sort properties in a given Sort object.
     * It removes any properties that are not present in the collection of valid properties.
     *
     * @param sort The original Sort object from the request.
     * @param validSortProperties A collection of valid sortable property names.
     * @return A new Sort object with only the valid sort orders.
     */
    default Sort getValidatedSort(Sort sort, Collection<String> validSortProperties) {
        List<Sort.Order> validOrders = new ArrayList<>();

        for (Sort.Order order : sort) {
            if (validSortProperties.contains(order.getProperty())) {
                try {
                    Sort.Direction.valueOf(order.getDirection().name());
                    validOrders.add(order);
                } catch (IllegalArgumentException e) {
                    log.warn("Invalid sort direction '{}' for property '{}'. Ignoring this order.",
                            order.getDirection().name(), order.getProperty());
                }
            }
        }

        if (!validOrders.isEmpty()) {
            return Sort.by(validOrders);
        } else {
            return Sort.unsorted();
        }
    }

    /**
     * Adds pagination parameters to RedirectAttributes for preserving state during redirection.
     * This is used to ensure that the user is redirected to the same page with the same
     * sorting and page size after an action (e.g., canceling an order).
     *
     * @param attributes The RedirectAttributes object.
     * @param page The page number to add.
     * @param size The page size to add.
     * @param sort The list of sort parameters to add.
     */
    default void addPaginationAttributes(RedirectAttributes attributes,
                                         Integer page,
                                         Integer size,
                                         List<String> sort) {
        if (page != null) {
            attributes.addAttribute(PAGE_ATTRIBUTE_KEY, page);
        }
        if (size != null) {
            attributes.addAttribute(SIZE_ATTRIBUTE_KEY, size);
        }
        if (sort != null && !sort.isEmpty()) {
            for (String sortParam : sort) {
                attributes.addAttribute(SORT_ATTRIBUTE_KEY, sortParam);
            }
        }
    }
}
