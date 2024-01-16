package com.daily.daily.dailrypage.domain;

import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class DailryPage {
    @Id
    @GeneratedValue
    private Long id;
    private String background;

    @Type(JsonType.class)
    @Column(name = "elements", columnDefinition = "JSON")
    private Object elements;

    public static DailryPage createEmptyPage() {
        return new DailryPage();
    }

    public void updateBackground(String background) {
        this.background = background;
    }

    public void updateElements(Object elements) {
        this.elements = elements;
    }
}
