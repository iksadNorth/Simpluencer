package com.iksad.simpluencer.repository;

import com.iksad.simpluencer.entity.Panel;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@DataJpaTest
@DisplayName("[PanelRepository]")
class PanelRepositoryTest {
    @Autowired PanelRepository panelRepository;

    @Sql(scripts = {"classpath:/data_init/create_panel.sql"})
    @Test @DisplayName("[findByProviderAndPrincipalName][정상]")
    void findByProviderAndPrincipalName() {
        panelRepository.findAll().forEach(panel -> {
            log.info(panel.getProvider());
            log.info(panel.getPrincipalName());
            log.info("");
        });
        // Given
        String provider = "google";
        String principalName = "rfewdr3w";

        // When
        Optional<Panel> optional = panelRepository.findByProviderAndPrincipalName(provider, principalName);

        // then
        assertThat(optional.isPresent()).isTrue();
        assertThat(optional.get().getDescription()).isEqualTo("mock description1");
        optional.ifPresent(agent -> log.info(agent.toString()));
    }

    @Sql(scripts = {"classpath:/data_init/create_panel.sql"})
    @Test @DisplayName("[findByAgent_Id][정상]")
    void findByAgent_Id() {
        // Given
        Long agentId = 200L;

        // When
        List<Panel> panels = panelRepository.findByAgent_Id(agentId);

        // then
        assertThat(panels.size()).isEqualTo(4);
        assertThat(panels.get(0).getAgent().getId()).isEqualTo(200L);
        panels.forEach(agent -> log.info(agent.toString()));
    }
}