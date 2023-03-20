package com.ilyaevteev.shopapp.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.ilyaevteev.shopapp.model.History;
import com.ilyaevteev.shopapp.model.tools.CustomJsonDateDeserializer;
import lombok.Data;

import java.util.Date;

@Data
public class HistoryDto {
    private Long id;

    private String name;

    @JsonDeserialize(using = CustomJsonDateDeserializer.class)
    private Date date;

    private Long user;

    public static HistoryDto fromHistory(History history) {
        HistoryDto historyDto = new HistoryDto();
        historyDto.setId(history.getId());
        historyDto.setName(history.getName());
        historyDto.setUser(historyDto.getUser());
        historyDto.setDate(history.getDate());

        return historyDto;
    }
}
