package com.daily.daily.auth.dto;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
public class LoginDTO {

    @Length(min = 4, max = 15)
    private String username;

    @Length(min = 8, max = 20)
    private String password;
}
