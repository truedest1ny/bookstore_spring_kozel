package com.kozel.bookstore.web.controller;

import com.kozel.bookstore.service.CartService;
import com.kozel.bookstore.service.OrderService;
import com.kozel.bookstore.service.dto.CartDto;
import com.kozel.bookstore.service.dto.OrderDto;
import com.kozel.bookstore.service.dto.OrderShowingDto;
import com.kozel.bookstore.service.dto.UserSessionDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/ordered")
@RequiredArgsConstructor
public class AccountOrderController {

    private final OrderService orderService;
    private final CartService cartService;

    @GetMapping
    public String getAccountOrders(@SessionAttribute UserSessionDto user, Model model) {
        List<OrderShowingDto> orders = orderService.findByUserId(user.getId());
        model.addAttribute("orders", orders);
        model.addAttribute("isEmployee", false);
        return "order/user_orders";
    }

    @GetMapping("/{id}")
    public String getOrder(@PathVariable long id,
                           @SessionAttribute UserSessionDto user,
                           Model model) {
        OrderDto order = orderService.getById(id, user);

        model.addAttribute("order", order);
        model.addAttribute("isEmployee", false);
        return "order/order";
    }

    @PostMapping("/add")
    public String addOrder(@SessionAttribute UserSessionDto user,
                           RedirectAttributes attributes) {
        CartDto cartDto = cartService.findByUserId(user.getId());
        OrderDto orderDto = orderService.create(cartDto);

        attributes.addFlashAttribute("order", orderDto);
        attributes.addFlashAttribute("success",
                "The order was successfully placed!");
        return "redirect:/ordered/" + orderDto.getId();
    }

    @PostMapping("/cancel/{id}")
    public String cancelOrder(@PathVariable Long id,
                              @SessionAttribute UserSessionDto user,
                              RedirectAttributes attributes) {
        orderService.cancel(id, user);
        attributes.addFlashAttribute("success",
                "The order was successfully cancelled.");
        return "redirect:/ordered";
    }
}
