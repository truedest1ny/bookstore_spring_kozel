package com.kozel.bookstore.controller;


import com.kozel.bookstore.service.OrderService;
import com.kozel.bookstore.service.UserService;
import com.kozel.bookstore.service.dto.OrderDto;
import com.kozel.bookstore.service.dto.OrderShowingDto;
import com.kozel.bookstore.service.dto.UserDto;
import com.kozel.bookstore.service.dto.UserShowingDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final UserService userService;

    @GetMapping("/{id}")
    public String getOrder(@PathVariable long id, Model model) {
        OrderDto order = orderService.getById(id);
        model.addAttribute("order", order);
        model.addAttribute("items", order.getItems());
        return "order/order";
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
        model.addAttribute("login", login);
        return "order/orders_by_user";
    }

    @GetMapping
    public String getOrders(Model model) {
        List<OrderShowingDto> orders = orderService.getOrdersDtoShort();
        model.addAttribute("orders", orders);
        return "order/orders";
    }

}
