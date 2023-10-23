package com.sansyro.sgpspring.repository;

import com.sansyro.sgpspring.entity.User;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends PagingAndSortingRepository<User, Long> {
    User findByEmail(String email);

    User findByEmailAndPassword(String email, String password);
}
