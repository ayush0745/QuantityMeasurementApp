package com.apps.quantitymeasurement.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.apps.quantitymeasurement.entities.AppUserEntity;

@Repository
public interface AppUserRepository extends JpaRepository<AppUserEntity, Long> {
	Optional<AppUserEntity> findByUsername(String username);
	Optional<AppUserEntity> findByEmail(String email);

	boolean existsByUsername(String username);
	boolean existsByEmail(String email);
}