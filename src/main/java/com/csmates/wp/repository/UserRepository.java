package com.csmates.wp.repository;

import com.csmates.wp.domain.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<AppUser, Long> {
    Optional<AppUser> findByUsername(String username);

    Optional<AppUser> findByEmail(String email);

    boolean existsAppUserByUsername(String username);

    @Query("UPDATE AppUser a SET a.enabled = TRUE where a.email = ?1")
    int enableAppUser(String email);
}
