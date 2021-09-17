package com.csmates.wp.controller;

import com.csmates.wp.credentials.RegistrationRequest;
import com.csmates.wp.service.RegistrationService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/v1/registration")
@AllArgsConstructor
public class RegistrationController {
    private final RegistrationService registrationService;

    @PostMapping
    public String register(@RequestBody RegistrationRequest request) {
        return registrationService.register(request);
    }

    @PostMapping(path = "confirm")
    public String confirm(@RequestParam(name = "token") String token) {
        return registrationService.confirmToken(token);
    }
}
