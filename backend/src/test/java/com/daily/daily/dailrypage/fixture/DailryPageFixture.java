package com.daily.daily.dailrypage.fixture;

import com.daily.daily.dailrypage.domain.DailryPage;
import com.daily.daily.dailrypage.dto.DailryPageCreateResponseDTO;
import com.daily.daily.dailrypage.dto.DailryPageDTO;
import com.daily.daily.dailrypage.dto.DailryPagePreviewDTO;
import com.daily.daily.dailrypage.dto.DailryPageThumbnailDTO;
import com.daily.daily.dailrypage.dto.DailryPageUpdateDTO;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.util.ReflectionTestUtils;
import org.testcontainers.shaded.com.fasterxml.jackson.core.JsonProcessingException;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.daily.daily.dailry.fixture.DailryFixture.DAILRY_ID;
import static com.daily.daily.dailry.fixture.DailryFixture.일반회원1이_작성한_2번째_다일리;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.IMAGE_PNG_VALUE;

public class DailryPageFixture {

    public static final Long 다일리페이지_ID = 5L;

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static DailryPage 비어있는_다일리_페이지() {
        DailryPage 비어있는_다일리_페이지 = DailryPage.builder()
                .pageNumber(1)
                .dailry(일반회원1이_작성한_2번째_다일리())
                .build();

        ReflectionTestUtils.setField(비어있는_다일리_페이지, "id", 다일리페이지_ID);
        return 비어있는_다일리_페이지;
    }

    public static DailryPage 다일리_페이지() {
        DailryPage 다일리_페이지 = DailryPage.builder()
                .pageNumber(1)
                .dailry(일반회원1이_작성한_2번째_다일리())
                .build();

        다일리_페이지.addOrUpdateElements(다일리_페이지_수정요청_DTO().getElements());
        ReflectionTestUtils.setField(다일리_페이지, "id", 다일리페이지_ID);

        return 다일리_페이지;
    }
    public static DailryPageCreateResponseDTO 비어있는_다일리_페이지_DTO() {
        return DailryPageCreateResponseDTO.builder()
                .dailryId(DAILRY_ID)
                .pageId(다일리페이지_ID)
                .background("grid")
                .thumbnail("https://data.da-ily.site/util/thumbnail/empty.png")
                .pageNumber(1L)
                .build();
    }

    public static DailryPageUpdateDTO 다일리_페이지_수정요청_DTO() {
        DailryPageUpdateDTO 다일리_페이지_수정요청_DTO = new DailryPageUpdateDTO();

        다일리_페이지_수정요청_DTO.setBackground("무지");
        다일리_페이지_수정요청_DTO.setDeletedElementIds(List.of("aba113a"));
        다일리_페이지_수정요청_DTO.setElements(List.of(첫번째_elementsDTO(), 두번째_elementsDTO(), 세번째_elementsDTO()));

        return 다일리_페이지_수정요청_DTO;
    }

    public static MockMultipartFile 다일리_페이지_섬네일_파일() {
        return new MockMultipartFile(
                "thumbnail",
                "awefkaweop.png",
                IMAGE_PNG_VALUE,
                "dailryPage".getBytes()
        );
    }

    public static MockMultipartFile 다일리_페이지_요청_DTO_JSON_파일() throws JsonProcessingException {
        return new MockMultipartFile(
                "dailryPageRequest",
                "dailryPageRequest",
                APPLICATION_JSON_VALUE,
                objectMapper.writeValueAsBytes(다일리_페이지_수정요청_DTO())
        );
    }

    public static DailryPageDTO 다일리_페이지_응답_DTO() {
        return DailryPageDTO.builder()
                .pageId(다일리페이지_ID)
                .background("무지")
                .thumbnail("https://data.da-ily.site/thumbnail/5/1/awefkaweop")
                .pageNumber(1)
                .elements(List.of(첫번째_elementsDTO(), 두번째_elementsDTO(), 세번째_elementsDTO()))
                .build();
    }

