package com.daily.daily.dailrypage.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.Value;

import java.util.List;
import java.util.Map;

@Getter
@Setter
public class DailryPageUpdateDTO {
    @NotBlank(message = "background는 비어있을 수 없습니다.")
    private String background;

    @Valid
    private List<ElementDTO> elements;

    @Getter
    @Setter
    @ToString
    public static class ElementDTO {
        @NotBlank(message = "id는 비어있을 수 없습니다.")
        private String id;

        @NotBlank(message = "type은 비어있을 수 없습니다.")
        private String type;

        @NotNull(message = "order는 비어있을 수 없습니다.")
        private Integer order;

        @NotNull(message = "position 값은 비어있을 수 없습니다.")
        @Valid
        private PositionDTO position;

        @NotNull(message = "size 값은 비어있을 수 없습니다.")
        @Valid
        private SizeDTO size;

        @NotBlank(message = "rotation 값은 비어있을 수 없습니다.")
        private String rotation;
        private Map<String, Object> properties;

        @Getter
        @Setter
        @AllArgsConstructor
        @NoArgsConstructor
        public static class PositionDTO {
            @NotNull(message = "position 값은 비어있을 수 없습니다.")
            private Integer x;
            @NotNull(message = "position 값은 비어있을 수 없습니다.")
            private Integer y;
        }

        @Getter
        @Setter
        @AllArgsConstructor
        @NoArgsConstructor
        public static class SizeDTO {
            @NotNull(message = "size 값은 비어있을 수 없습니다.")
            private Integer width;

            @NotNull(message = "size 값은 비어있을 수 없습니다.")
            private Integer height;
        }
    }
}

