package com.apps;

public class QuantityMeasurementApp {

	public static boolean demonstrateWeightEquality(QuantityWeight weight1, QuantityWeight weight2) {
		return weight1.equals(weight2);
	}

	public static boolean demonstrateWeightComparison(double value1, WeightUnit unit1, double value2,
			WeightUnit unit2) {

		QuantityWeight weight1 = new QuantityWeight(value1, unit1);
		QuantityWeight weight2 = new QuantityWeight(value2, unit2);

		boolean result = weight1.equals(weight2);

		System.out.println("Comparison Result: " + result);
		return result;
	}

	public static QuantityWeight demonstrateWeightConversion(double value, WeightUnit fromUnit, WeightUnit toUnit) {
		QuantityWeight weight = new QuantityWeight(value, fromUnit);
		QuantityWeight converted = weight.convertTo(toUnit);
		System.out.println("Converted " + weight + " to " + converted);
		return converted;
	}

	public static QuantityWeight demonstrateWeightConversion(QuantityWeight weight, WeightUnit toUnit) {
		if (weight == null) {
			throw new IllegalArgumentException("Weight cannot be null");
		}
		QuantityWeight converted = weight.convertTo(toUnit);
		System.out.println("Converted " + weight + " to " + converted);
		return converted;
	}

	public static QuantityWeight demonstrateWeightAddition(QuantityWeight weight1, QuantityWeight weight2) {
		QuantityWeight sum = weight1.add(weight2);
		System.out.println(weight1 + " + " + weight2 + " = " + sum);
		return sum;
	}

	public static QuantityWeight demonstrateWeightAddition(QuantityWeight weight1, QuantityWeight weight2,
			WeightUnit targetUnit) {
		QuantityWeight sum = weight1.add(weight2, targetUnit);
		System.out.println(weight1 + " + " + weight2 + " = " + sum);
		return sum;
	}

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

		demonstrateWeightComparison(1.0, WeightUnit.KILOGRAM, 1.0, WeightUnit.KILOGRAM);
		demonstrateWeightComparison(1.0, WeightUnit.KILOGRAM, 1000.0, WeightUnit.GRAM);
		demonstrateWeightComparison(2.0, WeightUnit.POUND, 2.0, WeightUnit.POUND);
		demonstrateWeightComparison(1.0, WeightUnit.KILOGRAM, 2.20462, WeightUnit.POUND);
		demonstrateWeightComparison(500.0, WeightUnit.GRAM, 0.5, WeightUnit.KILOGRAM);
		demonstrateWeightComparison(1.0, WeightUnit.POUND, 453.592, WeightUnit.GRAM);

		demonstrateWeightConversion(1.0, WeightUnit.KILOGRAM, WeightUnit.GRAM);
		demonstrateWeightConversion(2.0, WeightUnit.POUND, WeightUnit.KILOGRAM);
		demonstrateWeightConversion(500.0, WeightUnit.GRAM, WeightUnit.POUND);
		demonstrateWeightConversion(0.0, WeightUnit.KILOGRAM, WeightUnit.GRAM);

		demonstrateWeightAddition(new QuantityWeight(1.0, WeightUnit.KILOGRAM),
				new QuantityWeight(2.0, WeightUnit.KILOGRAM));

		demonstrateWeightAddition(new QuantityWeight(1.0, WeightUnit.KILOGRAM),
				new QuantityWeight(1000.0, WeightUnit.GRAM));

		demonstrateWeightAddition(new QuantityWeight(500.0, WeightUnit.GRAM),
				new QuantityWeight(0.5, WeightUnit.KILOGRAM));

		demonstrateWeightAddition(new QuantityWeight(1.0, WeightUnit.KILOGRAM),
				new QuantityWeight(1000.0, WeightUnit.GRAM), WeightUnit.GRAM);

		demonstrateWeightAddition(new QuantityWeight(1.0, WeightUnit.POUND),
				new QuantityWeight(453.592, WeightUnit.GRAM), WeightUnit.POUND);

		demonstrateWeightAddition(new QuantityWeight(2.0, WeightUnit.KILOGRAM),
				new QuantityWeight(4.0, WeightUnit.POUND), WeightUnit.KILOGRAM);

		QuantityWeight weight = new QuantityWeight(1.0, WeightUnit.KILOGRAM);
		QuantityLength length = new QuantityLength(1.0, LengthUnit.FEET);

		System.out.println("Weight equals Length? " + weight.equals(length));
	}
}