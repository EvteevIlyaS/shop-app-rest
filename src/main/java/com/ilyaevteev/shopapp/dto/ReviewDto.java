package com.ilyaevteev.shopapp.dto;

import lombok.Data;

@Data
public class ReviewDto {
    private Long id;

    private String review;

    private String product;
}
