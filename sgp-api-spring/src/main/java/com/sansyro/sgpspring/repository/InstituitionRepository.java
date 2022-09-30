package com.sansyro.sgpspring.repository;

import com.sansyro.sgpspring.entity.Instituition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InstituitionRepository extends JpaRepository<Instituition, Long> {
}
