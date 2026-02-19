package com.apps;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class QuantityMeasurementAppTest {

	@Test
	public void testFeetEquality() {
		Length l1 = new Length(1.0, Length.LengthUnit.FEET);
		Length l2 = new Length(1.0, Length.LengthUnit.FEET);
		assertEquals(l1, l2, "1 foot should equal 1 foot");
	}

	@Test
	public void testInchesEquality() {
		Length l1 = new Length(12.0, Length.LengthUnit.INCHES);
		Length l2 = new Length(12.0, Length.LengthUnit.INCHES);
		assertEquals(l1, l2, "12 inches should equal 12 inches");
	}

	@Test
	public void testFeetInchesComparison() {
		Length feet = new Length(1.0, Length.LengthUnit.FEET);
		Length inches = new Length(12.0, Length.LengthUnit.INCHES);
		assertEquals(feet, inches, "1 foot should equal 12 inches");
	}

	@Test
	public void testFeetInequality() {
		Length l1 = new Length(1.0, Length.LengthUnit.FEET);
		Length l2 = new Length(2.0, Length.LengthUnit.FEET);
		assertNotEquals(l1, l2, "1 foot should not equal 2 feet");
	}

	@Test
	public void testInchesInequality() {
		Length l1 = new Length(1.0, Length.LengthUnit.INCHES);
		Length l2 = new Length(2.0, Length.LengthUnit.INCHES);
		assertNotEquals(l1, l2, "1 inch should not equal 2 inches");
	}

	@Test
	public void testCrossUnitInequality() {
		Length feet = new Length(1.0, Length.LengthUnit.FEET);
		Length inches = new Length(10.0, Length.LengthUnit.INCHES);
		assertNotEquals(feet, inches, "1 foot should not equal 10 inches");
	}

	@Test
	public void testMultipleFeetComparison() {
		Length feet = new Length(2.0, Length.LengthUnit.FEET);
		Length inches = new Length(24.0, Length.LengthUnit.INCHES);
		assertEquals(feet, inches, "2 feet should equal 24 inches");
	}
}