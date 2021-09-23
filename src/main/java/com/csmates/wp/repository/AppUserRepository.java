package com.csmates.wp.repository;

import com.csmates.wp.domain.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser, Long> {
    Optional<AppUser> findByEmail(String email);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("UPDATE AppUser a SET a.enabled = TRUE where a.email = ?1")
    int enableAppUser(String email);

    List<AppUser> findByUsername(String username);
}
