package com.apps.quantitymeasurement.service;

import com.apps.quantitymeasurement.model.QuantityDTO;
import com.apps.quantitymeasurement.entities.QuantityMeasurementEntity;

public interface IQuantityMeasurementService {
	public boolean compare(QuantityDTO thisQuantityDTO, QuantityDTO thatQuantityDTO);

	public QuantityDTO convert(QuantityDTO thisQuantityDTO, String targetUnit);

	public QuantityDTO add(QuantityDTO thisQuantityDTO, QuantityDTO thatQuantityDTO);

	public QuantityDTO add(QuantityDTO thisQuantityDTO, QuantityDTO thatQuantityDTO, QuantityDTO targetUnitDTO);

	public QuantityDTO subtract(QuantityDTO thisQuantityDTO, QuantityDTO thatQuantityDTO);

	public QuantityDTO subtract(QuantityDTO thisQuantityDTO, QuantityDTO thatQuantityDTO, QuantityDTO targetUnitDTO);

	public double divide(QuantityDTO thisQuantityDTO, QuantityDTO thatQuantityDTO);

	public java.util.List<QuantityMeasurementEntity> getHistory();
}