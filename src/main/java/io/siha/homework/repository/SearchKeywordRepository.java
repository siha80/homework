package io.siha.homework.repository;

import io.siha.homework.entity.SearchKeyword;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SearchKeywordRepository extends JpaRepository<SearchKeyword, Long> {
    List<SearchKeyword> findFirst10OrderBySearchCountDesc();
}
