package com.apps;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class QuantityMeasurementAppTest {

	private static final double EPSILON = 0.00001;

	@Test
	public void testLengthEquality() {
		QuantityLength l1 = new QuantityLength(1.0, LengthUnit.FEET);
		QuantityLength l2 = new QuantityLength(12.0, LengthUnit.INCHES);

		assertTrue(QuantityMeasurementApp.demonstrateLengthEquality(l1, l2));
	}

	@Test
	public void testLengthComparisonUsingValues() {
		boolean result = QuantityMeasurementApp.demonstrateLengthComparison(3.0, LengthUnit.FEET, 1.0,
				LengthUnit.YARDS);

		assertTrue(result);
	}

	@Test
	public void testLengthConversionUsingValues() {
		QuantityLength converted = QuantityMeasurementApp.demonstrateLengthConversion(1.0, LengthUnit.FEET,
				LengthUnit.INCHES);

		assertEquals(12.0, converted.getValue(), EPSILON);
		assertEquals(LengthUnit.INCHES, converted.getUnit());
	}

	@Test
	public void testLengthConversionUsingObject() {
		QuantityLength original = new QuantityLength(2.0, LengthUnit.YARDS);

		QuantityLength converted = QuantityMeasurementApp.demonstrateLengthConversion(original, LengthUnit.FEET);

		assertEquals(6.0, converted.getValue(), EPSILON);
		assertEquals(LengthUnit.FEET, converted.getUnit());
	}

	@Test
	public void testLengthAdditionDefaultUnit() {
		QuantityLength l1 = new QuantityLength(2.0, LengthUnit.YARDS);
		QuantityLength l2 = new QuantityLength(3.0, LengthUnit.FEET);

		QuantityLength sum = QuantityMeasurementApp.demonstrateLengthAddition(l1, l2);

		assertEquals(3.0, sum.getValue(), EPSILON);
		assertEquals(LengthUnit.YARDS, sum.getUnit());
	}

	@Test
	public void testLengthAdditionWithTargetUnit() {
		QuantityLength l1 = new QuantityLength(2.0, LengthUnit.YARDS);
		QuantityLength l2 = new QuantityLength(3.0, LengthUnit.FEET);

		QuantityLength sum = QuantityMeasurementApp.demonstrateLengthAddition(l1, l2, LengthUnit.INCHES);

		assertEquals(108.0, sum.getValue(), EPSILON);
		assertEquals(LengthUnit.INCHES, sum.getUnit());
	}

	@Test
	public void testNullEqualityHandling() {
		QuantityLength l1 = new QuantityLength(1.0, LengthUnit.FEET);
		assertFalse(QuantityMeasurementApp.demonstrateLengthEquality(l1, null));
	}

	@Test
	public void testNullConversionThrowsException() {
		assertThrows(IllegalArgumentException.class,
				() -> QuantityMeasurementApp.demonstrateLengthConversion(null, LengthUnit.FEET));
	}
}