package com.iksad.simpluencer.service;

import com.iksad.simpluencer.model.request.PanelUpdateRequest;
import com.iksad.simpluencer.model.response.PanelReadResponse;

import java.util.List;

public interface PanelService {
    void update(Long id, PanelUpdateRequest request);

    void delete(Long id);

    List<PanelReadResponse> getPanelsOf(Long agentId);
}
