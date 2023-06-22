package com.iksad.simpluencer.fixture;

import com.iksad.simpluencer.model.AgentDto;
import com.iksad.simpluencer.model.request.UserRequest;
import com.iksad.simpluencer.type.RoleType;

import java.time.LocalDateTime;
import java.util.List;

public class AgentFixture {
    public static UserRequest userRequest1() {
        return UserRequest.builder()
                .email("mock email")
                .password("mock password")
                .nickname("mock nickname")
                .build();
    }

    public static AgentDto principal() {
        return AgentDto.builder()
                .id(1L)
                .createdAt(LocalDateTime.now())
                .email("principal email")
                .password("principal password")
                .nickname("principal nickname")
                .roles(List.of(RoleType.USER))
                .build();
    }
}
