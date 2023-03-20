package com.ilyaevteev.shopapp.dto;

import lombok.Data;

@Data
public class RatingDto {
    private Long id;

    private int rating;

    private String product;
}
