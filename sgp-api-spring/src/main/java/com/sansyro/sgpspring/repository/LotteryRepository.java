package com.sansyro.sgpspring.repository;

import com.sansyro.sgpspring.entity.Lottery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LotteryRepository extends PagingAndSortingRepository<Lottery, Long> {

    @Query(value = "SELECT l FROM Lottery l JOIN l.user u WHERE u.id = :userId ORDER BY l.lotteryDate")
    List<Lottery> list(@Param("userId") Long userId);

    @Query(value = "SELECT l FROM Lottery l JOIN l.user u WHERE u.id = :userId ",
            countQuery = "SELECT count(l) FROM Lottery l JOIN l.user u WHERE u.id = :userId")
    Page<Lottery> list(@Param("userId") Long userId, Pageable pageable);

}
