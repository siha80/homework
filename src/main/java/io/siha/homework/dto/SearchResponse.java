package io.siha.homework.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import java.util.List;

@Data
public class SearchResponse {
    private Meta meta;
    private List<Document> documents;

    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
    @Data
    public static class Meta {
        private Integer totalCount;
        private Integer pageableCount;
        private Boolean isEnd;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    @Data
    public static class Document {
        private String title;
        private String contents;
        private String url;
        private String blogname;
        private String thumbnail;
        private String datetime;
    }
}
