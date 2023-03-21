package io.siha.homework.service.client;

import io.siha.homework.dto.SearchResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(
        name = "kakao-search-api-client",
        url = "https://dapi.kakao.com"
)
public interface KakaoSearchApiClient {
    @GetMapping(value = "/v2/search/blog", produces = MediaType.APPLICATION_JSON_VALUE)
    SearchResponse searchBlog(@RequestHeader("Authorization") final String authorizationHeader,
                              @RequestParam final String query,
                              @RequestParam(required = false, defaultValue = "accuracy") final String sort,
                              @RequestParam(required = false, defaultValue = "1") final int page,
                              @RequestParam(required = false, defaultValue = "10") final int size
    );
}
