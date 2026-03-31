package com.apps.quantitymeasurement.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.apps.quantitymeasurement.model.QuantityDTO;
import com.apps.quantitymeasurement.request.CompareRequest;
import com.apps.quantitymeasurement.service.IQuantityMeasurementService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/quantity")
public class QuantityMeasurementController {

	private static final Logger logger = LoggerFactory.getLogger(QuantityMeasurementController.class);

	private final IQuantityMeasurementService quantityMeasurementService;

	public QuantityMeasurementController(IQuantityMeasurementService quantityMeasurementService) {
		this.quantityMeasurementService = quantityMeasurementService;
		logger.info("Quantity Measurement Controller initialized");
	}

	@PostMapping("/compare")
	public ResponseEntity<Boolean> performComparison(@RequestBody CompareRequest request) {
		logger.info("Comparing quantities");
		boolean result = quantityMeasurementService.compare(request.getThisQuantity(), request.getThatQuantity());
		return ResponseEntity.ok(result);
	}

	@PostMapping("/convert")
	public ResponseEntity<QuantityDTO> performConversion(@Valid @RequestBody QuantityDTO quantityDTO,
			@RequestParam String targetUnit) {
		logger.info("Converting quantity to {}", targetUnit);
		QuantityDTO result = quantityMeasurementService.convert(quantityDTO, targetUnit);
		return ResponseEntity.ok(result);
	}

	@PostMapping("/add")
	public ResponseEntity<QuantityDTO> performAddition(@RequestBody CompareRequest request) {
		logger.info("Adding quantities");
		QuantityDTO result = quantityMeasurementService.add(request.getThisQuantity(), request.getThatQuantity());
		return ResponseEntity.ok(result);
	}

	@PostMapping("/subtract")
	public ResponseEntity<QuantityDTO> performSubtraction(@RequestBody CompareRequest request) {
		logger.info("Subtracting quantities");
		QuantityDTO result = quantityMeasurementService.subtract(request.getThisQuantity(), request.getThatQuantity());
		return ResponseEntity.ok(result);
	}

	@PostMapping("/divide")
	public ResponseEntity<Double> performDivision(@RequestBody CompareRequest request) {
		logger.info("Dividing quantities");
		double result = quantityMeasurementService.divide(request.getThisQuantity(), request.getThatQuantity());
		return ResponseEntity.ok(result);
	}
}