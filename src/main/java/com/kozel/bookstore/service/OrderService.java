package com.kozel.bookstore.service;


import com.kozel.bookstore.service.dto.ServiceOrderDto;
import com.kozel.bookstore.service.dto.ServiceOrderShowingDto;

import java.util.List;

public interface OrderService {
    List<ServiceOrderDto> getAll();
    List<ServiceOrderShowingDto> getOrdersDtoShort();
    ServiceOrderDto getById(Long id);
    Long create(ServiceOrderDto serviceOrderDto);
    ServiceOrderDto update(ServiceOrderDto serviceOrderDto);
    void disable(Long id);
    List<ServiceOrderShowingDto> findByUserId(Long userId);
}
