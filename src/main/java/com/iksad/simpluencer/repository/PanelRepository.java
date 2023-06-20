package com.iksad.simpluencer.repository;

import com.iksad.simpluencer.entity.Panel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PanelRepository extends JpaRepository<Panel, Long> {
    Optional<Panel> findByProviderAndPrincipalName(String clientRegistrationId, String principalName);
}
