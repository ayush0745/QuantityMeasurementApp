package com.apps;

public class QuantityMeasurementApp {
	public static boolean demonstrateLengthEquality(QuantityLength length1, QuantityLength length2) {
		return length1.equals(length2);
	}

	public static boolean demonstrateLengthComparison(double value1, LengthUnit unit1, double value2,
			LengthUnit unit2) {

		QuantityLength length1 = new QuantityLength(value1, unit1);
		QuantityLength length2 = new QuantityLength(value2, unit2);

		boolean result = length1.equals(length2);

		System.out.println("Comparison Result: " + result);
		return result;
	}

	public static QuantityLength demonstrateLengthConversion(double value, LengthUnit fromUnit, LengthUnit toUnit) {
		QuantityLength length = new QuantityLength(value, fromUnit);
		QuantityLength converted = length.convertTo(toUnit);
		System.out.println("Converted " + length + " to " + converted);
		return converted;
	}

	public static QuantityLength demonstrateLengthConversion(QuantityLength length, LengthUnit toUnit) {
		if (length == null) {
			throw new IllegalArgumentException("Length cannot be null");
		}
		QuantityLength converted = length.convertTo(toUnit);
		System.out.println("Converted " + length + " to " + converted);
		return converted;
	}

	public static QuantityLength demonstrateLengthAddition(QuantityLength length1, QuantityLength length2) {
		QuantityLength sum = length1.add(length2);
		System.out.println(length1 + " + " + length2 + " = " + sum);
		return sum;
	}

	public static QuantityLength demonstrateLengthAddition(QuantityLength length1, QuantityLength length2,
			LengthUnit targetUnit) {
		QuantityLength sum = length1.add(length2, targetUnit);
		System.out.println(length1 + " + " + length2 + " = " + sum);
		return sum;
	}

	public static void main(String args[]) {
		demonstrateLengthComparison(1.0, LengthUnit.FEET, 12.0, LengthUnit.INCHES);
		demonstrateLengthComparison(1.0, LengthUnit.YARDS, 36.0, LengthUnit.INCHES);
		demonstrateLengthComparison(100.0, LengthUnit.CENTIMETERS, 39.3701, LengthUnit.INCHES);
		demonstrateLengthComparison(3.0, LengthUnit.FEET, 1.0, LengthUnit.YARDS);
		demonstrateLengthComparison(30.48, LengthUnit.CENTIMETERS, 1.0, LengthUnit.FEET);

		demonstrateLengthConversion(1.0, LengthUnit.FEET, LengthUnit.INCHES);

		QuantityLength lengthInYards = new QuantityLength(2.0, LengthUnit.YARDS);

		demonstrateLengthConversion(lengthInYards, LengthUnit.FEET);

		demonstrateLengthAddition(lengthInYards, new QuantityLength(3.0, LengthUnit.FEET));

		demonstrateLengthAddition(lengthInYards, new QuantityLength(3.0, LengthUnit.FEET), LengthUnit.INCHES);
	}
}