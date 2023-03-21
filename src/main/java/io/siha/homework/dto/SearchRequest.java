package io.siha.homework.dto;

import io.siha.homework.enums.SortBy;
import lombok.Data;

@Data
public class SearchRequest {
    private String query;
    private SortBy sort = SortBy.ACCURACY;
    private Integer page = 1;
    private Integer size = 10;
}
