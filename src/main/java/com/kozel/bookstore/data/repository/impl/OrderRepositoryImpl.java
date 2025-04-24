package com.kozel.bookstore.data.repository.impl;

import com.kozel.bookstore.data.dao.BookDao;
import com.kozel.bookstore.data.dao.OrderDao;
import com.kozel.bookstore.data.dao.OrderItemDao;
import com.kozel.bookstore.data.dao.UserDao;
import com.kozel.bookstore.data.dto.BookDto;
import com.kozel.bookstore.data.dto.OrderDto;
import com.kozel.bookstore.data.dto.OrderItemDto;
import com.kozel.bookstore.data.dto.UserDto;
import com.kozel.bookstore.data.entity.Book;
import com.kozel.bookstore.data.entity.Order;
import com.kozel.bookstore.data.entity.OrderItem;
import com.kozel.bookstore.data.entity.User;
import com.kozel.bookstore.data.mapper.DataMapper;
import com.kozel.bookstore.data.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderRepositoryImpl implements OrderRepository {

    private final OrderDao orderDao;
    private final OrderItemDao orderItemDao;
    private final BookDao bookDao;
    private final UserDao userDao;
    private final DataMapper dataMapper;

    @Override
    public List<Order> findByUserId(Long userId) {
        return convertDtosToEntities(orderDao.findByUserId(userId));
    }

    @Override
    public List<Order> findByStatus(Order.Status status) {
        List<OrderDto> orderDtos = orderDao.findByStatus(OrderDto.Status.valueOf(status.toString()));
        return convertDtosToEntities(orderDtos);
    }

    @Override
    public long countAll() {
        return orderDao.countAll();
    }

    @Override
    public Long save(Order object) {
        //TODO Order Save
        return 0L;
    }

    @Override
    public Order findById(Long id) {
        return convertDtoToEntity(orderDao.findById(id));
    }

    @Override
    public List<Order> findAll() {
        return convertDtosToEntities(orderDao.findAll());
    }

    @Override
    public Order update(Order order) {
        //TODO Order Update
        return null;
    }

    @Override
    public void delete(Order object) {
        //TODO Order Delete
    }

    private List<Order> convertDtosToEntities(List<OrderDto> orderDtos) {
        List<Order> orders = new ArrayList<>();
        for (OrderDto orderDto : orderDtos) {
            orders.add(convertDtoToEntity(orderDto));
        }
        return orders;
    }

    private Order convertDtoToEntity(OrderDto orderDto) {
        Order order = new Order();
        order.setId(orderDto.getId());
        order.setDate(orderDto.getDate());
        order.setStatus(Order.Status.valueOf(orderDto.getStatus().toString()));
        order.setTotalPrice(orderDto.getPrice());

        UserDto userDto = userDao.findById(orderDto.getUserId());
        User user = dataMapper.toEntity(userDto);
        order.setUser(user);

        List<OrderItemDto> orderItemDtos = orderItemDao.findByOrderId(orderDto.getId());
        List<OrderItem> orderItems = convertItemDtosToItems(orderItemDtos);
        order.setItems(orderItems);

        return order;
    }

    private List<OrderItem> convertItemDtosToItems(List<OrderItemDto> orderItemDtos) {
        List<OrderItem> orderItems = new ArrayList<>();
        for (OrderItemDto orderItemDto : orderItemDtos) {
            OrderItem orderItem = new OrderItem();
            orderItem.setId(orderItemDto.getId());
            orderItem.setQuantity(orderItemDto.getQuantity());
            orderItem.setPrice(orderItemDto.getPrice());

            BookDto bookDto = bookDao.findById(orderItemDto.getBookId());
            Book book = dataMapper.toEntity(bookDto);
            orderItem.setBook(book);
            orderItems.add(orderItem);
        }
        return orderItems;
    }
}
