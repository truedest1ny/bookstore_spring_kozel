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

/**
 * Controller for managing all orders from an administrative or employee perspective.
 * This class provides functionalities to view, filter, approve, and archive orders
 * across all users in the system.
 */
@Controller
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController implements PaginationValidator, InMemoryPaginationHandler {

    private final OrderService orderService;
    private final UserService userService;

    /**
     * Displays the details of a specific order.
     * This method is for employees to view any order by its ID, including its
     * paginated and sorted items. The order items are sorted in-memory.
     *
     * @param pageable The pagination and sorting parameters for the order items.
     * @param id The unique ID of the order to display.
     * @param user The session attribute containing the current employee's details (for authorization checks).
     * @param model The Spring Model to add view attributes.
     * @param attributes RedirectAttributes for handling flash messages and redirection.
     * @return The view name for the order details page.
     */
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

    /**
     * Displays a paginated list of all orders in the system.
     * This serves as the primary dashboard for employees to see all orders.
     * Pagination and sorting are handled by the database repository.
     *
     * @param pageable The pagination and sorting parameters.
     * @param model The Spring Model to add view attributes.
     * @param attributes RedirectAttributes for redirection handling.
     * @return The view name for the orders list page.
     */
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

    /**
     * Displays a form page for employees to find orders by a user's login.
     * It also populates the model with a paginated list of users for convenience.
     *
     * @param pageable The pagination parameters for the user list.
     * @param model The Spring Model to add the user list to.
     * @return The view name for the user filter form page.
     */
    @GetMapping("/find_by_user")
    public String getByUserForm(Pageable pageable, Model model) {
        Page<UserShowingDto> users = userService.getUsersDtoShort(pageable);
        model.addAttribute(PAGE_ATTRIBUTE_KEY, users);
        return "order/filter_orders_by_user";
    }

    /**
     * Filters and displays a paginated list of orders placed by a specific user.
     * The user is identified by their login, which is provided through the form.
     *
     * @param pageable The pagination and sorting parameters for the orders.
     * @param login The login of the user to filter orders by.
     * @param model The Spring Model to add the filtered orders to.
     * @param attributes RedirectAttributes for redirection handling.
     * @return The view name for the filtered user's orders page.
     */
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

    /**
     * Archives a specific order.
     * After archiving, the user is redirected to the main orders list,
     * preserving their pagination state.
     *
     * @param id The unique ID of the order to archive.
     * @param user The session attribute containing the current employee's details.
     * @param page The current page number for redirection.
     * @param size The current page size for redirection.
     * @param sort The current sort parameters for redirection.
     * @param attributes RedirectAttributes for handling flash messages and redirection.
     * @return A redirect string to the main orders list page.
     */
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

    /**
     * Approves a specific order.
     * After approval, the user is redirected to the main orders list,
     * preserving their pagination state.
     *
     * @param id The unique ID of the order to approve.
     * @param user The session attribute containing the current employee's details.
     * @param page The current page number for redirection.
     * @param size The current page size for redirection.
     * @param sort The current sort parameters for redirection.
     * @param attributes RedirectAttributes for handling flash messages and redirection.
     * @return A redirect string to the main orders list page.
     */
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
