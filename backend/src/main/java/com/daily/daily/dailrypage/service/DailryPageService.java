package com.daily.daily.dailrypage.service;

import com.daily.daily.common.exception.UnauthorizedAccessException;
import com.daily.daily.common.service.S3StorageService;
import com.daily.daily.dailry.domain.Dailry;
import com.daily.daily.dailry.exception.DailryNotFoundException;
import com.daily.daily.dailry.repository.DailryRepository;
import com.daily.daily.dailrypage.domain.DailryPage;
import com.daily.daily.dailrypage.dto.*;
import com.daily.daily.dailrypage.exception.DailryPageNotFoundException;
import com.daily.daily.dailrypage.exception.DailryPageThumbnailNotFoundException;
import com.daily.daily.dailrypage.repository.DailryPageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class DailryPageService {
    private final String THUMBNAIL_STORAGE_DIRECTORY_PATH = "thumbnail/%s/%s/" + LocalDate.now(ZoneId.of("Asia/Seoul"));

    private final DailryPageRepository dailryPageRepository;

    private final DailryRepository dailryRepository;

    private final S3StorageService storageService;

    public DailryPageCreateResponseDTO create(Long memberId, Long dailryId) {
        Dailry dailry = dailryRepository.findById(dailryId)
                .orElseThrow(DailryNotFoundException::new);

        if (!dailry.belongsTo(memberId)) {
            throw new UnauthorizedAccessException();
        }

        int dailryPageCount = dailryPageRepository.countByDailry(dailry);
        int newDiaryPageNumber = dailryPageCount + 1;

        DailryPage dailryPage = DailryPage.builder()
                .dailry(dailry)
                .pageNumber(newDiaryPageNumber)
                .build();

        DailryPage savedPage = dailryPageRepository.save(dailryPage);

        return DailryPageCreateResponseDTO.from(savedPage);
    }

    public DailryPageDTO update(Long memberId, Long pageId, DailryPageUpdateDTO dailryPageUpdateDTO, MultipartFile thumbnail) {

        DailryPage findPage = dailryPageRepository.findById(pageId)
                .orElseThrow(DailryPageNotFoundException::new);

        if (!findPage.belongsTo(memberId)) {
            throw new UnauthorizedAccessException();
        }
        findPage.updateBackground(dailryPageUpdateDTO.getBackground());
        findPage.updateElements(dailryPageUpdateDTO.getElements());

        if (thumbnail == null || thumbnail.isEmpty()) {
            throw new DailryPageThumbnailNotFoundException();
        }

        uploadThumbnail(findPage, thumbnail, memberId, findPage.getDailry().getId());
        return DailryPageDTO.from(findPage);
    }

    private void uploadThumbnail(DailryPage findPage, MultipartFile thumbnail, Long memberId, Long dailryId) {
        String fileUrl = storageService.uploadImage(thumbnail, String.format(THUMBNAIL_STORAGE_DIRECTORY_PATH, memberId, dailryId), findPage.getId().toString());
        findPage.updateThumbnail(fileUrl);
    }


    public DailryPageDTO find(Long memberId, Long pageId) {
        DailryPage findPage = dailryPageRepository.findById(pageId)
                .orElseThrow(DailryPageNotFoundException::new);

        if (!findPage.belongsTo(memberId)) {
            throw new UnauthorizedAccessException();
        }

        return DailryPageDTO.from(findPage);
    }

    public DailryPagePreviewDTO findAll(Long memberId, Long dailryId) {
        Dailry dailry = dailryRepository.findById(dailryId)
                .orElseThrow(DailryNotFoundException::new);

        if (!dailry.belongsTo(memberId)) {
            throw new UnauthorizedAccessException();
        }

        List<DailryPageThumbnailDTO> thumbnails = dailryPageRepository.findThumbnails(dailryId);

        return DailryPagePreviewDTO.from(dailryId, thumbnails);
    }

    public void delete(Long memberId, Long pageId) {
        DailryPage findPage = dailryPageRepository.findById(pageId)
                .orElseThrow(DailryPageNotFoundException::new);

        if (!findPage.belongsTo(memberId)) {
            throw new UnauthorizedAccessException();
        }
        dailryPageRepository.deleteById(pageId);
    }
}
