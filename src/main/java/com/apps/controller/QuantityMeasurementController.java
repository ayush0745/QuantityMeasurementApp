package com.apps.controller;

import com.apps.entity.QuantityDTO;
import com.apps.service.IQuantityMeasurementService;

public class QuantityMeasurementController {

	private IQuantityMeasurementService quantityMeasurementService;

	public QuantityMeasurementController(IQuantityMeasurementService quantityMeasurementService) {
		this.quantityMeasurementService = quantityMeasurementService;
	}

	public boolean performComparison(QuantityDTO thisQuantityDTO, QuantityDTO thatQuantityDTO) {
		return quantityMeasurementService.compare(thisQuantityDTO, thatQuantityDTO);
	}

	public QuantityDTO performConversion(QuantityDTO thisQuantityDTO, String targetUnit) {
		return quantityMeasurementService.convert(thisQuantityDTO, targetUnit);
	}

	public QuantityDTO performAddition(QuantityDTO thisQuantityDTO, QuantityDTO thatQuantityDTO) {
		return quantityMeasurementService.add(thisQuantityDTO, thatQuantityDTO);
	}

	public QuantityDTO performAddition(QuantityDTO thisQuantityDTO, QuantityDTO thatQuantityDTO,
			QuantityDTO targetUnitDTO) {
		return quantityMeasurementService.add(thisQuantityDTO, thatQuantityDTO, targetUnitDTO);
	}

	public QuantityDTO performSubtraction(QuantityDTO thisQuantityDTO, QuantityDTO thatQuantityDTO) {
		return quantityMeasurementService.subtract(thisQuantityDTO, thatQuantityDTO);
	}

	public QuantityDTO performSubtraction(QuantityDTO thisQuantityDTO, QuantityDTO thatQuantityDTO,
			QuantityDTO targetUnitDTO) {
		return quantityMeasurementService.subtract(thisQuantityDTO, thatQuantityDTO, targetUnitDTO);
	}

	public double performDivision(QuantityDTO thisQuantityDTO, QuantityDTO thatQuantityDTO) {
		return quantityMeasurementService.divide(thisQuantityDTO, thatQuantityDTO);
	}
}