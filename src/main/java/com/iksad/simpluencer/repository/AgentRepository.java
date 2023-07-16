package com.iksad.simpluencer.repository;

import com.iksad.simpluencer.entity.Agent;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface AgentRepository extends JpaRepository<Agent, Long> {
    Optional<Agent> findByEmail(String username);

    @Query("SELECT a FROM Agent a LEFT JOIN FETCH a.panels p WHERE a.id = :id ORDER BY p.location")
    Optional<Agent> findWithPanelsById(Long id);
}
