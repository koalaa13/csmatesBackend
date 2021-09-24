package com.csmates.wp.repository;

import com.csmates.wp.domain.AppUser;
import com.csmates.wp.domain.ConfirmationToken;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import javax.persistence.PersistenceException;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class ConfirmationTokenRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ConfirmationTokenRepository repository;

    private final String token = UUID.randomUUID().toString();
    private final LocalDateTime creationTime = LocalDateTime.now();
    private final LocalDateTime expirationTime = LocalDateTime.now()
            .plusMinutes(15);
    private AppUser appUser;

    @BeforeEach
    public void beforeEach() {
        final String email = "test@test.test";
        final String username = "tester";
        final String password = "password";
        appUser = new AppUser(
                email,
                username,
                password
        );
        entityManager.persist(appUser);
        entityManager.flush();
    }

    @Test
    public void saveAndGetTokenTest() {
        ConfirmationToken confirmationToken = new ConfirmationToken(
                token,
                creationTime,
                expirationTime,
                appUser
        );
        entityManager.persist(confirmationToken);
        assertDoesNotThrow(() -> entityManager.flush());

        var foundToken = repository.findByToken(token);
        assertTrue(foundToken.isPresent());
        assertEquals(token, foundToken.get().getToken());
        assertEquals(creationTime, foundToken.get().getCreationTime());
        assertEquals(expirationTime, foundToken.get().getExpiresAt());
        assertEquals(appUser, foundToken.get().getAppUser());
        assertNull(foundToken.get().getConfirmedAt());
    }

    @Test
    public void saveTokenWithoutUserTest() {
        ConfirmationToken confirmationToken = new ConfirmationToken(
                token,
                creationTime,
                expirationTime,
                null
        );
        entityManager.persist(confirmationToken);
        assertThrows(PersistenceException.class, () -> entityManager.flush());
    }

    @Test
    public void saveTokenWithoutCreationTimeTest() {
        ConfirmationToken confirmationToken = new ConfirmationToken(
                token,
                null,
                expirationTime,
                appUser
        );
        entityManager.persist(confirmationToken);
        assertThrows(PersistenceException.class, () -> entityManager.flush());
    }

    @Test
    public void saveTokenWithoutExpirationTimeTest() {
        ConfirmationToken confirmationToken = new ConfirmationToken(
                token,
                creationTime,
                null,
                appUser
        );
        entityManager.persist(confirmationToken);
        assertThrows(PersistenceException.class, () -> entityManager.flush());
    }

    @Test
    public void saveTokenWithoutUUIDTokenTest() {
        ConfirmationToken confirmationToken = new ConfirmationToken(
                null,
                creationTime,
                expirationTime,
                appUser
        );
        entityManager.persist(confirmationToken);
        assertThrows(PersistenceException.class, () -> entityManager.flush());
    }

    @Test
    public void confirmTokenTest() {
        ConfirmationToken confirmationToken = new ConfirmationToken(
                token,
                creationTime,
                expirationTime,
                appUser
        );
        entityManager.persist(confirmationToken);
        assertDoesNotThrow(() -> entityManager.flush());

        var foundToken = repository.findByToken(token);
        assertTrue(foundToken.isPresent());
        assertEquals(token, foundToken.get().getToken());
        assertEquals(creationTime, foundToken.get().getCreationTime());
        assertEquals(expirationTime, foundToken.get().getExpiresAt());
        assertEquals(appUser, foundToken.get().getAppUser());
        assertNull(foundToken.get().getConfirmedAt());

        final LocalDateTime confirmedAt = LocalDateTime.now();
        assertEquals(1, repository.updateConfirmedAt(token, confirmedAt));
        foundToken = repository.findByToken(token);
        assertTrue(foundToken.isPresent());
        assertEquals(token, foundToken.get().getToken());
        assertEquals(creationTime, foundToken.get().getCreationTime());
        assertEquals(expirationTime, foundToken.get().getExpiresAt());
        assertEquals(appUser, foundToken.get().getAppUser());
        assertEquals(confirmedAt, foundToken.get().getConfirmedAt());
    }
}


