package com.iksad.simpluencer.service;

import com.iksad.simpluencer.model.AgentDto;
import com.iksad.simpluencer.repository.AgentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class AgentServiceImpl implements AgentService {
    private final AgentRepository agentRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return agentRepository.findByEmail(username)
                .map(AgentDto::fromEntity)
                .orElseThrow(() -> new UsernameNotFoundException(username));
    }
}
