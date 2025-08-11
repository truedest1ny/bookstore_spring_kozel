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

/**
 * Controller for managing a user's personal order history and details.
 * This controller handles displaying a user's orders, viewing specific order details,
 * creating new orders from the user's cart, and canceling existing orders.
 * It also includes helper methods for pagination and sorting.
 */
@Controller
@RequestMapping("/ordered")
@RequiredArgsConstructor
public class AccountOrderController implements PaginationValidator, InMemoryPaginationHandler {

    private final OrderService orderService;

    /**
     * Displays a paginated list of all orders for the logged-in user.
     * The method fetches orders by the user's ID and applies pagination and sorting.
     * It also handles redirecting to the last page if the requested page number is out of bounds.
     *
     * @param pageable The pagination and sorting parameters provided by the request.
     * @param user The session attribute containing the current user's details.
     * @param model The Spring Model for adding view attributes.
     * @param attributes RedirectAttributes for handling flash messages and redirection.
     * @return The view name for the user's order list page.
     */
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

    /**
     * Displays the details of a specific order for the logged-in user.
     * The method retrieves the full order and then handles in-memory pagination and
     * sorting for its order items.
     *
     * @param pageable The pagination and sorting parameters for the order items.
     * @param id The unique ID of the order to display.
     * @param user The session attribute containing the current user's details.
     * @param model The Spring Model for adding view attributes.
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

    /**
     * Creates a new order from the user's shopping cart.
     * Upon successful creation, the user is redirected to the newly created order's details page.
     *
     * @param user The session attribute containing the current user's details.
     * @param attributes RedirectAttributes for handling flash messages and redirection.
     * @return A redirect string to the newly created order's details page.
     */
    @PostMapping("/add")
    public String addOrder(@SessionAttribute UserSessionDto user,
                           RedirectAttributes attributes) {
        OrderDto orderDto = orderService.create(user.getId());

        attributes.addFlashAttribute(SUCCESS_MESSAGE_KEY,
                "The order was successfully placed!");
        return "redirect:/ordered/" + orderDto.getId();
    }

    /**
     * Cancels an existing order for the logged-in user.
     * The user is redirected back to their order list page with a success message.
     * The method also ensures that pagination and sorting parameters are preserved in the redirect.
     *
     * @param id The unique ID of the order to cancel.
     * @param user The session attribute containing the current user's details.
     * @param page The current page number for redirection.
     * @param size The current page size for redirection.
     * @param sort The current sort parameters for redirection.
     * @param attributes RedirectAttributes for handling flash messages and redirection.
     * @return A redirect string to the user's order list page.
     */
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
