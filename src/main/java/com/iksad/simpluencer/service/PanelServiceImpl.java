package com.iksad.simpluencer.service;

import com.iksad.simpluencer.entity.Panel;
import com.iksad.simpluencer.exception.ErrorType.PanelNotFoundType;
import com.iksad.simpluencer.model.request.PanelUpdateRequest;
import com.iksad.simpluencer.model.response.PanelReadResponse;
import com.iksad.simpluencer.repository.PanelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class PanelServiceImpl implements PanelService {
    private final PanelRepository panelRepository;

    private Panel loadPanelById(Long id) {
        return panelRepository.findById(id)
                .orElseThrow(() -> new PanelNotFoundType(id));
    }

    @Override
    public void update(Long id, PanelUpdateRequest request) {
        Panel entity = loadPanelById(id);

        request.overwrite(entity);
        panelRepository.save(entity);
    }

    @Override
    public void delete(Long id) {
        loadPanelById(id);

        panelRepository.deleteById(id);
    }

    @Override
    public List<PanelReadResponse> getPanelsOf(Long agentId) {
        return panelRepository.findByAgent_Id(agentId).stream()
                .map(PanelReadResponse::of).toList();
    }
}
