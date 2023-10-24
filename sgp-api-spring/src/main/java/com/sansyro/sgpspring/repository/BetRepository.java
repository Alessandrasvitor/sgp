package com.sansyro.sgpspring.repository;

import com.sansyro.sgpspring.entity.Bet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BetRepository extends PagingAndSortingRepository<Bet, Long> {

    @Query(value = "SELECT b FROM Bet b JOIN b.user u WHERE u.id = :userId ORDER BY b.betDate")
    List<Bet> list(@Param("userId") Long userId);

    @Query(value = "SELECT b FROM Bet b JOIN b.user u WHERE u.id = :userId ",
            countQuery = "SELECT count(b) FROM Bet b JOIN b.user u WHERE u.id = :userId")
    Page<Bet> list(@Param("userId") Long userId, Pageable pageable);

}
