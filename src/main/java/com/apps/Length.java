package com.apps;

public class Length {
	private final double value;
	private final LengthUnit unit;
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

	private double convertToBaseUnit() {
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
		validate(value, targetUnit);

		double baseValue = convertToBaseUnit();
		double convertedValue = baseValue / targetUnit.getConversionFactor();

		return new Length(convertedValue, targetUnit);
	}

	/*
	 * adds Length object and returns with prior unit type
	 */
	public Length add(Length thatLength) {
		validate(this);
		validate(thatLength);

		double val1 = convertToBaseUnit();
		double val2 = thatLength.convertToBaseUnit();

		double sum = val1 + val2;

		double sumInUnit = sum / this.unit.getConversionFactor();

		return new Length(sumInUnit, this.unit);
	}

	/*
	 * adds raw values and returns sum
	 */
	public static Length add(double value1, LengthUnit unit1, double value2, LengthUnit unit2) {
		Length l1 = new Length(value1, unit1);
		Length l2 = new Length(value2, unit2);

		Length sum = l1.add(l2);
		return sum;
	}

	/*
	 * static method adds and returns sum with specific return type
	 */
	public static Length add(Length l1, Length l2, LengthUnit targetUnit) {
		validate(l1);
		validate(l2);
		validate(targetUnit);

		double sum = l1.convertToBaseUnit() + l2.convertToBaseUnit();
		double sumInTarget = sum / targetUnit.getConversionFactor();

		return new Length(sumInTarget, targetUnit);
	}

	/*
	 * adds Length object and returns sum with specific unit type
	 */
	public Length add(Length thatLength, LengthUnit targetUnit) {
		validate(thatLength);
		validate(targetUnit);

		Length sum = addAndConvert(thatLength, targetUnit);

		return sum;
	}

	private Length addAndConvert(Length thatLength, LengthUnit targetUnit) {
		validate(this);

		double sum = convertToBaseUnit() + thatLength.convertToBaseUnit();
		double sumInTarget = sum / targetUnit.getConversionFactor();

		return new Length(sumInTarget, targetUnit);
	}

	/*
	 * validates Length object
	 */
	public static void validate(Length length) {
		if (length == null) {
			throw new IllegalArgumentException("Length cannot be null");
		}
		if (!Double.isFinite(length.value)) {
			throw new IllegalArgumentException("Value must be finite");
		}
		if (length.unit == null) {
			throw new IllegalArgumentException("Target unit cannot be null");
		}
	}

	/*
	 * validates value and unit
	 */
	public static void validate(double value, LengthUnit unit) {
		if (!Double.isFinite(value)) {
			throw new IllegalArgumentException("Value must be finite");
		}

		if (unit == null) {
			throw new IllegalArgumentException("Target unit cannot be null");
		}
	}

	/*
	 * validates Unit type
	 */
	public static void validate(LengthUnit unit) {
		if (unit == null) {
			throw new IllegalArgumentException("Target unit cannot be null");
		}
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