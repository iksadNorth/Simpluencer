package com.iksad.simpluencer.model.request;

import com.iksad.simpluencer.entity.Panel;
import lombok.Builder;

import static com.iksad.simpluencer.utils.VarInspectUtils.isBlank;

@Builder(toBuilder = true)
public record PanelUpdateRequest(
        String description,
        int location,
        String link
) {
    public void overwrite(Panel entity) {
        if(!isBlank(this.description))
            entity.setDescription(this.description);
        if(this.location != 0)
            entity.setLocation(this.location);
        if(!isBlank(this.link))
            entity.setLink(this.link);
    }
}
