package com.iksad.simpluencer.service;

import com.iksad.simpluencer.config.EmailContent.PasswordResetEmailContent;
import com.iksad.simpluencer.entity.Agent;
import com.iksad.simpluencer.exception.ErrorType;
import com.iksad.simpluencer.model.AgentDto;
import com.iksad.simpluencer.model.request.ResetPasswordRequest;
import com.iksad.simpluencer.model.request.UserRequest;
import com.iksad.simpluencer.repository.AgentRepository;
import com.iksad.simpluencer.repository.EmailRepository;
import com.iksad.simpluencer.utils.RandomUtils;
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
    private final EmailRepository emailRepository;
    private final PasswordResetEmailContent passwordResetEmailContent;

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

    @Override
    public void resetPassword(ResetPasswordRequest request) {
        // 조회하고
        String email = request.email();
        Agent entity = agentRepository.findByEmail(email)
                .orElseThrow(() -> ErrorType.EMAIL_NOT_FOUND.toException(email));

        // 무작위 생성된 비밀번호 16자리를 생성하고
        String newPassWord = RandomUtils.getPassWordHex();

        // 비밀 번호를 바꾼 뒤, DB에 저장.
        entity.setPassword(passwordEncoder.encode(newPassWord));
        agentRepository.save(entity);

        // 이메일로 해당 비밀 번호 통보.
        String title = passwordResetEmailContent.getTitle(entity);
        String content = passwordResetEmailContent.getContent(entity, newPassWord);
        emailRepository.send(email, title, content);
    }
}
