package com.daily.daily.dailry.domain;

import com.daily.daily.common.domain.BaseTimeEntity;
import com.daily.daily.member.domain.Member;
import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Builder
public class Dailry extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    public void updateTitle(String title) {
        this.title = title;
    }

}
