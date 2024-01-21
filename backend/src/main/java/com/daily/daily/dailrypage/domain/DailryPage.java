package com.daily.daily.dailrypage.domain;

import com.daily.daily.dailry.domain.Dailry;
import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Type;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Builder
public class DailryPage {
    @Id
    @GeneratedValue
    private Long id;
    private String background;

    @Type(JsonType.class)
    @Column(name = "elements", columnDefinition = "JSON")
    private Object elements;

    @ManyToOne
    @JoinColumn(name = "dailry_id")
    private Dailry dailry;

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
