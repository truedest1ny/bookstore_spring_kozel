package com.kozel.bookstore.service.impl;

import com.kozel.bookstore.data.entity.Cart;
import com.kozel.bookstore.data.entity.CartItem;
import com.kozel.bookstore.data.entity.Order;
import com.kozel.bookstore.data.entity.OrderItem;
import com.kozel.bookstore.data.mapper.DataMapper;
import com.kozel.bookstore.data.repository.CartRepository;
import com.kozel.bookstore.data.repository.OrderRepository;
import com.kozel.bookstore.service.OrderService;
import com.kozel.bookstore.service.dto.order.OrderDto;
import com.kozel.bookstore.service.dto.order.OrderShowingDto;
import com.kozel.bookstore.service.dto.user.UserSessionDto;
import com.kozel.bookstore.service.exception.AuthorizationException;
import com.kozel.bookstore.service.exception.BusinessException;
import com.kozel.bookstore.service.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;
    private final DataMapper mapper;


    @Override
    public List<OrderDto> getAll() {
        log.debug("Called getAll() method");
        return mapper.toOrderDtoList(
                orderRepository.findAll());
    }

    @Override
    public List<OrderShowingDto> getOrdersDtoShort() {
        log.debug("Called getOrdersDtoShort() method");
        return mapper.toOrderShowingDtoList(
                orderRepository.findAll());
    }

    @Override
    public List<OrderShowingDto> findByUserId(Long userId) {
        log.debug("Called findByUserId() method");
        return mapper.toOrderShowingDtoList(
                orderRepository.findByUserId(userId));
    }

    @Override
    public OrderDto getById(Long id, UserSessionDto user) {
        log.debug("Called getById() method");
            Order order = orderRepository.findById(id).orElseThrow(
                    () -> new ResourceNotFoundException(
                            "Cannot find order by id " + id)
            );

        validateOrderAffiliation(user, order);
        return mapper.toDto(order);
    }

    @Override
    public OrderDto create(Long userId) {
        log.debug("Called create() method");


        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Cart not found with user ID: " + userId));

        if (cart.getItems().isEmpty()) {
            throw new BusinessException(
                    "Cannot create an order from an empty cart.");
        }

        Order order = mapper.toOrder(cart);
        order.setDate(LocalDateTime.now());
        for (OrderItem orderItem : order.getItems()) {
            CartItem correspondingCartItem = cart.getItems().stream()
                    .filter(ci -> ci.getBook().getId().equals(orderItem.getBook().getOriginalBookId()))
                    .findFirst()
                    .orElseThrow(() -> new IllegalStateException(
                            "Cart item was not for book with ID " +
                                    orderItem.getBook().getOriginalBookId()));
            BigDecimal itemLinePrice = correspondingCartItem.getBook().getPrice()
                                        .multiply(new BigDecimal(orderItem.getQuantity()));
            orderItem.setPrice(itemLinePrice);
        }
        updateOrderTotalPrice(order);
        Order savedOrder = orderRepository.save(order);

        cartRepository.deleteItemsByCartId(cart.getId());
        cart.setTotalPrice(BigDecimal.ZERO);
        cartRepository.save(cart);

        return mapper.toDto(savedOrder);
    }




    @Override
    public OrderDto update(OrderDto orderDto) {
        log.debug("Called update() method");

        if (orderDto.getId() == null) {
            throw new IllegalArgumentException(
                    "Order ID must be provided for update operation.");
        }

        Order existingOrder = orderRepository.findById(orderDto.getId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Order not found for update with ID: " + orderDto.getId()));

        if (existingOrder.getStatus() != Order.Status.PENDING){
            throw new BusinessException(
                    "Cannot update an approved order!");
        }

        //TODO: add update order logic

        updateOrderTotalPrice(existingOrder);
        Order savedOrder = orderRepository.save(existingOrder);
        return mapper.toDto(savedOrder);
    }

    @Override
    public void archive(Long orderId, UserSessionDto user) {
        log.debug("Called archive() method");
            Order orderToArchive = orderRepository.findById(orderId)
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Cannot find order (id = " + orderId + ")"));

            if (user.getRole() != UserSessionDto.Role.ADMIN
                    && user.getRole() != UserSessionDto.Role.SUPER_ADMIN) {
                throw new AuthorizationException(
                        "Order with ID " + orderId + " cannot be archived." +
                                " You don't have corresponding rights");
            }

            if (orderToArchive.getStatus() != Order.Status.CANCELLED) {
                throw new BusinessException(
                        "Order with ID " + orderId + " cannot be archived in its current status ("
                        + orderToArchive.getStatus() + ").");
            }

            orderRepository.delete(orderToArchive);
    }

    @Override
    public void approve(Long orderId, UserSessionDto user) {
        log.debug("Called approve() method");
            Order orderToApprove = orderRepository.findById(orderId)
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Cannot find order (id = " + orderId + ")"));

            if (user.getRole() == UserSessionDto.Role.CUSTOMER) {
                throw new AuthorizationException(
                        "Order with ID " + orderId + " cannot be approved." +
                                " You don't have corresponding rights");
            }

            if (orderToApprove.getStatus() != Order.Status.PENDING) {
                throw new BusinessException(
                        "Order with ID " + orderId + " cannot be approved in its current status ("
                                + orderToApprove.getStatus() + ").");
            }

            orderToApprove.setStatus(Order.Status.PAID);
            orderRepository.save(orderToApprove);
    }

    @Override
    public void cancel(Long orderId, UserSessionDto user) {
        log.debug("Called cancel() method");
            Order orderToCancel = orderRepository.findById(orderId)
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Cannot find order (id = " + orderId + ")"));

            validateOrderAffiliation(user, orderToCancel);

            if (user.getRole() != UserSessionDto.Role.CUSTOMER) {
                throw new AuthorizationException(
                        "Order with ID " + orderId + " cannot be cancelled." +
                                " You don't have corresponding rights");
            }

            if (orderToCancel.getStatus() != Order.Status.PENDING) {
                throw new BusinessException(
                        "Order with ID " + orderId + " cannot be cancelled in its current status ("
                                + orderToCancel.getStatus() + ").");
            }

            orderToCancel.setStatus(Order.Status.CANCELLED);
            orderRepository.save(orderToCancel);
    }

    private void validateOrderAffiliation(UserSessionDto user, Order order) {
        if (user.getRole() != UserSessionDto.Role.CUSTOMER){
            return;
        }

        if (!order.getUser().getId().equals(user.getId())) {
            throw new AuthorizationException(
                    "Access Denied: You do not have permission to view this order.");
        }
    }

    private void updateOrderTotalPrice(Order order) {
        BigDecimal newTotalPrice = order.getItems().stream()
                .map(OrderItem::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        order.setTotalPrice(newTotalPrice);
    }
}
