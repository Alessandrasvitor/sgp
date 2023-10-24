package com.sansyro.sgpspring.repository;

import com.sansyro.sgpspring.entity.Instituition;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InstituitionRepository extends PagingAndSortingRepository<Instituition, Long> {
}
