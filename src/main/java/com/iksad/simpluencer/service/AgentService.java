package com.iksad.simpluencer.service;

import com.iksad.simpluencer.model.AgentDto;
import com.iksad.simpluencer.model.request.ProfileUpdateRequest;
import com.iksad.simpluencer.model.request.ResetPasswordRequest;
import com.iksad.simpluencer.model.request.UserRequest;
import com.iksad.simpluencer.model.response.ProfileResponse;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface AgentService extends UserDetailsService {
    void create(UserRequest request);

    void resetPassword(ResetPasswordRequest request);

    ProfileResponse readProfileById(Long agentId);

    void update(AgentDto principal, ProfileUpdateRequest request);
}
