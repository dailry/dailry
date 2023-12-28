package com.daily.daily.member.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PasswordRecoverDTO {

    @NotBlank
    private String username;

    @NotBlank
    @Email
    private String email;
}
