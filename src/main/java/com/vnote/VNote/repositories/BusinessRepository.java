package com.vnote.VNote.repositories;

import com.vnote.VNote.entities.BusinessEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface BusinessRepository extends JpaRepository<BusinessEntity, UUID> {
    Optional<BusinessEntity> findByName(String name);
    boolean existsByUserIdAndName(UUID userId, String name);
}
