package com.iksad.simpluencer.service;

import com.iksad.simpluencer.entity.Notice;
import com.iksad.simpluencer.entity.Panel;
import com.iksad.simpluencer.exception.ErrorType.NoticeNotFoundType;
import com.iksad.simpluencer.model.WebApiNoticeCreateDto;
import com.iksad.simpluencer.model.request.NoticeCreateRequest;
import com.iksad.simpluencer.model.response.NoticeReadResponse;
import com.iksad.simpluencer.repository.NoticeRepository;
import com.iksad.simpluencer.repository.PanelRepository;
import com.iksad.simpluencer.repository.WebApiRepository;
import com.iksad.simpluencer.utils.VarInspectUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class NoticeServiceImpl implements NoticeService {
    private final NoticeRepository noticeRepository;
    private final PanelRepository panelRepository;
    private final ImageService imageService;
    private final WebApiRepository webApiRepository;

    @Override
    public void create(Long agentId, NoticeCreateRequest request) {
        Notice entity = new Notice();

        // 이미지 업로드하고
        MultipartFile image = request.image();
        String imageUri = null;
        if (!image.isEmpty()) {
            imageUri = imageService.save(image);
            entity.setImageUri(imageUri);
        }

        // 각 플랫폼에 업로드하기.
        WebApiNoticeCreateDto noticeCreateDto = WebApiNoticeCreateDto.builder()
                .content(request.content())
                .imageUri(imageUri)
                .build();

        Long[] panelIds = request.platforms();
        List<Panel> panelsSelected = panelRepository.findAllById(List.of(panelIds));
        panelsSelected.forEach(panel -> webApiRepository.createNotice(panel, noticeCreateDto));

        // 공지 테이블에 저장.
        String content = request.content();
        if (!VarInspectUtils.isBlank(content)) {
            entity.setContent(content);
        }

        noticeRepository.save(entity);
    }

    @Override
    public NoticeReadResponse readById(Long id) {
        return noticeRepository.findById(id)
                .map(NoticeReadResponse::fromEntity)
                .orElseThrow(() -> new NoticeNotFoundType(id));
    }

    @Override
    public Slice<NoticeReadResponse> read(Long agentId, Pageable pageable) {
        return noticeRepository.findByWriter_Id(agentId, pageable)
                .map(NoticeReadResponse::fromEntity);
    }
}
