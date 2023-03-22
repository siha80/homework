package io.siha.homework.repository;

import io.siha.homework.entity.SearchKeyword;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;

import javax.persistence.LockModeType;
import java.util.List;
import java.util.Optional;

@Repository
public interface SearchKeywordRepository extends JpaRepository<SearchKeyword, Long> {
    @Lock(LockModeType.PESSIMISTIC_READ)
    Optional<SearchKeyword> findFirstByKeyword(String keyword);
    List<SearchKeyword> findFirst10ByOrderBySearchCountDesc();
}
