package com.iksad.simpluencer.repository;

import com.iksad.simpluencer.entity.Panel;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PanelRepository extends JpaRepository<Panel, Long> {
    @EntityGraph(attributePaths = {"agent"})
    Optional<Panel> findByProviderAndPrincipalName(String clientRegistrationId, String principalName);

    @Query("SELECT p FROM Panel p WHERE p.agent.id = :agentId ORDER BY p.location")
    List<Panel> findByAgent_Id(Long agentId);

    @Query("SELECT p FROM Panel p WHERE p.agent.id = :agentId ORDER BY p.id")
    List<Panel> findByAgent_IdOrderById(Long agentId);
}
