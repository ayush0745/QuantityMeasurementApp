package com.apps.quantitymeasurement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.apps.quantitymeasurement.model.QuantityMeasurementEntity;

import java.util.List;

@Repository
public interface QuantityMeasurementDatabaseRepository extends JpaRepository<QuantityMeasurementEntity, Long> {

	List<QuantityMeasurementEntity> findByOperation(String operation);

	List<QuantityMeasurementEntity> findByThisMeasurementType(String type);
}