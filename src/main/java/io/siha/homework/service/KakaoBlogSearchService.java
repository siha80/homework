package io.siha.homework.service;

import io.siha.homework.dto.SearchResponse;
import io.siha.homework.dto.ResponseT;
import io.siha.homework.enums.SortBy;
import io.siha.homework.service.client.KakaoSearchApiClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class KakaoBlogSearchService implements BlogSearchService {
    private final KakaoSearchApiClient client;
    private final SearchKeywordService searchKeywordService;

    @Value("${search-api.kakao.rest-api-key:}")
    private String restApiKey;
    private String kakaoApiAuthorizationHeader = null;

    public KakaoBlogSearchService(KakaoSearchApiClient client, SearchKeywordService searchKeywordService) {
        this.client = client;
        this.searchKeywordService = searchKeywordService;
    }

    @PostConstruct
    public void init() {
        kakaoApiAuthorizationHeader = String.format("KakaoAK %s", restApiKey);
    }

    @Override
    public ResponseT<SearchResponse> search(String query, SortBy sort, int page, int size) {
        searchKeywordService.update(query);
        return ResponseT.of(client.searchBlog(kakaoApiAuthorizationHeader, query, sort.getValue(), page, size));
    }
}
