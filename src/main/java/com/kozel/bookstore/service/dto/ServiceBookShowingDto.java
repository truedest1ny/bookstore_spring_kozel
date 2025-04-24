package com.kozel.bookstore.service.dto;

import lombok.Data;


@Data
public class ServiceBookShowingDto {

    private Long id;
    private String name;
    private String author;
    private Integer publishedYear;

}
