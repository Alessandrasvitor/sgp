package com.sansyro.sgpspring.repository;

import com.sansyro.sgpspring.entity.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends PagingAndSortingRepository<Course, Long> {

    Course findByName(String name);

    @Query(value = "SELECT c FROM Course c JOIN c.user u WHERE u.id = :userId ORDER BY c.name")
    List<Course> list(@Param("userId") Long userId);

    @Query(value = "SELECT c FROM Course c JOIN c.user u WHERE u.id = :userId",
            countQuery = "SELECT count(c) FROM Course c JOIN c.user u WHERE u.id = :userId")
    Page<Course> list(@Param("userId") Long userId, Pageable pageable);

}
