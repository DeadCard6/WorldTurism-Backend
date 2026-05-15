package com.worldturism.spring.app.repository;

import com.worldturism.spring.app.model.ProviderProfile;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProviderProfileRepository extends JpaRepository<ProviderProfile, Long> {

	Optional<ProviderProfile> findByUserId(Long userId);
}
