package com.apps;

public class QuantityLength {
	private final double value;
	private final LengthUnit unit;
	private static final double EPSILON = 0.00001;

	public QuantityLength(double value, LengthUnit unit) {
		if (unit == null) {
			throw new IllegalArgumentException("Unit cannot be null");
		}
		if (!Double.isFinite(value)) {
			throw new IllegalArgumentException("Value must be finite");
		}
		this.value = value;
		this.unit = unit;
	}

	public double getValue() {
		return value;
	}

	public LengthUnit getUnit() {
		return unit;
	}

	public boolean compare(QuantityLength thatLength) {
		if (thatLength == null) {
			return false;
		}

		return Math.abs(unit.convertToBaseUnit(value) - thatLength.unit.convertToBaseUnit(thatLength.value)) < EPSILON;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		QuantityLength other = (QuantityLength) o;
		return this.compare(other);
	}

	public QuantityLength convertTo(LengthUnit targetUnit) {
		validate(targetUnit);

		double baseValue = unit.convertToBaseUnit(value);
		double convertedValue = targetUnit.convertFromBaseUnit(baseValue);

		return new QuantityLength(convertedValue, targetUnit);
	}

	/*
	 * adds Length object and returns with prior unit type
	 */
	public QuantityLength add(QuantityLength thatLength) {
		validate(thatLength);

		double val1 = unit.convertToBaseUnit(value);
		double val2 = thatLength.unit.convertToBaseUnit(thatLength.value);

		double sum = val1 + val2;

		double sumInUnit = unit.convertFromBaseUnit(sum);

		return new QuantityLength(sumInUnit, this.unit);
	}

	/*
	 * adds Length object and returns sum with specific unit type
	 */
	public QuantityLength add(QuantityLength thatLength, LengthUnit targetUnit) {
		validate(thatLength);
		validate(targetUnit);

		return addAndConvert(thatLength, targetUnit);
	}

	private QuantityLength addAndConvert(QuantityLength thatLength, LengthUnit targetUnit) {
		double sum = unit.convertToBaseUnit(value) + thatLength.unit.convertToBaseUnit(thatLength.value);
		double sumInTarget = targetUnit.convertFromBaseUnit(sum);

		return new QuantityLength(sumInTarget, targetUnit);
	}

	/*
	 * validates Length object
	 */
	public void validate(QuantityLength length) {
		if (length == null) {
			throw new IllegalArgumentException("Length cannot be null");
		}
	}

	/*
	 * validates Unit type
	 */
	public void validate(LengthUnit unit) {
		if (unit == null) {
			throw new IllegalArgumentException("Target unit cannot be null");
		}
	}

	@Override
	public String toString() {
		return String.format("%.2f %s", value, unit);
	}

	public static void main(String args[]) {
		QuantityLength length = new QuantityLength(1, LengthUnit.YARDS);
		System.out.println(length.convertTo(LengthUnit.FEET));
	}
}