package com.iksad.simpluencer.service;

import com.iksad.simpluencer.config.EmailContent.PasswordResetEmailContent;
import com.iksad.simpluencer.entity.Agent;
import com.iksad.simpluencer.entity.Panel;
import com.iksad.simpluencer.exception.ErrorType.AgentNotFoundType;
import com.iksad.simpluencer.exception.ErrorType.EmailNotFoundType;
import com.iksad.simpluencer.model.AgentDto;
import com.iksad.simpluencer.model.request.ProfileUpdateRequest;
import com.iksad.simpluencer.model.request.ResetPasswordRequest;
import com.iksad.simpluencer.model.request.UserRequest;
import com.iksad.simpluencer.model.response.ProfileResponse;
import com.iksad.simpluencer.repository.AgentRepository;
import com.iksad.simpluencer.repository.EmailRepository;
import com.iksad.simpluencer.repository.PanelRepository;
import com.iksad.simpluencer.utils.RandomUtils;
import com.iksad.simpluencer.utils.SessionHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor
public class AgentServiceImpl implements AgentService {
    private final AgentRepository agentRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailRepository emailRepository;
    private final PasswordResetEmailContent passwordResetEmailContent;
    private final PanelRepository panelRepository;
    private final ImageService imageService;

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
                .orElseThrow(() -> new EmailNotFoundType(email));

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

    @Override
    public ProfileResponse readProfileById(Long agentId) {
        return agentRepository.findWithPanelsById(agentId)
                .map(ProfileResponse::fromEntity)
                .orElseThrow(() -> new AgentNotFoundType(agentId));
    }

    @Override
    public void update(AgentDto principal, ProfileUpdateRequest request) {
        Agent entity = principal.toEntity();

        // 유저 정보 저장.
        if(request.nickname() != null && !request.nickname().isBlank()) {
            entity.setNickname(request.nickname());
        }
        if(request.introduction() != null && !request.introduction().isBlank()) {
            entity.setIntroduction(request.introduction());
        }

        // 각 패널의 순서 조정.
        if(request.locations() != null && request.locations().length != 0) {
            List<Panel> panels = panelRepository.findByAgent_IdOrderById(entity.getId());
            setPanelsLocation(request.locations(), panels);
            entity.setPanels(panels);
        }

        // 이미지 저장 및 파일 위치 추출.
        if(request.image() != null && !request.image().isEmpty()) {
            String dirSavedImage = imageService.save(request.image());
            entity.setProfileImage(dirSavedImage);
        }

        // 엔티티 저장.
        AgentDto principalUpdated = AgentDto.fromEntity(agentRepository.save(entity));

        // Security Context Holder 최신화하기.
        SessionHandler.updateContext(principalUpdated);
    }

    public void setPanelsLocation(Long[] locations, List<Panel> panels) {
        assert locations.length == panels.size() : "DB에 저장된 패널 갯수와 프론트에서 넘어온 패널 갯수가 다름.";

        Map<Long, Panel> idMapper = new HashMap<>();
        for(Panel panel : panels) {
            idMapper.put(panel.getId(), panel);
        }

        for (int i=0; i<panels.size(); i++) {
            idMapper.get(locations[i]).setLocation(i);
        }
    }
}
