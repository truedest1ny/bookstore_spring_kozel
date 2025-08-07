package com.kozel.bookstore.web.controller.order;

import com.kozel.bookstore.service.OrderService;
import com.kozel.bookstore.service.dto.order.OrderDto;
import com.kozel.bookstore.service.dto.order.OrderItemDto;
import com.kozel.bookstore.service.dto.order.OrderShowingDto;
import com.kozel.bookstore.service.dto.user.UserSessionDto;
import com.kozel.bookstore.web.pagination.InMemoryPaginationHandler;
import com.kozel.bookstore.web.pagination.PaginationValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.kozel.bookstore.util.WebConstants.*;

@Controller
@RequestMapping("/ordered")
@RequiredArgsConstructor
public class AccountOrderController implements PaginationValidator, InMemoryPaginationHandler {

    private final OrderService orderService;

    @GetMapping
    public String getAccountOrders(@PageableDefault(
                                    size = 20, sort = "date", direction = Sort.Direction.ASC)
                                        Pageable pageable,
                                        @SessionAttribute UserSessionDto user,
                                        Model model,
                                        RedirectAttributes attributes) {
        Pageable correctedPageable = correctPageableParams(pageable, ORDER_SORT_PROPERTIES);

        Page<OrderShowingDto> ordersPage = orderService.findByUserId(correctedPageable, user.getId());

        Optional<String> redirectUrl = validateAndRedirectPage(
                ordersPage, correctedPageable, "/ordered", attributes);

        if (redirectUrl.isPresent()) {
            return redirectUrl.get();
        }

        model.addAttribute(PAGE_ATTRIBUTE_KEY, ordersPage);
        model.addAttribute(IS_EMPLOYEE_ATTRIBUTE_KEY, false);

        addSortParamsToModel(model, ordersPage);
        return "order/user_orders";
    }

    @GetMapping("/{id}")
    public String getOrder(@PageableDefault(
                                       size = 20,
                                       sort = "quantity",
                                       direction = Sort.Direction.DESC) Pageable pageable,
                            @PathVariable long id,
                            @SessionAttribute UserSessionDto user,
                            Model model,
                            RedirectAttributes attributes) {

        Pageable correctedPageable = correctPageableParams(pageable, ORDER_ITEMS_SORT_PROPERTIES);

        OrderDto order = orderService.getById(id, user);

        List<OrderItemDto> itemsList = new ArrayList<>(order.getItems());
        applySortToList(itemsList, correctedPageable.getSort());

        Page<OrderItemDto> itemsPage = createPageFromList(itemsList, correctedPageable);

        Optional<String> redirectUrl = validateAndRedirectPage(
                itemsPage, correctedPageable, "/ordered/" + id , attributes);

        if (redirectUrl.isPresent()) {
            return redirectUrl.get();
        }

        model.addAttribute(PAGE_ATTRIBUTE_KEY, itemsPage);
        model.addAttribute(ORDER_ATTRIBUTE_KEY, order);
        model.addAttribute(IS_EMPLOYEE_ATTRIBUTE_KEY, false);
        addSortParamsToModel(model, itemsPage);
        return "order/order";
    }

    @PostMapping("/add")
    public String addOrder(@SessionAttribute UserSessionDto user,
                           RedirectAttributes attributes) {
        OrderDto orderDto = orderService.create(user.getId());

        attributes.addFlashAttribute(SUCCESS_MESSAGE_KEY,
                "The order was successfully placed!");
        return "redirect:/ordered/" + orderDto.getId();
    }

    @PostMapping("/cancel/{id}")
    public String cancelOrder(@PathVariable Long id,
                              @SessionAttribute UserSessionDto user,
                              @RequestParam(required = false) Integer page,
                              @RequestParam(required = false) Integer size,
                              @RequestParam(required = false) List<String> sort,
                              RedirectAttributes attributes) {
        orderService.cancel(id, user);
        attributes.addFlashAttribute(SUCCESS_MESSAGE_KEY,
                "The order was successfully cancelled.");
        addPaginationAttributes(attributes, page, size, sort);
        return "redirect:/ordered";
    }
}
