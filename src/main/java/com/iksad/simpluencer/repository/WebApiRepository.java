package com.iksad.simpluencer.repository;

import com.iksad.simpluencer.entity.Panel;
import com.iksad.simpluencer.model.WebApiNoticeCreateDto;

public interface WebApiRepository {
    void createNotice(Panel panel, WebApiNoticeCreateDto dto);
}
