package com.iksad.simpluencer.repository;

import com.iksad.simpluencer.entity.Panel;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PanelRepository extends JpaRepository<Panel, Long> {
    @EntityGraph(attributePaths = {"agent"})
    Optional<Panel> findByProviderAndPrincipalName(String clientRegistrationId, String principalName);

    List<Panel> findByAgent_Id(Long agentId);
}
