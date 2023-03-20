package com.ilyaevteev.shopapp.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.ilyaevteev.shopapp.model.tools.CustomJsonDateDeserializer;
import lombok.Data;

import java.util.Date;

@Data
public class DiscountDto {
    private Long id;

    private int amountPercentage;

    @JsonDeserialize(using = CustomJsonDateDeserializer.class)
    private Date durationUpTo;
}
