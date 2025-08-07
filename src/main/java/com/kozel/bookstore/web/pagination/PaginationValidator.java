package com.kozel.bookstore.web.pagination;

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

public interface PaginationValidator {

    default Pageable validatePageSize(Pageable pageable) {
        if (pageable.getPageSize() > MAX_PAGE_SIZE) {
            return PageRequest.of(pageable.getPageNumber(), MAX_PAGE_SIZE, pageable.getSort());
        }
        return pageable;
    }

    default Pageable correctPageableParams(Pageable pageable, Collection<String> sortProperties) {
        Pageable correctedPageable = validatePageSize(pageable);
        Sort correctedSort = getValidatedSort(correctedPageable.getSort(), sortProperties);
        return PageRequest.of(
                correctedPageable.getPageNumber(), correctedPageable.getPageSize(), correctedSort);
    }

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

    default <T> void addSortParamsToModel(Model model, Page<T> page) {
        List<String> sortParams = new ArrayList<>();
        if (page.getSort().isSorted()) {
            for (Sort.Order order : page.getSort()) {
                sortParams.add(order.getProperty() + "," + order.getDirection().name().toLowerCase());
            }
        }
        model.addAttribute(SORT_PARAMETERS_ATTRIBUTE_KEY, sortParams);
    }

    default Sort getValidatedSort(Sort sort, Collection<String> validSortProperties) {
        List<Sort.Order> validOrders = new ArrayList<>();

        for (Sort.Order order : sort) {
            if (validSortProperties.contains(order.getProperty())) {
                try {
                    Sort.Direction.valueOf(order.getDirection().name());
                    validOrders.add(order);
                } catch (IllegalArgumentException ignored) {

                }
            }
        }

        if (!validOrders.isEmpty()) {
            return Sort.by(validOrders);
        } else {
            return Sort.unsorted();
        }
    }

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
