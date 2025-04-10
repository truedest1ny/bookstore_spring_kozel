package com.kozel.bookstore.service.dto;

import lombok.Data;


@Data
public class BookDtoShowing {

    private Long id;
    private String name;
    private String author;
    private Integer publishedYear;

}
