package com.apps;
public class Length {
	private double value;
	private LengthUnit unit;
	private static final double EPSILON = 0.00001;

	public enum LengthUnit {
		FEET(12.0), INCHES(1.0), YARDS(36.0), CENTIMETERS(0.393701);

		private final double conversionFactor;

		private LengthUnit(double conversionFactor) {
		
			this.conversionFactor = conversionFactor;
		}

		public double getConversionFactor() {
			return conversionFactor;
		}
	}

	public Length(double value, LengthUnit unit) {
		if (unit == null) {
			throw new IllegalArgumentException("Unit cannot be null");
		}
		if (!Double.isFinite(value)) {
			throw new IllegalArgumentException("Value must be finite");
		}
		this.value = value;
		this.unit = unit;
	}

	public double convertToBaseUnit() {
		return value * unit.getConversionFactor();
	}

	public boolean compare(Length thatLength) {
		if (thatLength == null) {
			return false;
		}

		return Math.abs(this.convertToBaseUnit() - thatLength.convertToBaseUnit()) < EPSILON;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		Length other = (Length) o;
		return this.compare(other);
	}

	public Length convertTo(LengthUnit targetUnit) {
		if (targetUnit == null) {
			throw new IllegalArgumentException("Target unit cannot be null");
		}

		if (!Double.isFinite(value)) {
			throw new IllegalArgumentException("Value must be finite");
		}

		double baseValue = convertToBaseUnit();
		double convertedValue = baseValue / targetUnit.getConversionFactor();

		return new Length(convertedValue, targetUnit);
	}

	public Length add(Length thatLength) {
		if (!Double.isFinite(this.value) || !Double.isFinite(thatLength.value)) {
			throw new IllegalArgumentException("Value must be finite");
		}

		double val1 = convertToBaseUnit();
		double val2 = thatLength.convertToBaseUnit();

		double sum = val1 + val2;

		double sumInUnit = sum / this.unit.getConversionFactor();

		return new Length(sumInUnit, this.unit);
	}

	public static void add(double value1, LengthUnit unit1, double value2, LengthUnit unit2) {
		Length l1 = new Length(value1, unit1);
		Length l2 = new Length(value2, unit2);
	}

	public static Length add(Length l1, Length l2, LengthUnit targetUnit) {
		if (l1 == null || l2 == null) {
			throw new IllegalArgumentException("Length cannot be null");
		}
		if (targetUnit == null) {
			throw new IllegalArgumentException("Target unit cannot be null");
		}
		double sum = l1.convertToBaseUnit() + l2.convertToBaseUnit();
		double sumInTarget = sum / targetUnit.getConversionFactor();

		return new Length(sumInTarget, targetUnit);
	}

	@Override
	public String toString() {
		return String.format("%.2f %s", value, unit);
	}

	public static void main(String args[]) {
		Length length = new Length(1, LengthUnit.YARDS);
		System.out.println(length.convertTo(LengthUnit.FEET));
	}
}