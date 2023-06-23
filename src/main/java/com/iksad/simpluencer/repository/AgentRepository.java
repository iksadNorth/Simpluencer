package com.iksad.simpluencer.repository;

import com.iksad.simpluencer.entity.Agent;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface AgentRepository extends JpaRepository<Agent, Long> {
    Optional<Agent> findByEmail(String username);

    @EntityGraph(attributePaths = {"panels"})
    @Query("SELECT a FROM Agent a WHERE a.id = :id")
    Optional<Agent> findWithPanelsById(Long id);
}
