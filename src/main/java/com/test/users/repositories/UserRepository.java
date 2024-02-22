package com.test.users.repositories;

import com.test.users.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    Boolean existsByEmail(String email);

    Optional<User> findByEmail(String email);

    @Query(value = "SELECT u.* FROM users u " +
            "WHERE (UPPER(name) like %?1% OR UPPER(email) like %?1%) ",
            countQuery = "SELECT COUNT(u.*) FROM users u " +
                    "WHERE (UPPER(name) like %?1% OR UPPER(email) like %?1%) ",
            nativeQuery = true)
    Page<User> findFiltering(String filter, Pageable paging);
}
