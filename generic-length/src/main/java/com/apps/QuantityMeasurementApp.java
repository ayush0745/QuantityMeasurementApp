package com.apps;

public class QuantityMeasurementApp {
	public static boolean demonstrateLengthEquality(Length length1, Length length2) {
		return length1.equals(length2);
	}

	public static boolean demonstrateFeetEquality() {
		Length l1 = new Length(1.0, Length.LengthUnit.FEET);
		Length l2 = new Length(1.0, Length.LengthUnit.FEET);

		boolean result = demonstrateLengthEquality(l1, l2);

		System.out.println("Feet to Feet Equality (1 ft == 1 ft): " + result);
		return result;
	}

	public static boolean demonstrateInchesEquality() {

		Length l1 = new Length(12.0, Length.LengthUnit.INCHES);
		Length l2 = new Length(12.0, Length.LengthUnit.INCHES);

		boolean result = demonstrateLengthEquality(l1, l2);

		System.out.println("Inches to Inches Equality (12 in == 12 in): " + result);
		return result;
	}

	public static void demonstrateFeetInchesComparison() {

		Length feet = new Length(1.0, Length.LengthUnit.FEET);
		Length inches = new Length(12.0, Length.LengthUnit.INCHES);

		boolean result = demonstrateLengthEquality(feet, inches);

		System.out.println("Feet to Inches Equality (1 ft == 12 in): " + result);
	}

	public static void main(String args[]) {
		demonstrateFeetEquality();
		demonstrateInchesEquality();
		demonstrateFeetInchesComparison();
	}
}