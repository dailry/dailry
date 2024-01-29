package com.daily.daily.dailry.fixture;

import com.daily.daily.dailry.domain.Dailry;
import com.daily.daily.dailry.dto.DailryDTO;
import com.daily.daily.dailry.dto.DailryFindDTO;
import com.daily.daily.dailry.dto.DailryUpdateDTO;

import java.util.ArrayList;
import java.util.List;

import static com.daily.daily.member.fixture.MemberFixture.일반회원1;

public class DailryFixture {

    public static final Long DAILRY_ID = 16L;

    public static final Long DAILRY_ID_2 = 17L;
    private static final String 다일리_제목 = "오늘의 다일리";
    private static final String 수정_다일리_제목= "내일의 다일리";

    public static DailryUpdateDTO 다일리_생성_DTO() {
        return new DailryUpdateDTO(다일리_제목);
    }

    public static DailryUpdateDTO 다일리_수정_DTO() {
        return new DailryUpdateDTO(수정_다일리_제목);
    }

    public static DailryDTO 다일리_응답_DTO() {
        DailryDTO result = DailryDTO.from(일반회원1이_작성한_다일리());
        return result;
    }

    public static DailryDTO 수정된_다일리_응답_DTO() {
        DailryDTO result = 다일리_응답_DTO();
        result.setTitle(수정_다일리_제목);
        return result;
    }

    public static List<DailryFindDTO> 다일리_리스트_응답_DTO() {
        List<DailryFindDTO> dailryList = new ArrayList<>();
        Dailry 일반회원1이_작성한_다일리 = 일반회원1이_작성한_다일리();
        Dailry 일반회원1이_작성한_2번째_다일리 = 일반회원1이_작성한_2번째_다일리();
        dailryList.add(DailryFindDTO.builder()
                .dailryId(일반회원1이_작성한_다일리.getId())
                .title(일반회원1이_작성한_다일리.getTitle())
                .build());
        dailryList.add(DailryFindDTO.builder()
                .dailryId(일반회원1이_작성한_2번째_다일리.getId())
                .title(일반회원1이_작성한_2번째_다일리.getTitle())
                .build());
        return dailryList;
    }

    public static DailryDTO 다일리_조회_DTO() {
        DailryDTO 다일리_응답_DTO = 다일리_응답_DTO();

        return DailryDTO.builder()
                .dailryId(다일리_응답_DTO.getDailryId())
                .title(다일리_응답_DTO.getTitle())
                .build();
    }

    public static Dailry 일반회원1이_작성한_다일리() {
        return Dailry.builder()
                .id(DAILRY_ID)
                .title(다일리_제목)
                .member(일반회원1())
                .build();
    }

    public static Dailry 일반회원1이_작성한_2번째_다일리() {
        return Dailry.builder()
                .id(DAILRY_ID_2)
                .title(다일리_제목)
                .member(일반회원1())
                .build();
    }

}
