package com.apps;

public class QuantityMeasurementApp {
	public static boolean demonstrateLengthEquality(Length length1, Length length2) {
		if (length1 == null || length2 == null) {
			System.out.println("Cannot compare null values.");
			return false;
		}
		return length1.equals(length2);
	}

	public static boolean demonstrateLengthComparison(double value1, Length.LengthUnit unit1, double value2,
			Length.LengthUnit unit2) {
		Length length1 = new Length(value1, unit1);
		Length length2 = new Length(value2, unit2);
		if (length1 == null || length2 == null) {
			System.out.println("Cannot compare null values.");
			return false;
		}

		boolean result = length1.compare(length2);
		System.out.println("Comparison Result: " + result);
		return result;
	}

	public static Length demonstrateLengthConversion(double value, Length.LengthUnit fromUnit,
			Length.LengthUnit toUnit) {
		Length length = new Length(value, fromUnit);
		Length converted = length.convertTo(toUnit);
		System.out.println("Converted " + length + " to " + converted);
		return converted;
	}

	public static Length demonstrateLengthConversion(Length length, Length.LengthUnit toUnit) {
		if (length == null) {
			throw new IllegalArgumentException("Length cannot be null");
		}
		Length converted = length.convertTo(toUnit);
		System.out.println("Converted " + length + " to " + converted);
		return converted;
	}

	public static void main(String args[]) {
		demonstrateLengthComparison(4.0, Length.LengthUnit.FEET, 12.0, Length.LengthUnit.INCHES);
		demonstrateLengthComparison(2.0, Length.LengthUnit.YARDS, 36.0, Length.LengthUnit.INCHES);
		demonstrateLengthComparison(200.0, Length.LengthUnit.CENTIMETERS, 39.3701, Length.LengthUnit.INCHES);
		demonstrateLengthComparison(3.0, Length.LengthUnit.FEET, 1.0, Length.LengthUnit.YARDS);
		demonstrateLengthComparison(30.48, Length.LengthUnit.CENTIMETERS, 1.0, Length.LengthUnit.FEET);
		demonstrateLengthConversion(1.0, Length.LengthUnit.FEET, Length.LengthUnit.INCHES);
		Length lengthInYards = new Length(2.0, Length.LengthUnit.YARDS);
		demonstrateLengthConversion(lengthInYards, Length.LengthUnit.FEET);
	}
}