package com.daily.daily.dailry.domain;

import com.daily.daily.common.domain.BaseTimeEntity;
import com.daily.daily.member.domain.Member;
import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

@Entity
@Getter
public class Dailry extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    protected Dailry() {
    }

    @Builder
    public Dailry(String title, Member member) {
        this.title = title;
        this.member = member;
    }

    public void updateTitle(String title) {
        this.title = title;
    }

    public boolean belongsTo(Long memberId) {
        return Objects.equals(this.member.getId(), memberId);
    }

}
