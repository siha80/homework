package io.siha.homework.service;

import io.siha.homework.dto.SearchResponse;
import io.siha.homework.dto.ResponseT;
import io.siha.homework.enums.SortBy;
import org.springframework.stereotype.Service;

@Service
public class NaverBlogSearchService implements BlogSearchService {
    @Override
    public ResponseT<SearchResponse> search(String query, SortBy sort, int page, int size) {
        return null;
    }
}
