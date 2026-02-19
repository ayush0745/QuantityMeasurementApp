package com.apps;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class QuantityMeasurementAppTest {

	@Test
	void testFeetEquality_SameValue() {
		QuantityMeasurementApp.Feet feet1 = new QuantityMeasurementApp.Feet(1.0);
		QuantityMeasurementApp.Feet feet2 = new QuantityMeasurementApp.Feet(1.0);

		assertEquals(feet1, feet2, "Feet objects with same value should be equal");
	}

	@Test
	void testFeetEquality_DifferentValue() {
		QuantityMeasurementApp.Feet feet1 = new QuantityMeasurementApp.Feet(1.0);
		QuantityMeasurementApp.Feet feet2 = new QuantityMeasurementApp.Feet(2.0);

		assertNotEquals(feet1, feet2, "Feet objects with different values should not be equal");
	}

	@Test
	void testFeetEquality_NullComparison() {
		QuantityMeasurementApp.Feet feet1 = new QuantityMeasurementApp.Feet(1.0);

		assertNotEquals(feet1, null, "Feet object should not be equal to null");
	}

	@Test
	void testFeetEquality_DifferentClass() {
		QuantityMeasurementApp.Feet feet1 = new QuantityMeasurementApp.Feet(1.0);
		String other = "1.0";

		assertNotEquals(feet1, other, "Feet object should not be equal to an object of a different class");
	}

	@Test
	void testFeetEquality_SameReference() {
		QuantityMeasurementApp.Feet feet1 = new QuantityMeasurementApp.Feet(1.0);
		QuantityMeasurementApp.Feet feet2 = feet1;

		assertEquals(feet1, feet2, "Feet objects that reference the same object should be equal");
	}
}