package com.csmates.wp.repository;

import com.csmates.wp.domain.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<AppUser, Long> {
    int countByLogin(String login);

    AppUser findByUsername(String username);
}
