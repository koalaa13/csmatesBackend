package com.csmates.wp.repository;

import com.csmates.wp.domain.AppUser;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import javax.persistence.PersistenceException;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class AppUserRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private AppUserRepository repository;

    @Test
    public void saveAndGetUserTest() {
        final String email = "test@test.test";
        final String username = "tester";
        final String password = "password";
        AppUser appUser = new AppUser(
                email,
                username,
                password
        );

        entityManager.persist(appUser);
        assertDoesNotThrow(() -> entityManager.flush());

        var foundUser = repository.findByEmail(email);
        assertTrue(foundUser.isPresent());
        assertEquals(email, foundUser.get().getEmail());
        assertEquals(username, foundUser.get().getUsername());
        assertEquals(password, foundUser.get().getPassword());
        assertFalse(foundUser.get().getEnabled()); // by default user is not enabled because of email confirmation
    }

    // email should be unique
    @Test
    public void saveTwoUsersWithSameEmailTest() {
        final String email = "test@test.com";
        AppUser appUser1 = new AppUser(
                email,
                "username1",
                "password1"
        );
        AppUser appUser2 = new AppUser(
                email,
                "username2",
                "password2"
        );
        entityManager.persist(appUser1);
        entityManager.persist(appUser2);
        assertThrows(PersistenceException.class, () -> entityManager.flush());
    }

    // but can have 2 users with the same usernames
    @Test
    public void saveTwoUserWithSameUsernamesTest() {
        final String username = "tester";
        AppUser appUser1 = new AppUser(
                "test1@test.test",
                username,
                "password1"
        );
        AppUser appUser2 = new AppUser(
                "test2@test.test",
                username,
                "password2"
        );
        entityManager.persist(appUser1);
        entityManager.persist(appUser2);
        assertDoesNotThrow(() -> entityManager.flush());

        var foundUsers = repository.findByUsername(username);
        assertEquals(2, foundUsers.size());
        assertEquals(appUser1, foundUsers.get(0));
        assertEquals(appUser2, foundUsers.get(1));
    }

    @Test
    public void enableUserTest() {
        final String email = "test@test.test";
        final String username = "tester";
        final String password = "password";
        AppUser appUser = new AppUser(
                email,
                username,
                password
        );

        entityManager.persist(appUser);
        assertDoesNotThrow(() -> entityManager.flush());

        var foundUser = repository.findByEmail(email);
        assertTrue(foundUser.isPresent());
        assertEquals(email, foundUser.get().getEmail());
        assertEquals(username, foundUser.get().getUsername());
        assertEquals(password, foundUser.get().getPassword());
        assertFalse(foundUser.get().getEnabled()); // by default user is not enabled because of email confirmation

        assertEquals(1, repository.enableAppUser(email));
        foundUser = repository.findByEmail(email);
        assertTrue(foundUser.isPresent());
        assertEquals(email, foundUser.get().getEmail());
        assertEquals(username, foundUser.get().getUsername());
        assertEquals(password, foundUser.get().getPassword());
        assertTrue(foundUser.get().getEnabled()); // now we enabled user
    }
}
