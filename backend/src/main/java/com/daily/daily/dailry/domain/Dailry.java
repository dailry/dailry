package com.daily.daily.dailry.domain;

import com.daily.daily.common.domain.BaseTimeEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class Dailry extends BaseTimeEntity {

    @Id
    @GeneratedValue
    private Long id;
    private String title;

    public static Dailry createEmptyDailry() {
        return new Dailry();
    }

    public void updateTitle(String title) {
        this.title = title;
    }

}
