package com.apps.app;

import com.apps.controller.QuantityMeasurementController;
import com.apps.entity.QuantityDTO;
import com.apps.repository.IQuantityMeasurementRepository;
import com.apps.repository.QuantityMeasurementCacheRepository;
import com.apps.repository.QuantityMeasurementJdbcRepository;
import com.apps.service.QuantityMeasurementServiceImpl;

public class QuantityMeasurementApp {
	private static QuantityMeasurementApp instance;

	public QuantityMeasurementController controller;
	public IQuantityMeasurementRepository repository;

	private QuantityMeasurementApp() {
		try {
			repository = QuantityMeasurementJdbcRepository.getInstance();
		} catch (Exception e) {
			repository = QuantityMeasurementCacheRepository.getInstance();
		}
		QuantityMeasurementServiceImpl service = new QuantityMeasurementServiceImpl(repository);
		controller = new QuantityMeasurementController(service);
	}

	public static QuantityMeasurementApp getInstance() {
		if (instance == null) {
			instance = new QuantityMeasurementApp();
		}
		return instance;
	}

	public static void main(String[] args) {

		QuantityMeasurementApp app = QuantityMeasurementApp.getInstance();
		QuantityMeasurementController controller = app.controller;

		QuantityDTO feet = QuantityDTO.ofLength(3, "FEET");
		QuantityDTO inches = QuantityDTO.ofLength(36, "INCHES");

		boolean comparisonResult = controller.performComparison(feet, inches);
		System.out.println("3 FEET == 36 INCHES ? " + comparisonResult);

		QuantityDTO resultAdd = controller.performAddition(feet, inches);
		System.out.println("Addition Result: " + resultAdd.getValue() + " " + resultAdd.getUnit().getUnitName());

		QuantityDTO conversionResult = controller.performConversion(feet, "YARDS");

		System.out.println("3 FEET in YARDS: " + conversionResult.getValue());

		QuantityDTO celsius = QuantityDTO.ofTemperature(0, "CELSIUS");

		QuantityDTO tempResult = controller.performConversion(celsius, "FAHRENHEIT");

		System.out.println("0 CELSIUS in FAHRENHEIT: " + tempResult.getValue());

		double divisionResult = controller.performDivision(feet, inches);
		System.out.println("Division Result: " + divisionResult);
	}
}