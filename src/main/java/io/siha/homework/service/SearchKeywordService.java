package io.siha.homework.service;

import io.siha.homework.dto.KeywordResponse;
import io.siha.homework.dto.ResponseT;
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
        final List<KeywordResponse.Keyword> keywords = searchKeywordRepository.findFirst10OrderBySearchCountDesc().stream()
                .map(word -> new KeywordResponse.Keyword(word.getKeyword(), word.getSearchCount()))
                .toList();

        return ResponseT.of(new KeywordResponse(keywords));
    }
}
