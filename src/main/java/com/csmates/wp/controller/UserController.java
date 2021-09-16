package com.csmates.wp.controller;

import com.csmates.wp.domain.AppUser;
import com.csmates.wp.repository.UserRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserController(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @GetMapping("")
    public List<AppUser> getAllUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/{userId}")
    public AppUser getUser(@PathVariable(name = "userId") Long id) {
        return userRepository.findById(id).orElseThrow(() ->
                new UsernameNotFoundException(
                        String.format("Username with id: %s not found", id)
                )
        );
    }

    @PostMapping("/register")
    public void signUp(@RequestBody AppUser user) {
        // have no field login on front
        // made login = username by default
        user.setLogin(user.getUsername());
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }
}
