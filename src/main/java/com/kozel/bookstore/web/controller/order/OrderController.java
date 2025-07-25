package com.kozel.bookstore.web.controller.order;


import com.kozel.bookstore.service.OrderService;
import com.kozel.bookstore.service.UserService;
import com.kozel.bookstore.service.dto.order.OrderDto;
import com.kozel.bookstore.service.dto.order.OrderShowingDto;
import com.kozel.bookstore.service.dto.user.UserDto;
import com.kozel.bookstore.service.dto.user.UserSessionDto;
import com.kozel.bookstore.service.dto.user.UserShowingDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final UserService userService;

    @GetMapping("/{id}")
    public String getOrder(@PathVariable long id,
                           @SessionAttribute UserSessionDto user,
                           Model model) {
        OrderDto order = orderService.getById(id, user);
        model.addAttribute("order", order);
        model.addAttribute("isEmployee", true);
        return "order/order";
    }

    @GetMapping
    public String getOrders(Model model) {
        List<OrderShowingDto> orders = orderService.getOrdersDtoShort();
        model.addAttribute("orders", orders);
        return "order/orders";
    }

    @GetMapping("/find_by_user")
    public String getByUserForm(Model model) {
        List<UserShowingDto> users = userService.getUsersDtoShort();
        model.addAttribute("users", users);
        return "order/filter_orders_by_user";
    }

    @PostMapping("/find_by_user")
    public String getByUser(@RequestParam(name = "userFilter") String login, Model model) {
        UserDto userDto = userService.getByLogin(login);

        List<OrderShowingDto> orders = orderService.findByUserId(userDto.getId());
        model.addAttribute("orders", orders);
        model.addAttribute("isEmployee", true);
        return "order/user_orders";
    }

    @PostMapping("/archive/{id}")
    public String archiveOrder(@PathVariable Long id,
                               @SessionAttribute UserSessionDto user,
                               RedirectAttributes attributes) {
        orderService.archive(id, user);
        attributes.addFlashAttribute("success",
                "The order was successfully archived.");
        return "redirect:/orders";
    }

    @PostMapping("/approve/{id}")
    public String approveOrder(@PathVariable Long id,
                               @SessionAttribute UserSessionDto user,
                               RedirectAttributes attributes) {
        orderService.approve(id, user);
        attributes.addFlashAttribute("success",
                "The order was successfully approved.");
        return "redirect:/orders";
    }
}
