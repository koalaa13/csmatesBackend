package com.csmates.wp.credentials;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class RegistrationRequest {
    @NotBlank(message = "Username for registration is mandatory and can not be null or empty")
    private final String username;
    @NotBlank(message = "Email for registration is mandatory and can not be null or empty")
    private final String email;
    @NotBlank(message = "Password for registration is mandatory and can not be null or empty")
    private final String password;
}
