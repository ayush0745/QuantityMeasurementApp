package com.apps;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class QuantityMeasurementAppTest {

	@Test
	void testFeetEquality_SameValue() {
		assertTrue(QuantityMeasurementApp.demonstrateFeetEquality(1.0, 1.0));
	}

	@Test
	void testFeetEquality_DifferentValue() {
		assertFalse(QuantityMeasurementApp.demonstrateFeetEquality(1.0, 2.0));
	}

	@Test
	void testFeetEquality_NullComparison() {
		QuantityMeasurementApp.Feet f = new QuantityMeasurementApp.Feet(1.0);
		assertNotEquals(f, null);
	}

	@Test
	void testFeetEquality_SameReference() {
		QuantityMeasurementApp.Feet f1 = new QuantityMeasurementApp.Feet(1.0);
		QuantityMeasurementApp.Feet f2 = f1;
		assertEquals(f1, f2);
	}

	@Test
	void testInchesEquality_SameValue() {
		assertTrue(QuantityMeasurementApp.demonstrateInchesEquality(1.0, 1.0));
	}

	@Test
	void testInchesEquality_DifferentValue() {
		assertFalse(QuantityMeasurementApp.demonstrateInchesEquality(1.0, 2.0));
	}

	@Test
	void testInchesEquality_NullComparison() {
		QuantityMeasurementApp.Inches i = new QuantityMeasurementApp.Inches(1.0);
		assertNotEquals(i, null);
	}

	@Test
	void testInchesEquality_SameReference() {
		QuantityMeasurementApp.Inches i1 = new QuantityMeasurementApp.Inches(1.0);
		QuantityMeasurementApp.Inches i2 = i1;
		assertEquals(i1, i2);
	}
}