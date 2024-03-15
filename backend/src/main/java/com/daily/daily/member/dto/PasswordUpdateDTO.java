package com.daily.daily.member.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PasswordUpdateDTO {
    @NotBlank
    private String presentPassword;

    @Length(min = 8, max = 20)
    @NotBlank
    private String updatePassword;
}
