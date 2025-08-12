package com.kozel.bookstore.service;


import com.kozel.bookstore.service.dto.order.OrderDto;
import com.kozel.bookstore.service.dto.order.OrderShowingDto;
import com.kozel.bookstore.service.dto.user.UserSessionDto;
import com.kozel.bookstore.service.exception.AuthorizationException;
import com.kozel.bookstore.service.exception.BusinessException;
import com.kozel.bookstore.service.exception.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * A service for managing order business logic.
 * This service provides methods for retrieving, creating, and managing
 * the lifecycle of orders, including status changes and security checks.
 */
public interface OrderService {

    /**
     * Retrieves all orders with their full details in a paginated format.
     * This method is intended for administrative use.
     *
     * @param pageable The pagination information.
     * @return A paginated list of all orders as {@link OrderDto}.
     */
    Page<OrderDto> getAll(Pageable pageable);

    /**
     * Retrieves all orders with a simplified view in a paginated format.
     * This is suitable for lists where a summary is sufficient.
     *
     * @param pageable The pagination information.
     * @return A paginated list of simplified orders as {@link OrderShowingDto}.
     */
    Page<OrderShowingDto> getOrdersDtoShort(Pageable pageable);

    /**
     * Retrieves a single order by its unique identifier.
     * Access is restricted to employees or the user who owns the order.
     *
     * @param id The unique ID of the order.
     * @param user The session details of the user making the request.
     * @return The {@link OrderDto} for the specified ID.
     * @throws ResourceNotFoundException if no order is found with the given ID.
     * @throws AuthorizationException if the user is not authorized to view the order.
     */
    OrderDto getById(Long id, UserSessionDto user);

    /**
     * Creates a new order from a user's shopping cart.
     * This process moves all items from the cart to a new order and clears the cart.
     *
     * @param userId The unique ID of the user.
     * @return The newly created {@link OrderDto}.
     * @throws ResourceNotFoundException if the user's cart is not found.
     * @throws BusinessException if the user's cart is empty.
     */
    OrderDto create(Long userId);

    /**
     * Updates an existing order.
     * This operation is only permitted for orders with a {@code PENDING} status.
     *
     * @param orderDto The DTO containing the updated order data.
     * @return The updated {@link OrderDto}.
     * @throws IllegalArgumentException if the DTO's ID is null.
     * @throws ResourceNotFoundException if no order is found with the specified ID.
     * @throws BusinessException if the order's status is not PENDING.
     */
    OrderDto update(OrderDto orderDto);

    /**
     * Archives a cancelled order.
     * This is a soft delete operation that sets the order status to {@code ARCHIVED}.
     * Only admins can perform this action.
     *
     * @param orderId The unique ID of the order to archive.
     * @param user The session details of the user making the request.
     * @throws ResourceNotFoundException if the order is not found.
     * @throws AuthorizationException if the user lacks the required permissions.
     * @throws BusinessException if the order's status is not CANCELLED.
     */
    void archive(Long orderId, UserSessionDto user);

    /**
     * Approves a pending order, setting its status to {@code PAID}.
     * Only employees can perform this action.
     *
     * @param orderId The unique ID of the order to approve.
     * @param user The session details of the user making the request.
     * @throws ResourceNotFoundException if the order is not found.
     * @throws AuthorizationException if the user is a CUSTOMER.
     * @throws BusinessException if the order's status is not PENDING.
     */
    void approve(Long orderId, UserSessionDto user);

    /**
     * Cancels a pending order, setting its status to {@code CANCELLED}.
     * This action is permitted only for the customer who owns the order or a {@code SUPER_ADMIN}.
     *
     * @param orderId The unique ID of the order to cancel.
     * @param user The session details of the user making the request.
     * @throws ResourceNotFoundException if the order is not found.
     * @throws AuthorizationException if the user is not authorized to cancel the order.
     * @throws BusinessException if the order's status is not PENDING.
     */
    void cancel(Long orderId, UserSessionDto user);

    /**
     * Retrieves a paginated list of simplified orders for a specific user.
     *
     * @param pageable The pagination information.
     * @param userId The unique ID of the user.
     * @return A paginated list of orders as {@link OrderShowingDto} for the user.
     */
    Page<OrderShowingDto> findByUserId(Pageable pageable, Long userId);
}
