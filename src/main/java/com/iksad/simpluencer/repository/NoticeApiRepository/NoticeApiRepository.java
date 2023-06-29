package com.iksad.simpluencer.repository.NoticeApiRepository;

import com.iksad.simpluencer.entity.Panel;
import com.iksad.simpluencer.model.WebApiNoticeCreateDto;

public interface NoticeApiRepository {
    void createNotice(Panel panel, WebApiNoticeCreateDto dto);
}