    public static DailryPagePreviewDTO 다일리_페이지_미리보기_DTO() {
        DailryPageThumbnailDTO 썸네일1 = new DailryPageThumbnailDTO(1L,1, "https://data.da-ily.site/thumbnail/5/1/awerqlwp33124");
        DailryPageThumbnailDTO 썸네일2 = new DailryPageThumbnailDTO(2L,2, "https://data.da-ily.site/thumbnail/5/2/73123wqrw");
        DailryPageThumbnailDTO 썸네일3 = new DailryPageThumbnailDTO(3L,3, "https://data.da-ily.site/thumbnail/5/3/u12rgf31412");

        DailryPagePreviewDTO 다일리_페이지_미리보기_DTO = new DailryPagePreviewDTO();
        다일리_페이지_미리보기_DTO.setDailryId(2L);
        다일리_페이지_미리보기_DTO.setPages(List.of(썸네일1, 썸네일2, 썸네일3));

        return 다일리_페이지_미리보기_DTO;
    }

    public static DailryPageUpdateDTO.ElementDTO 첫번째_elementsDTO() {
        DailryPageUpdateDTO.ElementDTO 첫번째_elementsDTO = new DailryPageUpdateDTO.ElementDTO();

        첫번째_elementsDTO.setId("asdf");
        첫번째_elementsDTO.setType("textBox");
        첫번째_elementsDTO.setOrder(1);
        첫번째_elementsDTO.setPosition(new DailryPageUpdateDTO.ElementDTO.PositionDTO(100, 50));
        첫번째_elementsDTO.setSize(new DailryPageUpdateDTO.ElementDTO.SizeDTO(200, 100));
        첫번째_elementsDTO.setRotation("90deg");

        Map<String, Object> typeContent = new HashMap<>();
        typeContent.put("font", "굴림");
        typeContent.put("text", "아 어지럽다.");
        typeContent.put("color", "#333333");
        typeContent.put("fontSize", 12);
        typeContent.put("fontWeight", "bold");
        typeContent.put("backgroundColor", "#ffcc00");

        첫번째_elementsDTO.setTypeContent(typeContent);

        return 첫번째_elementsDTO;
    }

    public static DailryPageUpdateDTO.ElementDTO 두번째_elementsDTO() {
        DailryPageUpdateDTO.ElementDTO 두번째_elementsDTO = new DailryPageUpdateDTO.ElementDTO();
        두번째_elementsDTO.setId("123avxsdf");
        두번째_elementsDTO.setType("drawing");
        두번째_elementsDTO.setOrder(3);
        두번째_elementsDTO.setPosition(new DailryPageUpdateDTO.ElementDTO.PositionDTO(100, 50));
        두번째_elementsDTO.setSize(new DailryPageUpdateDTO.ElementDTO.SizeDTO(250, 150));
        두번째_elementsDTO.setRotation("60deg");

        Map<String, Object> typeContent = new HashMap<>();
        typeContent.put("base64", "YXNjc2FzYXZmbnJ0bnJ0bnN0");

        두번째_elementsDTO.setTypeContent(typeContent);

        return 두번째_elementsDTO;
    }

    public static DailryPageUpdateDTO.ElementDTO 세번째_elementsDTO() {
        DailryPageUpdateDTO.ElementDTO 세번째_elementsDTO = new DailryPageUpdateDTO.ElementDTO();
        세번째_elementsDTO.setId("123a21233df");
        세번째_elementsDTO.setType("sticker");
        세번째_elementsDTO.setOrder(5);
        세번째_elementsDTO.setPosition(new DailryPageUpdateDTO.ElementDTO.PositionDTO(110, 50));
        세번째_elementsDTO.setSize(new DailryPageUpdateDTO.ElementDTO.SizeDTO(300, 150));
        세번째_elementsDTO.setRotation("45deg");

        Map<String, Object> typeContent = new HashMap<>();
        typeContent.put("imageUrl", "https://trboard.game.onstove.com/Data/TR/20180111/13/636512725318200105.jpg");

        세번째_elementsDTO.setTypeContent(typeContent);

        return 세번째_elementsDTO;
    }
}
