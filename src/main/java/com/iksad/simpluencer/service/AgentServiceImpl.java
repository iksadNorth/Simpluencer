package com.iksad.simpluencer.service;

import com.iksad.simpluencer.entity.Agent;
import com.iksad.simpluencer.entity.RoleOfAgent;
import com.iksad.simpluencer.model.AgentDto;
import com.iksad.simpluencer.model.request.UserRequest;
import com.iksad.simpluencer.repository.AgentRepository;
import com.iksad.simpluencer.type.RoleType;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class AgentServiceImpl implements AgentService {
    private final AgentRepository agentRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return agentRepository.findByEmail(username)
                .map(AgentDto::fromEntity)
                .orElseThrow(() -> new UsernameNotFoundException(username));
    }

    @Override
    public void create(UserRequest request) {
        Agent entity = request.toEntity(passwordEncoder);
        agentRepository.save(entity);
    }
}
