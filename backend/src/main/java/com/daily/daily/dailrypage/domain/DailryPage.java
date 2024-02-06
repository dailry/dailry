package com.daily.daily.dailrypage.domain;

import com.daily.daily.common.domain.BaseTimeEntity;
import com.daily.daily.dailry.domain.Dailry;
import com.daily.daily.dailrypage.dto.DailryPageUpdateDTO;
import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.annotations.Type;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Entity
@Getter
public class DailryPage extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String background;
    private String thumbnail;
    private Integer pageNumber;

    @Type(JsonType.class)
    @Column(name = "elements", columnDefinition = "JSON")
    @Builder.Default
    private Map<String, Object> elements = new HashMap<>();

    @ManyToOne
    @JoinColumn(name = "dailry_id")
    private Dailry dailry;

    @Builder
    public DailryPage(Integer pageNumber, Dailry dailry) {
        this.pageNumber = pageNumber;
        this.dailry = dailry;
    }
    protected DailryPage() {
    }

    public static DailryPage createEmptyPage() {
        return new DailryPage();
    }

    public void updateBackground(String background) {
        this.background = background;
    }

    public void updatePageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public void updateElements(List<DailryPageUpdateDTO.ElementDTO> elements) {
        for (DailryPageUpdateDTO.ElementDTO element : elements) {
            this.elements.put(element.getId(), element);
        }
    }

    public void updateThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public boolean belongsTo(Long memberId) {
        return Objects.equals(this.dailry.getMember().getId(), memberId);
    }
}
