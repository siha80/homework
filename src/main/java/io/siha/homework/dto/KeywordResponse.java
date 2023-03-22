package io.siha.homework.dto;

import java.util.List;

public record KeywordResponse(
        List<Keyword> keywords
) {
    public record Keyword(
            String value,
            Integer count
    ) {

    }
}
