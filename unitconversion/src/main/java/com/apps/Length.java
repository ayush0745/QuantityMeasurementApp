package com.apps;

public class Length {
	private double value;
	private LengthUnit unit;
	private static final double EPSILON = 0.00001;

	//length enum
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
		if (!Double.isFinite(value)) {
			throw new IllegalArgumentException(" Value must be a finite number");
		}
		if (unit == null) {
			throw new IllegalArgumentException(" Unit cannot be null");
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

		if (!Double.isFinite(this.value)) {
			throw new IllegalArgumentException("Value must be a finite number");
		}

		double baseValue = convertToBaseUnit();
		double convertedValue = baseValue / targetUnit.getConversionFactor();

		return new Length(convertedValue, targetUnit);
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