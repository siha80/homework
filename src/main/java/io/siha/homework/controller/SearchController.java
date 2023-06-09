package io.siha.homework.controller;

import io.siha.homework.dto.KeywordResponse;
import io.siha.homework.dto.SearchResponse;
import io.siha.homework.dto.ResponseT;
import io.siha.homework.enums.SortBy;
import io.siha.homework.service.BlogSearchService;
import io.siha.homework.service.SearchKeywordService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SearchController {
    private final BlogSearchService searchService;
    private final SearchKeywordService searchKeywordService;

    public SearchController(@Qualifier("kakaoBlogSearchService") BlogSearchService searchService, SearchKeywordService searchKeywordService) {
        this.searchService = searchService;
        this.searchKeywordService = searchKeywordService;
    }

    @GetMapping(value = "/api/v1/search")
    public ResponseEntity<ResponseT<SearchResponse>> search(
            @RequestParam final String query,
            @RequestParam(required = false, defaultValue = "ACCURACY") final SortBy sort,
            @RequestParam(required = false, defaultValue = "1") final int page,
            @RequestParam(required = false, defaultValue = "10") final int size
    ) {
        final ResponseT<SearchResponse> response = searchService.search(query, sort, page, size);
        return new ResponseEntity<>(response, response.success() ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }

    @GetMapping(value = "/api/v1/keywords")
    public ResponseEntity<ResponseT<KeywordResponse>> keywords() {
        final ResponseT<KeywordResponse> response = searchKeywordService.keywords();
        return new ResponseEntity<>(response, response.success() ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }
}
