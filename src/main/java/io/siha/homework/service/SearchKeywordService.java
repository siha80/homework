package io.siha.homework.service;

import io.siha.homework.dto.KeywordResponse;
import io.siha.homework.dto.ResponseT;
import io.siha.homework.entity.SearchKeyword;
import io.siha.homework.repository.SearchKeywordRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchKeywordService {
    private final SearchKeywordRepository searchKeywordRepository;

    public SearchKeywordService(SearchKeywordRepository searchKeywordRepository) {
        this.searchKeywordRepository = searchKeywordRepository;
    }

    public ResponseT<KeywordResponse> keywords() {
        final List<KeywordResponse.Keyword> keywords = searchKeywordRepository.findFirst10ByOrderBySearchCountDesc().stream()
                .map(word -> new KeywordResponse.Keyword(word.getKeyword(), word.getSearchCount()))
                .toList();

        return ResponseT.of(new KeywordResponse(keywords));
    }

    public SearchKeyword update(final String query) {
        SearchKeyword searchKeyword = searchKeywordRepository.findFirstByKeyword(query)
                .orElseGet(() -> newSearchKeyword(query));

        searchKeyword.addCount();
        return searchKeywordRepository.save(searchKeyword);
    }

    private SearchKeyword newSearchKeyword(final String keyword) {
        final SearchKeyword searchKeyword = new SearchKeyword();
        searchKeyword.setKeyword(keyword);
        searchKeyword.setSearchCount(0);
        return searchKeyword;
    }
}
