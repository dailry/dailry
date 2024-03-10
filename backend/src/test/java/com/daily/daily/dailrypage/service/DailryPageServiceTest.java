package com.daily.daily.dailrypage.service;

import com.daily.daily.common.service.S3StorageService;
import com.daily.daily.dailry.repository.DailryRepository;
import com.daily.daily.dailrypage.domain.DailryPage;
import com.daily.daily.dailrypage.dto.DailryPageDTO;
import com.daily.daily.dailrypage.dto.DailryPageUpdateDTO;
import com.daily.daily.dailrypage.repository.DailryPageRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static com.daily.daily.dailrypage.fixture.DailryPageFixture.다일리_페이지;
import static com.daily.daily.dailrypage.fixture.DailryPageFixture.다일리_페이지_섬네일_파일;
import static com.daily.daily.dailrypage.fixture.DailryPageFixture.다일리_페이지_수정요청_DTO;
import static com.daily.daily.dailrypage.fixture.DailryPageFixture.다일리페이지_ID;
import static com.daily.daily.dailrypage.fixture.DailryPageFixture.두번째_elementsDTO;
import static com.daily.daily.dailrypage.fixture.DailryPageFixture.비어있는_다일리_페이지;
import static com.daily.daily.dailrypage.fixture.DailryPageFixture.첫번째_elementsDTO;
import static com.daily.daily.member.fixture.MemberFixture.일반회원1_ID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DailryPageServiceTest {

    @Mock
    DailryPageRepository dailryPageRepository;

    @Mock
    DailryRepository dailryRepository;

    @Mock
    S3StorageService storageService;

    @InjectMocks
    DailryPageService dailryPageService;

    ObjectMapper objectMapper = new ObjectMapper();


    @Nested
    @DisplayName("DailryPageService/다일리 페이지 수정 메서드 테스트")
    class update{
        @Test
        @DisplayName("다일리 수정 메서드가 호출될 때에는 반드시 썸네일을 업데이트 해야한다.")
        void test1() {
            //given
            DailryPage 비어있는_다일리_페이지 = 비어있는_다일리_페이지();
            MockMultipartFile 다일리_페이지_섬네일_파일 = 다일리_페이지_섬네일_파일();
            DailryPageUpdateDTO 다일리_페이지_수정요청_dto = 다일리_페이지_수정요청_DTO();

            when(dailryPageRepository.findById(any())).thenReturn(Optional.of(비어있는_다일리_페이지));
            when(storageService.uploadImage(any(), any(), any())).thenReturn(URI.create(""));

            //when
            dailryPageService.update(일반회원1_ID, 다일리페이지_ID, 다일리_페이지_수정요청_dto, 다일리_페이지_섬네일_파일);

            //then
            verify(storageService).uploadImage(any(), any(), any());
        }

        @Test
        @DisplayName("다일리 수정 메서드에서 elements를 올바르게 추가하는지 테스트한다.")
        void test2() {
            //given
            DailryPage 비어있는_다일리_페이지 = 비어있는_다일리_페이지();
            MockMultipartFile 다일리_페이지_섬네일_파일 = 다일리_페이지_섬네일_파일();
            DailryPageUpdateDTO 다일리_페이지_수정요청_dto = 다일리_페이지_수정요청_DTO();

            when(dailryPageRepository.findById(any())).thenReturn(Optional.of(비어있는_다일리_페이지));
            when(storageService.uploadImage(any(), any(), any())).thenReturn(URI.create(""));

            //when
            DailryPageDTO result = dailryPageService.update(일반회원1_ID, 다일리페이지_ID, 다일리_페이지_수정요청_dto, 다일리_페이지_섬네일_파일);

            //then
            Collection<Object> elements = result.getElements();
            assertThat(elements).hasSize(3);
        }
        @Test
        @DisplayName("다일리 수정 메서드에서 elements 를 올바르게 삭제하는지 테스트한다.")
        void test3() {
            //given
            DailryPageUpdateDTO 다일리_페이지_수정요청_dto = new DailryPageUpdateDTO();

            List<String> deletedElementIds = new ArrayList<>();
            deletedElementIds.add(첫번째_elementsDTO().getId());
            deletedElementIds.add(두번째_elementsDTO().getId());

            다일리_페이지_수정요청_dto.setDeletedElementIds(deletedElementIds);

            when(dailryPageRepository.findById(any())).thenReturn(Optional.of(다일리_페이지()));
            when(storageService.uploadImage(any(), any(), any())).thenReturn(URI.create(""));

            //when
            DailryPageDTO result = dailryPageService.update(일반회원1_ID, 다일리페이지_ID, 다일리_페이지_수정요청_dto, 다일리_페이지_섬네일_파일());

            //then  3 - 2 = 1
            Collection<Object> elements = result.getElements();
            assertThat(elements).hasSize(1);
        }
    }
}