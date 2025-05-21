package com.kozel.bookstore.service.impl;

import com.kozel.bookstore.data.entity.Order;
import com.kozel.bookstore.data.mapper.DataMapper;
import com.kozel.bookstore.data.repository.OrderRepository;
import com.kozel.bookstore.service.OrderService;
import com.kozel.bookstore.service.dto.ServiceOrderDto;
import com.kozel.bookstore.service.dto.ServiceOrderShowingDto;
import com.kozel.bookstore.service.exception.BookNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final DataMapper dataMapper;


    @Override
    public List<ServiceOrderDto> getAll() {
        log.debug("Called getAll() method");

        return orderRepository.findAll()
                .stream()
                .map(dataMapper::toServiceDto)
                .toList();
    }

    @Override
    public List<ServiceOrderShowingDto> getOrdersDtoShort() {
        log.debug("Called getOrdersDtoShort() method");

        return orderRepository.findAll()
                .stream()
                .map(dataMapper::toServiceShortedDto)
                .toList();
    }

    @Override
    public List<ServiceOrderShowingDto> findByUserId(Long userId) {
        return orderRepository.findByUserId(userId)
                .stream()
                .map(dataMapper::toServiceShortedDto)
                .toList();
    }

    @Override
    public ServiceOrderDto getById(Long id) {
        log.debug("Called getById() method");
            Order order = orderRepository.findById(id).orElseThrow(
                    () -> new BookNotFoundException("Cannot find order by id " + id)
            );

            return dataMapper.toServiceDto(order);
    }

    @Override
    public Long create(ServiceOrderDto serviceOrderDto) {
        log.debug("Called create() method");
        //TODO CREATE ORDER Service
        return 0L;
    }

    @Override
    public ServiceOrderDto update(ServiceOrderDto serviceOrderDto) {
        log.debug("Called update() method");
        //TODO UPDATE ORDER Service
        return null;
    }

    @Override
    public void disable(Long id) {
        log.debug("Called disable() method");
        //TODO DELETE ORDER Service
    }
}
