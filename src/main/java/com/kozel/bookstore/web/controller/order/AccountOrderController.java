package com.kozel.bookstore.web.controller.order;

import com.kozel.bookstore.service.OrderService;
import com.kozel.bookstore.service.dto.order.OrderDto;
import com.kozel.bookstore.service.dto.order.OrderShowingDto;
import com.kozel.bookstore.service.dto.user.UserSessionDto;
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

import static com.kozel.bookstore.util.WebConstants.*;

@Controller
@RequestMapping("/ordered")
@RequiredArgsConstructor
public class AccountOrderController {

    private final OrderService orderService;

    @GetMapping
    public String getAccountOrders(@SessionAttribute UserSessionDto user, Model model) {
        List<OrderShowingDto> orders = orderService.findByUserId(user.getId());
        model.addAttribute(ORDERS_ATTRIBUTE_KEY, orders);
        model.addAttribute(IS_EMPLOYEE_ATTRIBUTE_KEY, false);
        return "order/user_orders";
    }

    @GetMapping("/{id}")
    public String getOrder(@PathVariable long id,
                           @SessionAttribute UserSessionDto user,
                           Model model) {
        OrderDto order = orderService.getById(id, user);

        model.addAttribute(ORDER_ATTRIBUTE_KEY, order);
        model.addAttribute(IS_EMPLOYEE_ATTRIBUTE_KEY, false);
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
                              RedirectAttributes attributes) {
        orderService.cancel(id, user);
        attributes.addFlashAttribute(SUCCESS_MESSAGE_KEY,
                "The order was successfully cancelled.");
        return "redirect:/ordered";
    }
}
