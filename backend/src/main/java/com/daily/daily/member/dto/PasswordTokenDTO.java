package com.daily.daily.member.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PasswordTokenDTO {

    @NotBlank
    private String passwordResetToken;

    @NotBlank
    private String password;

}
