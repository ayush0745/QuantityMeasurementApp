package com.apps;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class QuantityMeasurementAppTest {

	private static final double EPSILON = 0.00001;

	@Test
	public void testEquality() {
		Quantity<LengthUnit> l1 = new Quantity<>(1.0, LengthUnit.FEET);
		Quantity<LengthUnit> l2 = new Quantity<>(12.0, LengthUnit.INCHES);

		assertTrue(QuantityMeasurementApp.demonstrateEquality(l1, l2));

		Quantity<WeightUnit> w1 = new Quantity<>(1.0, WeightUnit.KILOGRAM);
		Quantity<WeightUnit> w2 = new Quantity<>(1000.0, WeightUnit.GRAM);

		assertTrue(QuantityMeasurementApp.demonstrateEquality(w1, w2));
	}

	@Test
	public void testComparisonUsingValues() {
		boolean lengthResult = QuantityMeasurementApp.demonstrateComparison(3.0, LengthUnit.FEET, 1.0,
				LengthUnit.YARDS);
		assertTrue(lengthResult);

		boolean weightResult = QuantityMeasurementApp.demonstrateComparison(1.0, WeightUnit.KILOGRAM, 1000.0,
				WeightUnit.GRAM);
		assertTrue(weightResult);
	}

	@Test
	public void testConversion() {
		Quantity<LengthUnit> length = new Quantity<>(1.0, LengthUnit.FEET);
		Quantity<LengthUnit> convertedLength = QuantityMeasurementApp.demonstrateConversion(length, LengthUnit.INCHES);
		assertEquals(12.0, convertedLength.getValue(), EPSILON);
		assertEquals(LengthUnit.INCHES, convertedLength.getUnit());

		Quantity<WeightUnit> weight = new Quantity<>(1.0, WeightUnit.KILOGRAM);
		Quantity<WeightUnit> convertedWeight = QuantityMeasurementApp.demonstrateConversion(weight, WeightUnit.GRAM);
		assertEquals(1000.0, convertedWeight.getValue(), EPSILON);
		assertEquals(WeightUnit.GRAM, convertedWeight.getUnit());
	}

	@Test
	public void testAdditionDefaultUnit() {
		Quantity<LengthUnit> l1 = new Quantity<>(2.0, LengthUnit.YARDS);
		Quantity<LengthUnit> l2 = new Quantity<>(3.0, LengthUnit.FEET);

		Quantity<LengthUnit> sum = QuantityMeasurementApp.demonstrateAddition(l1, l2);
		assertEquals(3.0, sum.getValue(), EPSILON);
		assertEquals(LengthUnit.YARDS, sum.getUnit());

		Quantity<WeightUnit> w1 = new Quantity<>(1.0, WeightUnit.KILOGRAM);
		Quantity<WeightUnit> w2 = new Quantity<>(1000.0, WeightUnit.GRAM);

		Quantity<WeightUnit> sumWeight = QuantityMeasurementApp.demonstrateAddition(w1, w2);
		assertEquals(2.0, sumWeight.getValue(), EPSILON);
		assertEquals(WeightUnit.KILOGRAM, sumWeight.getUnit());
	}

	@Test
	public void testAdditionWithTargetUnit() {
		Quantity<LengthUnit> l1 = new Quantity<>(2.0, LengthUnit.YARDS);
		Quantity<LengthUnit> l2 = new Quantity<>(3.0, LengthUnit.FEET);

		Quantity<LengthUnit> sum = QuantityMeasurementApp.demonstrateAddition(l1, l2, LengthUnit.INCHES);
		assertEquals(108.0, sum.getValue(), EPSILON);
		assertEquals(LengthUnit.INCHES, sum.getUnit());

		Quantity<WeightUnit> w1 = new Quantity<>(1.0, WeightUnit.KILOGRAM);
		Quantity<WeightUnit> w2 = new Quantity<>(1000.0, WeightUnit.GRAM);

		Quantity<WeightUnit> sumWeight = QuantityMeasurementApp.demonstrateAddition(w1, w2, WeightUnit.GRAM);
		assertEquals(2000.0, sumWeight.getValue(), EPSILON);
		assertEquals(WeightUnit.GRAM, sumWeight.getUnit());
	}

	@Test
	public void testNullEqualityHandling() {
		Quantity<LengthUnit> l1 = new Quantity<>(1.0, LengthUnit.FEET);
		assertFalse(QuantityMeasurementApp.demonstrateEquality(l1, null));

		Quantity<WeightUnit> w1 = new Quantity<>(1.0, WeightUnit.KILOGRAM);
		assertFalse(QuantityMeasurementApp.demonstrateEquality(w1, null));
	}

	@Test
	public void testNullConversionThrowsException() {
		assertThrows(NullPointerException.class,
				() -> QuantityMeasurementApp.demonstrateConversion(null, LengthUnit.FEET));

		assertThrows(NullPointerException.class,
				() -> QuantityMeasurementApp.demonstrateConversion(null, WeightUnit.KILOGRAM));
	}

	@Test
	public void testIncompatibleTypes() {
		Quantity<LengthUnit> length = new Quantity<>(1.0, LengthUnit.FEET);
		Quantity<WeightUnit> weight = new Quantity<>(1.0, WeightUnit.KILOGRAM);

		assertFalse(length.equals(weight));
	}
}