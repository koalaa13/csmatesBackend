package com.csmates.wp.controller;

import com.csmates.wp.domain.AppUser;
import com.csmates.wp.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@AllArgsConstructor
public class UserController {
    private final UserService userService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping
    public List<AppUser> getAllUsers() {
        return userService.getAllUsers();
    }
}
