package com.kozel.bookstore.web.controller.order;


import com.kozel.bookstore.service.OrderService;
import com.kozel.bookstore.service.UserService;
import com.kozel.bookstore.service.dto.order.OrderDto;
import com.kozel.bookstore.service.dto.order.OrderItemDto;
import com.kozel.bookstore.service.dto.order.OrderShowingDto;
import com.kozel.bookstore.service.dto.user.UserDto;
import com.kozel.bookstore.service.dto.user.UserSessionDto;
import com.kozel.bookstore.service.dto.user.UserShowingDto;
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
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController implements PaginationValidator, InMemoryPaginationHandler {


    private final OrderService orderService;
    private final UserService userService;

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
                itemsPage, correctedPageable, "/orders/" + id , attributes);

        if (redirectUrl.isPresent()) {
            return redirectUrl.get();
        }
        model.addAttribute(ORDER_ATTRIBUTE_KEY, order);
        model.addAttribute(PAGE_ATTRIBUTE_KEY, itemsPage);
        model.addAttribute(IS_EMPLOYEE_ATTRIBUTE_KEY, true);
        addSortParamsToModel(model, itemsPage);
        return "order/order";
    }

    @GetMapping
    public String getOrders(@PageableDefault(
                                size = 20,
                                sort = "date",
                                direction = Sort.Direction.ASC) Pageable pageable,
                                    Model model,
                                    RedirectAttributes attributes) {

        Pageable correctedPageable = correctPageableParams(pageable, ORDER_SORT_PROPERTIES);
        Page<OrderShowingDto> ordersPage = orderService.getOrdersDtoShort(correctedPageable);

        Optional<String> redirectUrl = validateAndRedirectPage(
                ordersPage, correctedPageable, "/orders", attributes);

        if (redirectUrl.isPresent()) {
            return redirectUrl.get();
        }
        model.addAttribute(PAGE_ATTRIBUTE_KEY, ordersPage);
        addSortParamsToModel(model, ordersPage);
        return "order/orders";
    }

    @GetMapping("/find_by_user")
    public String getByUserForm(Pageable pageable, Model model) {
        Page<UserShowingDto> users = userService.getUsersDtoShort(pageable);
        model.addAttribute(PAGE_ATTRIBUTE_KEY, users);
        return "order/filter_orders_by_user";
    }

    @PostMapping("/find_by_user")
    public String getByUser(@PageableDefault(
                                        size = 20,
                                        sort = "date",
                                        direction = Sort.Direction.ASC) Pageable pageable,
                            @RequestParam(name = "userFilter") String login,
                            Model model,
                            RedirectAttributes attributes) {
        UserDto userDto = userService.getByLogin(login);

        Pageable correctedPageable = correctPageableParams(pageable, ORDER_ITEMS_SORT_PROPERTIES);

        Page<OrderShowingDto> orders = orderService.findByUserId(correctedPageable, userDto.getId());


        Optional<String> redirectUrl = validateAndRedirectPage(
                orders, correctedPageable, "/orders", attributes);

        if (redirectUrl.isPresent()) {
            return redirectUrl.get();
        }

        model.addAttribute(PAGE_ATTRIBUTE_KEY, orders);
        model.addAttribute(IS_EMPLOYEE_ATTRIBUTE_KEY, true);
        addSortParamsToModel(model, orders);
        return "order/user_orders";
    }

    @PostMapping("/archive/{id}")
    public String archiveOrder(@PathVariable Long id,

                               @SessionAttribute UserSessionDto user,
                               @RequestParam(required = false) Integer page,
                               @RequestParam(required = false) Integer size,
                               @RequestParam(required = false) List<String> sort,
                               RedirectAttributes attributes) {
        orderService.archive(id, user);
        attributes.addFlashAttribute(SUCCESS_MESSAGE_KEY,
                "The order was successfully archived.");
        addPaginationAttributes(attributes, page, size, sort);
        return "redirect:/orders";
    }

    @PostMapping("/approve/{id}")
    public String approveOrder(@PathVariable Long id,
                               @SessionAttribute UserSessionDto user,
                               @RequestParam(required = false) Integer page,
                               @RequestParam(required = false) Integer size,
                               @RequestParam(required = false) List<String> sort,
                               RedirectAttributes attributes) {
        orderService.approve(id, user);
        attributes.addFlashAttribute(SUCCESS_MESSAGE_KEY,
                "The order was successfully approved.");
        addPaginationAttributes(attributes, page, size, sort);
        return "redirect:/orders";
    }
}
