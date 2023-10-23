package com.sansyro.sgpspring.repository;

import com.sansyro.sgpspring.entity.Bet;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BetRepository extends PagingAndSortingRepository<Bet, Long> {
}
