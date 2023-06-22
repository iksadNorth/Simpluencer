package com.iksad.simpluencer.fixture;

import com.iksad.simpluencer.model.AgentDto;
import com.iksad.simpluencer.model.PanelDto;

public class PanelFixture {
    public static PanelDto dto1(AgentDto agentDto) {
        return PanelDto.builder()
                .agentDto(agentDto)
                .provider("mock provider")
                .principalName("mock name")
                .description("mock description")
                .location(0)
                .build();
    }
}
