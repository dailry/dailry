package com.daily.daily.dailry.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Builder
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DailryUpdateDTO {
    @NotBlank(message = "다일리 제목은 비어있을 수 없습니다.")
    private String title;
}
