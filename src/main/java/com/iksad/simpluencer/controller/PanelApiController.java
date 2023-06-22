package com.iksad.simpluencer.controller;

import com.iksad.simpluencer.model.request.PanelUpdateRequest;
import com.iksad.simpluencer.service.PanelService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.iksad.simpluencer.utils.ResponseEntityUtils.getOkResponse;

@RestController
@RequestMapping("/api/v1/panel")
@RequiredArgsConstructor
public class PanelApiController {
    private final PanelService panelService;

    @PatchMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id, @RequestBody PanelUpdateRequest request) {
        panelService.update(id, request);
        return getOkResponse();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        panelService.delete(id);
        return getOkResponse();
    }
}
