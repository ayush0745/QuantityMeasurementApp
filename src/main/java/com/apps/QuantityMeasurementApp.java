package com.apps;

public class QuantityMeasurementApp {

	 public static <U extends IMeasurable> boolean demonstrateEquality(Quantity<U> q1, Quantity<U> q2) {
		boolean result = q1.equals(q2);
		System.out.println(q1 + " equals " + q2 + "? " + result);
		return result;
	}

	public static <U extends IMeasurable> boolean demonstrateComparison(double value1, U unit1, double value2,
			U unit2) {
		Quantity<U> q1 = new Quantity<>(value1, unit1);
		Quantity<U> q2 = new Quantity<>(value2, unit2);

		boolean result = demonstrateEquality(q1, q2);
		System.out.println("Comparison Result: " + result);
		return result;
	}

	public static <U extends IMeasurable> Quantity<U> demonstrateConversion(Quantity<U> quantity, U targetUnit) {
		Quantity<U> converted = quantity.convertTo(targetUnit);
		System.out.println("Converted " + quantity + " to " + converted);
		return converted;
	}

	public static <U extends IMeasurable> Quantity<U> demonstrateAddition(Quantity<U> q1, Quantity<U> q2) {
		Quantity<U> sum = q1.add(q2);
		System.out.println(q1 + " + " + q2 + " = " + sum);
		return sum;
	}

	public static <U extends IMeasurable> Quantity<U> demonstrateAddition(Quantity<U> q1, Quantity<U> q2,
			U targetUnit) {
		Quantity<U> sum = q1.add(q2, targetUnit);
		System.out.println(q1 + " + " + q2 + " = " + sum);
		return sum;
	}

	public static <U extends IMeasurable> Quantity<U> demonstrateSubtraction(Quantity<U> q1, Quantity<U> q2) {
		Quantity<U> result = q1.subtract(q2);
		System.out.println(q1 + " - " + q2 + " = " + result);
		return result;
	}

	public static <U extends IMeasurable> Quantity<U> demonstrateSubtraction(Quantity<U> q1, Quantity<U> q2,
			U targetUnit) {

		Quantity<U> result = q1.subtract(q2, targetUnit);
		System.out.println(q1 + " - " + q2 + " = " + result);
		return result;
	}

	public static <U extends IMeasurable> double demonstrateDivision(Quantity<U> q1, Quantity<U> q2) {

		double result = q1.divide(q2);
		System.out.println(q1 + " ÷ " + q2 + " = " + result);
		return result;
	}

	public static void main(String[] args) {

		demonstrateComparison(1.0, LengthUnit.FEET, 12.0, LengthUnit.INCHES);
		demonstrateComparison(3.0, LengthUnit.FEET, 1.0, LengthUnit.YARDS);

		Quantity<LengthUnit> length1 = new Quantity<>(2.0, LengthUnit.YARDS);
		Quantity<LengthUnit> length2 = new Quantity<>(3.0, LengthUnit.FEET);

		demonstrateConversion(length1, LengthUnit.FEET);
		demonstrateAddition(length1, length2);
		demonstrateAddition(length1, length2, LengthUnit.INCHES);

		demonstrateSubtraction(length1, length2);
		demonstrateSubtraction(length1, length2, LengthUnit.INCHES);

		demonstrateDivision(length1, length2);

		demonstrateComparison(1.0, WeightUnit.KILOGRAM, 1000.0, WeightUnit.GRAM);
		demonstrateComparison(1.0, WeightUnit.POUND, 453.592, WeightUnit.GRAM);

		Quantity<WeightUnit> weight1 = new Quantity<>(1.0, WeightUnit.KILOGRAM);
		Quantity<WeightUnit> weight2 = new Quantity<>(1000.0, WeightUnit.GRAM);

		demonstrateConversion(weight1, WeightUnit.GRAM);
		demonstrateAddition(weight1, weight2);
		demonstrateAddition(weight1, weight2, WeightUnit.KILOGRAM);

		demonstrateSubtraction(weight1, weight2);
		demonstrateSubtraction(weight1, weight2, WeightUnit.GRAM);

		demonstrateDivision(weight1, weight2);

		demonstrateComparison(1.0, VolumeUnit.LITRE, 1000.0, VolumeUnit.MILLILITRE);
		demonstrateComparison(1.0, VolumeUnit.LITRE, 0.264172, VolumeUnit.GALLON);

		Quantity<VolumeUnit> volume1 = new Quantity<>(1.0, VolumeUnit.LITRE);
		Quantity<VolumeUnit> volume2 = new Quantity<>(1000.0, VolumeUnit.MILLILITRE);

		demonstrateConversion(volume1, VolumeUnit.GALLON);
		demonstrateAddition(volume1, volume2);
		demonstrateAddition(volume1, volume2, VolumeUnit.GALLON);

		demonstrateSubtraction(volume1, volume2);
		demonstrateSubtraction(volume1, volume2, VolumeUnit.MILLILITRE);

		demonstrateDivision(volume1, volume2);
	}
}