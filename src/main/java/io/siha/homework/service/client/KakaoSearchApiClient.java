package io.siha.homework.service.client;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(
        name = "kakao-search-api-client"
)
public interface KakaoSearchApiClient {
}
