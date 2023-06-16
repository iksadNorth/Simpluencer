package com.iksad.simpluencer.service;

import com.iksad.simpluencer.repository.AgentRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class AgentServiceImplTest {
    @InjectMocks
    private AgentServiceImpl agentService;
    @Mock
    private AgentRepository agentRepository;

    @Test @DisplayName("[loadUserByUsername][비정상] 존재하지 않는 계정.")
    void loadUserByUsername() {
        // Given - 조회되지 않으면,
        String username = "mock email";
        given(agentRepository.findByEmail(anyString())).willReturn(Optional.empty());

        // When - loadUserByUsername 실행 시,
        // Then - UsernameNotFoundException 에러를 내뱉는다.
        assertThrows(UsernameNotFoundException.class, () -> {
            agentService.loadUserByUsername(username);
        });
    }
}