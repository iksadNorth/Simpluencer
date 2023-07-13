package com.iksad.simpluencer.repository;

import com.iksad.simpluencer.entity.Panel;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@ActiveProfiles(profiles = {"datasource-prod", "mysql-prod"})
@DataJpaTest
@DisplayName("[MySQLPanelRepositoryTest]")
class MySQLPanelRepositoryTest {
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
        assertThat(panels.size()).isEqualTo(5);
        assertThat(panels.get(0).getAgent().getId()).isEqualTo(200L);
        panels.forEach(agent -> log.info(agent.toString()));
    }

    @Sql(scripts = {"classpath:/data_init/create_panel.sql"})
    @Test @DisplayName("[findByAgent_Id][정상] location 값대로 정렬이 되어 있다.")
    void findByAgent_IdOrderedByLocation() {
        // Given
        Long agentId = 200L;

        // When
        List<Panel> panels = panelRepository.findByAgent_Id(agentId);

        // then
        int length = panels.size();
        for(int i=0; i<length-1; i++) {
            if(panels.get(i+1).getLocation() < panels.get(i).getLocation()) {
                throw new AssertionError("Location 순으로 오름차순 정렬이 안되어 있음.");
            }
        }
    }

    @Sql(scripts = {"classpath:/data_init/create_panel.sql"})
    @Test @DisplayName("[findByAgent_Id][정상] id 값대로 정렬이 되어 있다.")
    void findByAgent_IdOrderById() {
        // Given
        Long agentId = 200L;

        // When
        List<Panel> panels = panelRepository.findByAgent_IdOrderById(agentId);

        // then
        int length = panels.size();
        for(int i=0; i<length-1; i++) {
            if(panels.get(i+1).getId() < panels.get(i).getId()) {
                throw new AssertionError("id 순으로 오름차순 정렬이 안되어 있음.");
            }
        }
    }
}