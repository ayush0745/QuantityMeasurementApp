package com.apps;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class QuantityMeasurementAppTest {

	@Test
	void testFeetEquality() {
		Length l1 = new Length(1.0, Length.LengthUnit.FEET);
		Length l2 = new Length(1.0, Length.LengthUnit.FEET);
		assertEquals(l1, l2);
	}

	@Test
	void testInchesEquality() {
		Length l1 = new Length(12.0, Length.LengthUnit.INCHES);
		Length l2 = new Length(12.0, Length.LengthUnit.INCHES);
		assertEquals(l1, l2);
	}

	@Test
	void testFeetInchesComparison() {
		Length feet = new Length(1.0, Length.LengthUnit.FEET);
		Length inches = new Length(12.0, Length.LengthUnit.INCHES);
		assertEquals(feet, inches);
	}

	@Test
	void testFeetInequality() {
		Length l1 = new Length(1.0, Length.LengthUnit.FEET);
		Length l2 = new Length(2.0, Length.LengthUnit.FEET);
		assertNotEquals(l1, l2);
	}

	@Test
	void testInchesInequality() {
		Length l1 = new Length(1.0, Length.LengthUnit.INCHES);
		Length l2 = new Length(2.0, Length.LengthUnit.INCHES);
		assertNotEquals(l1, l2);
	}

	@Test
	void testCrossUnitInequality() {
		Length feet = new Length(1.0, Length.LengthUnit.FEET);
		Length inches = new Length(10.0, Length.LengthUnit.INCHES);
		assertNotEquals(feet, inches);
	}

	@Test
	void testMultipleFeetComparison() {
		Length feet = new Length(2.0, Length.LengthUnit.FEET);
		Length inches = new Length(24.0, Length.LengthUnit.INCHES);
		assertEquals(feet, inches);
	}

	@Test
	void testYardToYard_SameValue() {
		Length l1 = new Length(1.0, Length.LengthUnit.YARDS);
		Length l2 = new Length(1.0, Length.LengthUnit.YARDS);
		assertEquals(l1, l2);
	}

	@Test
	void testYardToYard_DifferentValue() {
		Length l1 = new Length(1.0, Length.LengthUnit.YARDS);
		Length l2 = new Length(2.0, Length.LengthUnit.YARDS);
		assertNotEquals(l1, l2);
	}

	@Test
	void testYardToFeet_EquivalentValue() {
		Length yard = new Length(1.0, Length.LengthUnit.YARDS);
		Length feet = new Length(3.0, Length.LengthUnit.FEET);
		assertEquals(yard, feet);
	}

	@Test
	void testFeetToYard_EquivalentValue() {
		Length feet = new Length(3.0, Length.LengthUnit.FEET);
		Length yard = new Length(1.0, Length.LengthUnit.YARDS);
		assertEquals(feet, yard);
	}

	@Test
	void testYardToInches_EquivalentValue() {
		Length yard = new Length(1.0, Length.LengthUnit.YARDS);
		Length inches = new Length(36.0, Length.LengthUnit.INCHES);
		assertEquals(yard, inches);
	}

	@Test
	void testInchesToYard_EquivalentValue() {
		Length inches = new Length(36.0, Length.LengthUnit.INCHES);
		Length yard = new Length(1.0, Length.LengthUnit.YARDS);
		assertEquals(inches, yard);
	}

	@Test
	void testYardToFeet_NonEquivalentValue() {
		Length yard = new Length(1.0, Length.LengthUnit.YARDS);
		Length feet = new Length(2.0, Length.LengthUnit.FEET);
		assertNotEquals(yard, feet);
	}

	@Test
	void testCentimeterToCentimeter_SameValue() {
		Length l1 = new Length(2.0, Length.LengthUnit.CENTIMETERS);
		Length l2 = new Length(2.0, Length.LengthUnit.CENTIMETERS);
		assertEquals(l1, l2);
	}

	@Test
	void testCentimeterToInches_EquivalentValue() {
		Length cm = new Length(1.0, Length.LengthUnit.CENTIMETERS);
		Length inch = new Length(0.393701, Length.LengthUnit.INCHES);
		assertEquals(cm, inch);
	}

	@Test
	void testCentimeterToFeet_NonEquivalentValue() {
		Length cm = new Length(1.0, Length.LengthUnit.CENTIMETERS);
		Length feet = new Length(1.0, Length.LengthUnit.FEET);
		assertNotEquals(cm, feet);
	}

	@Test
	void testCentimeterToFeet_EquivalentValue() {
		Length cm = new Length(30.48, Length.LengthUnit.CENTIMETERS);
		Length feet = new Length(1.0, Length.LengthUnit.FEET);
		assertEquals(cm, feet);
	}

	@Test
	void testReflexiveProperty() {
		Length yard = new Length(1.0, Length.LengthUnit.YARDS);
		assertEquals(yard, yard);
	}

	@Test
	void testSymmetricProperty() {
		Length yard = new Length(1.0, Length.LengthUnit.YARDS);
		Length feet = new Length(3.0, Length.LengthUnit.FEET);

		assertEquals(yard, feet);
		assertEquals(feet, yard);
	}

	@Test
	void testTransitiveProperty() {
		Length yard = new Length(1.0, Length.LengthUnit.YARDS);
		Length feet = new Length(3.0, Length.LengthUnit.FEET);
		Length inches = new Length(36.0, Length.LengthUnit.INCHES);

		assertEquals(yard, feet);
		assertEquals(feet, inches);
		assertEquals(yard, inches);
	}

	@Test
	void testNullComparison() {
		Length yard = new Length(1.0, Length.LengthUnit.YARDS);
		assertNotEquals(yard, null);
	}

	@Test
	void testSameReference() {
		Length cm = new Length(5.0, Length.LengthUnit.CENTIMETERS);
		Length sameRef = cm;
		assertEquals(cm, sameRef);
	}
}