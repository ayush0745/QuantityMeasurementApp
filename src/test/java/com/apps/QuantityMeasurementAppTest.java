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

	@Test
	public void testWeightEquality_SameUnit() {
		QuantityWeight w1 = new QuantityWeight(1.0, WeightUnit.KILOGRAM);
		QuantityWeight w2 = new QuantityWeight(1.0, WeightUnit.KILOGRAM);

		assertTrue(QuantityMeasurementApp.demonstrateWeightEquality(w1, w2));
	}

	@Test
	public void testWeightEquality_CrossUnit_KgToGram() {
		QuantityWeight w1 = new QuantityWeight(1.0, WeightUnit.KILOGRAM);
		QuantityWeight w2 = new QuantityWeight(1000.0, WeightUnit.GRAM);

		assertTrue(QuantityMeasurementApp.demonstrateWeightEquality(w1, w2));
	}

	@Test
	public void testWeightEquality_CrossUnit_KgToPound() {
		QuantityWeight w1 = new QuantityWeight(1.0, WeightUnit.KILOGRAM);
		QuantityWeight w2 = new QuantityWeight(2.20462, WeightUnit.POUND);

		assertTrue(QuantityMeasurementApp.demonstrateWeightEquality(w1, w2));
	}

	@Test
	public void testWeightEquality_NullHandling() {
		QuantityWeight w1 = new QuantityWeight(1.0, WeightUnit.KILOGRAM);
		assertFalse(QuantityMeasurementApp.demonstrateWeightEquality(w1, null));
	}

	@Test
	public void testWeightConversion_KgToGram() {
		QuantityWeight converted = QuantityMeasurementApp.demonstrateWeightConversion(1.0, WeightUnit.KILOGRAM,
				WeightUnit.GRAM);

		assertEquals(1000.0, converted.getValue(), EPSILON);
		assertEquals(WeightUnit.GRAM, converted.getUnit());
	}

	@Test
	public void testWeightConversion_PoundToKilogram() {
		QuantityWeight converted = QuantityMeasurementApp.demonstrateWeightConversion(2.20462, WeightUnit.POUND,
				WeightUnit.KILOGRAM);

		assertEquals(1.0, converted.getValue(), EPSILON);
		assertEquals(WeightUnit.KILOGRAM, converted.getUnit());
	}

	@Test
	public void testWeightConversion_RoundTrip() {
		QuantityWeight original = new QuantityWeight(1.5, WeightUnit.KILOGRAM);

		QuantityWeight converted = original.convertTo(WeightUnit.GRAM).convertTo(WeightUnit.KILOGRAM);

		assertEquals(1.5, converted.getValue(), EPSILON);
	}

	@Test
	public void testWeightAddition_SameUnit() {
		QuantityWeight w1 = new QuantityWeight(1.0, WeightUnit.KILOGRAM);
		QuantityWeight w2 = new QuantityWeight(2.0, WeightUnit.KILOGRAM);

		QuantityWeight sum = QuantityMeasurementApp.demonstrateWeightAddition(w1, w2);

		assertEquals(3.0, sum.getValue(), EPSILON);
		assertEquals(WeightUnit.KILOGRAM, sum.getUnit());
	}

	@Test
	public void testWeightAddition_CrossUnit_DefaultTarget() {
		QuantityWeight w1 = new QuantityWeight(1.0, WeightUnit.KILOGRAM);
		QuantityWeight w2 = new QuantityWeight(1000.0, WeightUnit.GRAM);

		QuantityWeight sum = QuantityMeasurementApp.demonstrateWeightAddition(w1, w2);

		assertEquals(2.0, sum.getValue(), EPSILON);
		assertEquals(WeightUnit.KILOGRAM, sum.getUnit());
	}

	@Test
	public void testWeightAddition_ExplicitTargetUnit() {
		QuantityWeight w1 = new QuantityWeight(1.0, WeightUnit.KILOGRAM);
		QuantityWeight w2 = new QuantityWeight(1000.0, WeightUnit.GRAM);

		QuantityWeight sum = QuantityMeasurementApp.demonstrateWeightAddition(w1, w2, WeightUnit.GRAM);

		assertEquals(2000.0, sum.getValue(), EPSILON);
		assertEquals(WeightUnit.GRAM, sum.getUnit());
	}

	@Test
	public void testWeightAddition_NegativeValues() {
		QuantityWeight w1 = new QuantityWeight(5.0, WeightUnit.KILOGRAM);
		QuantityWeight w2 = new QuantityWeight(-2000.0, WeightUnit.GRAM);

		QuantityWeight sum = QuantityMeasurementApp.demonstrateWeightAddition(w1, w2);

		assertEquals(3.0, sum.getValue(), EPSILON);
	}

	@Test
	public void testWeightVsLength_Incompatibility() {
		QuantityWeight weight = new QuantityWeight(1.0, WeightUnit.KILOGRAM);
		QuantityLength length = new QuantityLength(1.0, LengthUnit.FEET);

		assertFalse(weight.equals(length));
	}

	@Test
	public void testWeightNullConversionThrowsException() {
		assertThrows(IllegalArgumentException.class,
				() -> QuantityMeasurementApp.demonstrateWeightConversion(null, WeightUnit.KILOGRAM));
	}
}