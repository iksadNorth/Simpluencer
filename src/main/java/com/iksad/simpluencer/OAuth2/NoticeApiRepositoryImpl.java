package com.iksad.simpluencer.OAuth2;

import com.iksad.simpluencer.entity.Panel;
import com.iksad.simpluencer.model.WebApiNoticeCreateDto;
import com.iksad.simpluencer.type.OAuth2ProviderType;
import org.springframework.stereotype.Component;

@Component
public class NoticeApiRepositoryImpl implements NoticeApiRepository {
    @Override
    public void createNotice(Panel panel, WebApiNoticeCreateDto dto, Args args) {
        OAuth2ProviderType providerType = OAuth2ProviderType.valueOf(panel.getProvider());
        providerType.getNoticeApiRepository().createNotice(panel, dto, args);
    }
}
