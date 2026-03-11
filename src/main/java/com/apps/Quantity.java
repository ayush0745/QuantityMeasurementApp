package com.apps;

public class Quantity<U extends IMeasurable> {
	private final double value;
	private final U unit;
	private static final double EPSILON = 0.00001;

	public Quantity(double value, U unit) {
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

	public U getUnit() {
		return unit;
	}

	public boolean compare(Quantity<?> other) {
		if (other == null) {
			throw new IllegalArgumentException("Other quantity cannot be null");
		}

		if (!unit.getClass().equals(other.unit.getClass())) {
			return false;
		}

		double base = unit.convertToBaseUnit(value);
		double otherBase = other.unit.convertToBaseUnit(other.value);

		return Math.abs(base - otherBase) < EPSILON;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		Quantity<?> other = (Quantity<?>) o;

		return this.compare(other);
	}

	public Quantity<U> convertTo(U targetUnit) {
		validate(targetUnit);

		double baseValue = unit.convertToBaseUnit(value);
		double convertedValue = targetUnit.convertFromBaseUnit(baseValue);

		return new Quantity<U>(convertedValue, targetUnit);
	}

	/*
	 * adds quantities and returns with prior unit type
	 */
	public Quantity<U> add(Quantity<U> other) {
		validate(other);
		return add(other, this.unit);
	}

	/*
	 * adds Length object and returns sum with specific unit type
	 */
	public Quantity<U> add(Quantity<U> other, U targetUnit) {
		validate(other);
		validate(targetUnit);
		return addAndConvert(other, targetUnit);
	}

	private Quantity<U> addAndConvert(Quantity<U> other, U targetUnit) {
		double sum = unit.convertToBaseUnit(value) + other.unit.convertToBaseUnit(other.value);
		double sumInTarget = targetUnit.convertFromBaseUnit(sum);

		return new Quantity<U>(sumInTarget, targetUnit);
	}

	/*
	 * validates Length object
	 */
	public void validate(Quantity<U> length) {
		if (length == null) {
			throw new IllegalArgumentException("Length cannot be null");
		}
	}

	/*
	 * validates Unit type
	 */
	public void validate(U unit) {
		if (unit == null) {
			throw new IllegalArgumentException("Target unit cannot be null");
		}
		if (this.unit.getClass() != unit.getClass()) {
			throw new IllegalArgumentException("Cannot convert between different unit categories");
		}
	}

	@Override
	public String toString() {
		return String.format("%.2f %s", value, unit);
	}
}