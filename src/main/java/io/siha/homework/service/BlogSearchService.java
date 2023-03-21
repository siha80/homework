package io.siha.homework.service;

import io.siha.homework.dto.SearchResponse;
import io.siha.homework.dto.ResponseT;
import io.siha.homework.enums.SortBy;

public interface BlogSearchService {
    ResponseT<SearchResponse> search(final String query, final SortBy sort, final int page, final int size);
}
