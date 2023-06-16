package com.iksad.simpluencer.service;

import com.iksad.simpluencer.model.request.UserRequest;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface AgentService extends UserDetailsService {
    void create(UserRequest request);
}
