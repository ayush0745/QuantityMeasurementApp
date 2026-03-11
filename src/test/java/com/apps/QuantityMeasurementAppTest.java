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

		Quantity<VolumeUnit> v1 = new Quantity<>(1.0, VolumeUnit.LITRE);
		Quantity<VolumeUnit> v2 = new Quantity<>(0.264172, VolumeUnit.GALLON);
		assertTrue(QuantityMeasurementApp.demonstrateEquality(v1, v2));
	}

	@Test
	public void testComparisonUsingValues() {
		assertTrue(QuantityMeasurementApp.demonstrateComparison(3.0, LengthUnit.FEET, 1.0, LengthUnit.YARDS));

		assertTrue(QuantityMeasurementApp.demonstrateComparison(1.0, WeightUnit.KILOGRAM, 1000.0, WeightUnit.GRAM));

		assertTrue(QuantityMeasurementApp.demonstrateComparison(1.0, VolumeUnit.LITRE, 1000.0, VolumeUnit.MILLILITRE));
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

		Quantity<VolumeUnit> volume = new Quantity<>(1.0, VolumeUnit.LITRE);
		Quantity<VolumeUnit> convertedVolume = QuantityMeasurementApp.demonstrateConversion(volume,
				VolumeUnit.MILLILITRE);
		assertEquals(1000.0, convertedVolume.getValue(), EPSILON); // FIXED
		assertEquals(VolumeUnit.MILLILITRE, convertedVolume.getUnit());
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

		Quantity<VolumeUnit> v1 = new Quantity<>(1.0, VolumeUnit.LITRE);
		Quantity<VolumeUnit> v2 = new Quantity<>(1000.0, VolumeUnit.MILLILITRE);

		Quantity<VolumeUnit> sumVolume = QuantityMeasurementApp.demonstrateAddition(v1, v2);
		assertEquals(2.0, sumVolume.getValue(), EPSILON);
		assertEquals(VolumeUnit.LITRE, sumVolume.getUnit());
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

		Quantity<VolumeUnit> v1 = new Quantity<>(1.0, VolumeUnit.LITRE);
		Quantity<VolumeUnit> v2 = new Quantity<>(1000.0, VolumeUnit.MILLILITRE);

		Quantity<VolumeUnit> sumVolume = QuantityMeasurementApp.demonstrateAddition(v1, v2, VolumeUnit.MILLILITRE);
		assertEquals(2000.0, sumVolume.getValue(), EPSILON);
		assertEquals(VolumeUnit.MILLILITRE, sumVolume.getUnit());
	}

	@Test
	public void testSubtractionDefaultUnit() {
		Quantity<LengthUnit> l1 = new Quantity<>(10.0, LengthUnit.FEET);
		Quantity<LengthUnit> l2 = new Quantity<>(6.0, LengthUnit.INCHES);

		Quantity<LengthUnit> result = l1.subtract(l2);

		assertEquals(9.5, result.getValue(), EPSILON);
		assertEquals(LengthUnit.FEET, result.getUnit());
	}

	@Test
	public void testSubtractionWithTargetUnit() {
		Quantity<LengthUnit> l1 = new Quantity<>(10.0, LengthUnit.FEET);
		Quantity<LengthUnit> l2 = new Quantity<>(6.0, LengthUnit.INCHES);

		Quantity<LengthUnit> result = l1.subtract(l2, LengthUnit.INCHES);

		assertEquals(114.0, result.getValue(), EPSILON);
		assertEquals(LengthUnit.INCHES, result.getUnit());
	}

	@Test
	public void testDivisionDefaultUnit() {
		Quantity<LengthUnit> l1 = new Quantity<>(24.0, LengthUnit.INCHES);
		Quantity<LengthUnit> l2 = new Quantity<>(2.0, LengthUnit.FEET);

		double result = l1.divide(l2);

		assertEquals(1.0, result, EPSILON);
	}

	@Test
	public void testDivisionByZero() {
		Quantity<LengthUnit> l1 = new Quantity<>(10.0, LengthUnit.FEET);
		Quantity<LengthUnit> l2 = new Quantity<>(0.0, LengthUnit.FEET);

		assertThrows(ArithmeticException.class, () -> l1.divide(l2));
	}

	@Test
	public void testIncompatibleTypes() {
		Quantity<LengthUnit> length = new Quantity<>(1.0, LengthUnit.FEET);
		Quantity<WeightUnit> weight = new Quantity<>(1.0, WeightUnit.KILOGRAM);
		Quantity<VolumeUnit> volume = new Quantity<>(1.0, VolumeUnit.LITRE);

		assertFalse(length.equals(weight));
		assertFalse(weight.equals(volume));
	}
}