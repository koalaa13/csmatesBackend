package com.csmates.wp.repository;

import com.csmates.wp.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    int countByLogin(String login);

    User findByUsername(String username);
}
