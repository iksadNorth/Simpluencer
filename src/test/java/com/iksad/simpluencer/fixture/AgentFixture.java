package com.iksad.simpluencer.fixture;

import com.iksad.simpluencer.model.request.UserRequest;

public class AgentFixture {
    public static UserRequest userRequest1() {
        return UserRequest.builder()
                .email("mock email")
                .password("mock password")
                .nickname("mock nickname")
                .build();
    }
}
