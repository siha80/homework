package io.siha.homework.service

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.siha.homework.dto.SearchResponse
import io.siha.homework.enums.SortBy
import io.siha.homework.service.client.KakaoSearchApiClient

class KakaoBlogSearchServiceTest : FunSpec({
    val kakaoSearchApiClient: KakaoSearchApiClient = mockk()
    val kakaoBlogSearchService = KakaoBlogSearchService(kakaoSearchApiClient)

    test("search should return SUCCESS and result of query from kakao api") {
        val expected = SearchResponse()
        expected.meta = SearchResponse.Meta()
        expected.documents = listOf()

        every {
            kakaoSearchApiClient.searchBlog(any(), any(), any(), any(), any())
        } returns expected

        val response = kakaoBlogSearchService.search("test", SortBy.ACCURACY, 1, 10)
    }

})