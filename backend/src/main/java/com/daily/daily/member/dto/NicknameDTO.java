package com.daily.daily.member.dto;

import com.daily.daily.member.validator.Nickname;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NicknameDTO {
    //한글로하면 10자 영어숫자로하면 15자가 되도록
    @NotBlank
    @Nickname
    private String nickname;
}
