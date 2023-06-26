package com.iksad.simpluencer.service;

import com.iksad.simpluencer.model.request.NoticeCreateRequest;
import com.iksad.simpluencer.model.response.NoticeReadResponse;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Pageable;

public interface NoticeService {
    Slice<NoticeReadResponse> read(Long agentId, Pageable pageable);

    void create(Long agentId, NoticeCreateRequest request);

    NoticeReadResponse readById(Long id);
}
