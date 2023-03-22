package io.siha.homework.controller

import io.kotest.core.spec.style.FunSpec
import io.mockk.every
import io.mockk.mockk
import io.siha.homework.dto.KeywordResponse
import io.siha.homework.dto.ResponseT
import io.siha.homework.dto.SearchResponse
import io.siha.homework.service.KakaoBlogSearchService
import io.siha.homework.service.SearchKeywordService
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.restdocs.ManualRestDocumentation
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration
import org.springframework.restdocs.operation.preprocess.Preprocessors
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.test.web.servlet.setup.StandaloneMockMvcBuilder

private const val DOCUMENT_PATH = "search-api/"

@WebMvcTest(SearchController::class)
@AutoConfigureMockMvc
class SearchControllerAsciidocTest : FunSpec() {

    init {
        val kakaoBlogSearchService: KakaoBlogSearchService = mockk()
        val searchKeywordService: SearchKeywordService = mockk()

        lateinit var mockMvc: MockMvc

        var restDocumentation = ManualRestDocumentation("build/generated-snippets")

        beforeEach {
            restDocumentation.beforeTest(javaClass, "")

            mockMvc = MockMvcBuilders.standaloneSetup(SearchController(kakaoBlogSearchService, searchKeywordService))
                .apply<StandaloneMockMvcBuilder>(
                    documentationConfiguration(restDocumentation)
                        .uris()
                        .withScheme("http")
                        .withHost("merchant.alp.vani.la")
                        .withPort(8080)
                )
                .build()
        }

        afterTest { restDocumentation.afterTest() }

        test("search by kakao") {
            val query = "test"

            val expected = SearchResponse()
            expected.meta = SearchResponse.Meta()
            expected.meta.totalCount = 1
            expected.meta.pageableCount = 10
            expected.meta.isEnd = true

            val document = SearchResponse.Document()
            document.title = "expected title"
            expected.documents = listOf(document)

            every {
                kakaoBlogSearchService.search(any(), any(), any(), any())
            } returns ResponseT.of(expected)

            mockMvc.perform(
                get("/api/v1/search")
                    .queryParam("query", query)
                    .contentType(MediaType.APPLICATION_JSON)
            )
                .andExpect(status().isOk)
                .andDo(
                    document(
                        "${DOCUMENT_PATH}/search-blog",
                        Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
                        Preprocessors.preprocessResponse(Preprocessors.prettyPrint())
                    )
                )
        }

        test("keywords api should return top 10 counts of keywords") {
            val expected = KeywordResponse(
                listOf(
                    KeywordResponse.Keyword("keyword-1", 9),
                    KeywordResponse.Keyword("keyword-2", 8),
                    KeywordResponse.Keyword("keyword-3", 8),
                    KeywordResponse.Keyword("keyword-4", 7),
                    KeywordResponse.Keyword("keyword-5", 6),
                    KeywordResponse.Keyword("keyword-6", 3),
                    KeywordResponse.Keyword("keyword-7", 3),
                    KeywordResponse.Keyword("keyword-8", 3),
                )
            )

            every {
                searchKeywordService.keywords()
            } returns ResponseT.of(expected)

            mockMvc.perform(
                get("/api/v1/keywords")
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
            )
                .andExpect(status().isOk)
                .andDo(
                    document(
                        "${DOCUMENT_PATH}/search-keyword",
                        Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
                        Preprocessors.preprocessResponse(Preprocessors.prettyPrint())
                    )
                )
        }
    }

}
