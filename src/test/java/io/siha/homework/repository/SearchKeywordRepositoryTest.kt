package io.siha.homework.repository

import io.kotest.core.spec.style.FunSpec
import io.kotest.extensions.spring.SpringExtension
import io.kotest.matchers.shouldBe
import io.siha.homework.config.DatabaseConfig
import io.siha.homework.entity.SearchKeyword
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration

@ContextConfiguration(classes = [DatabaseConfig::class])
class SearchKeywordRepositoryTest : FunSpec() {
    override fun extensions() = listOf(SpringExtension)

    @Autowired
    private lateinit var searchKeywordRepository: SearchKeywordRepository

    init{
        beforeSpec {
            val keyword = SearchKeyword()
            keyword.keyword = "kakaobank"
            keyword.searchCount = 1
            searchKeywordRepository.save(keyword)
        }

        test("findFirst10OrderBySearchCount should return top 10 counts of keyword") {
            val result = searchKeywordRepository.findFirst10ByOrderBySearchCountDesc()
            result.size shouldBe 1
            result.get(0).keyword shouldBe "kakaobank"
        }
    }

}
