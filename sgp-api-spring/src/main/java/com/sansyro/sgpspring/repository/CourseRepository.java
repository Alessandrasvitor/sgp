package com.sansyro.sgpspring.repository;

import com.sansyro.sgpspring.entity.Course;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends PagingAndSortingRepository<Course, Long> {
    Course findByName(String name);

}
