package io.siha.homework.service

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.siha.homework.entity.SearchKeyword
import io.siha.homework.enums.ReturnCode
import io.siha.homework.repository.SearchKeywordRepository

class SearchKeywordServiceTest : FunSpec({
    val searchKeywordRepository: SearchKeywordRepository = mockk()
    val searchKeywordService = SearchKeywordService(searchKeywordRepository)

    test("keywords should return Keyword list") {
        val keyword = SearchKeyword()
        keyword.keyword = "search - keyword - 1"
        keyword.searchCount = 10

        every {
            searchKeywordRepository.findFirst10OrderBySearchCountDesc()
        } returns listOf(keyword)

        val response = searchKeywordService.keywords()
        response.returnCode shouldBe ReturnCode.SUCCESS.name
        response.data.keywords.size shouldBe 1
        response.data.keywords[0].value shouldBe keyword.keyword
    }

})
