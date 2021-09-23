package com.csmates.wp.service;

import com.csmates.wp.config.ConfirmationTokenConfig;
import com.csmates.wp.domain.AppUser;
import com.csmates.wp.domain.ConfirmationToken;
import com.csmates.wp.exceptions.EmailException;
import com.csmates.wp.repository.AppUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UserService {
    private final AppUserRepository appUserRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ConfirmationTokenService confirmationTokenService;
    private final ConfirmationTokenConfig confirmationTokenConfig;

    public String registerUser(AppUser user) {
        Optional<AppUser> existingUser = appUserRepository.findByEmail(user.getEmail());
        if (existingUser.isPresent()) {
            throw new EmailException("Email already taken");
        }
        String encodedPassword = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        appUserRepository.save(user);


        String token = UUID.randomUUID().toString();
        ConfirmationToken confirmationToken = new ConfirmationToken(
                token,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(confirmationTokenConfig.getTokenExpirationTime()),
                user
        );

        confirmationTokenService.saveConfirmationToken(confirmationToken);

        return token;
    }

    public void enableUser(String email) {
        appUserRepository.enableAppUser(email);
    }

    public List<AppUser> getAllUsers() {
        return appUserRepository.findAll();
    }
}
