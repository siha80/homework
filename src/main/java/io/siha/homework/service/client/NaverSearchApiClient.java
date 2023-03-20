package io.siha.homework.service.client;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(
        name = "naver-search-api-client"
)
public interface NaverSearchApiClient {

}
