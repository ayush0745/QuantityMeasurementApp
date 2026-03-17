package com.apps.app;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.apps.controller.QuantityMeasurementController;
import com.apps.entity.QuantityDTO;
import com.apps.exception.QuantityMeasurementException;

public class QuantityMeasurementAppTest {

	private static final double EPSILON = 0.00001;

	private QuantityMeasurementController controller;

	@BeforeEach
	void setup() {
		controller = QuantityMeasurementApp.getInstance().controller;
	}

	@Test
	void testLengthEquality() {
		QuantityDTO feet = QuantityDTO.ofLength(1, "FEET");
		QuantityDTO inches = QuantityDTO.ofLength(12, "INCHES");

		assertTrue(controller.performComparison(feet, inches));
	}

	@Test
	void testWeightEquality() {
		QuantityDTO kg = QuantityDTO.ofWeight(1, "KILOGRAM");
		QuantityDTO gram = QuantityDTO.ofWeight(1000, "GRAM");

		assertTrue(controller.performComparison(kg, gram));
	}

	@Test
	void testVolumeEquality() {
		QuantityDTO litre = QuantityDTO.ofVolume(1, "LITRE");
		QuantityDTO gallon = QuantityDTO.ofVolume(0.264172, "GALLON");

		assertTrue(controller.performComparison(litre, gallon));
	}

	@Test
	void testConversion() {
		QuantityDTO feet = QuantityDTO.ofLength(1, "FEET");

		QuantityDTO result = controller.performConversion(feet, "INCHES");

		assertEquals(12.0, result.getValue(), EPSILON);
		assertEquals("INCHES", result.getUnit().getUnitName());
	}

	@Test
	void testAdditionDefaultUnit() {
		QuantityDTO yards = QuantityDTO.ofLength(2, "YARDS");
		QuantityDTO feet = QuantityDTO.ofLength(3, "FEET");

		QuantityDTO result = controller.performAddition(yards, feet);

		assertEquals(3.0, result.getValue(), EPSILON);
		assertEquals("YARDS", result.getUnit().getUnitName());
	}

	@Test
	void testDivision() {
		QuantityDTO inches = QuantityDTO.ofLength(24, "INCHES");
		QuantityDTO feet = QuantityDTO.ofLength(2, "FEET");

		double result = controller.performDivision(inches, feet);

		assertEquals(1.0, result, EPSILON);
	}

	@Test
	void testDivisionByZero() {
		QuantityDTO q1 = QuantityDTO.ofLength(10, "FEET");
		QuantityDTO q2 = QuantityDTO.ofLength(0, "FEET");

		QuantityMeasurementException exception = assertThrows(QuantityMeasurementException.class,
				() -> controller.performDivision(q1, q2));

		assertEquals("Division by zero not allowed", exception.getMessage());
	}

	@Test
	void testTemperatureConversion() {
		QuantityDTO celsius = QuantityDTO.ofTemperature(0, "CELSIUS");

		QuantityDTO result = controller.performConversion(celsius, "FAHRENHEIT");

		assertEquals(32.0, result.getValue(), EPSILON);
	}

	@Test
	void testTemperatureUnsupportedAddition() {
		QuantityDTO t1 = QuantityDTO.ofTemperature(30, "CELSIUS");
		QuantityDTO t2 = QuantityDTO.ofTemperature(20, "CELSIUS");

		QuantityMeasurementException exception = assertThrows(QuantityMeasurementException.class,
				() -> controller.performAddition(t1, t2));

		assertTrue(exception.getMessage().contains("not allowed"));
	}

	@Test
	void testCrossCategoryComparison() {
		QuantityDTO length = QuantityDTO.ofLength(1, "FEET");
		QuantityDTO weight = QuantityDTO.ofWeight(1, "KILOGRAM");

		assertThrows(QuantityMeasurementException.class, () -> controller.performComparison(length, weight));
	}

}