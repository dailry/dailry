package com.daily.daily.dailrypage.domain;

import com.daily.daily.dailry.domain.Dailry;
import com.daily.daily.dailrypage.dto.DailryPageUpdateDTO;
import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Type;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private String thumbnail;
    private int pageNumber;

//    @Type(JsonType.class)
//    @Column(name = "elements", columnDefinition = "JSON")
//    private Object elements;

    @Type(JsonType.class)
    @Column(name = "elements", columnDefinition = "JSON")
    private Map<String, Object> elements = new HashMap<>();

    @ManyToOne
    @JoinColumn(name = "dailry_id")
    private Dailry dailry;

    public static DailryPage createEmptyPage() {
        return new DailryPage();
    }

    public void updateBackground(String background) {
        this.background = background;
    }

//    public void updateElements(Object elements) {
//        this.elements = elements;
//    }

    public void updatePageNumber(int pageNumber) { this.pageNumber = pageNumber; }

    public void updateElements(List<DailryPageUpdateDTO.ElementDTO> elements) {
        for (DailryPageUpdateDTO.ElementDTO element : elements) {
            this.elements.put(element.getId(), element);
        }
    }
}
