package com.apps;

public class QuantityWeight {
	private final double value;
	private final WeightUnit unit;
	private static final double EPSILON = 0.00001;

	public QuantityWeight(double value, WeightUnit unit) {
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

	public WeightUnit getUnit() {
		return unit;
	}

	public boolean compare(QuantityWeight thatWeight) {
		if (thatWeight == null) {
			return false;
		}

		return Math.abs(unit.convertToBaseUnit(value) - thatWeight.unit.convertToBaseUnit(thatWeight.value)) < EPSILON;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		QuantityWeight other = (QuantityWeight) o;
		return this.compare(other);
	}

	public QuantityWeight convertTo(WeightUnit targetUnit) {
		validate(targetUnit);

		double baseValue = unit.convertToBaseUnit(value);
		double convertedValue = targetUnit.convertFromBaseUnit(baseValue);

		return new QuantityWeight(convertedValue, targetUnit);
	}

	/*
	 * adds Weight object and returns with prior unit type
	 */
	public QuantityWeight add(QuantityWeight thatWeight) {
		validate(thatWeight);

		double val1 = unit.convertToBaseUnit(value);
		double val2 = thatWeight.unit.convertToBaseUnit(thatWeight.value);

		double sum = val1 + val2;

		double sumInUnit = unit.convertFromBaseUnit(sum);

		return new QuantityWeight(sumInUnit, this.unit);
	}

	/*
	 * adds Weight object and returns sum with specific unit type
	 */
	public QuantityWeight add(QuantityWeight thatWeight, WeightUnit targetUnit) {
		validate(thatWeight);
		validate(targetUnit);

		return addAndConvert(thatWeight, targetUnit);
	}

	private QuantityWeight addAndConvert(QuantityWeight thatWeight, WeightUnit targetUnit) {
		double sum = unit.convertToBaseUnit(value) + thatWeight.unit.convertToBaseUnit(thatWeight.value);
		double sumInTarget = targetUnit.convertFromBaseUnit(sum);

		return new QuantityWeight(sumInTarget, targetUnit);
	}

	/*
	 * validates Weight object
	 */
	public void validate(QuantityWeight Weight) {
		if (Weight == null) {
			throw new IllegalArgumentException("Weight cannot be null");
		}
	}

	/*
	 * validates Unit type
	 */
	public void validate(WeightUnit unit) {
		if (unit == null) {
			throw new IllegalArgumentException("Target unit cannot be null");
		}
	}

	@Override
	public String toString() {
		return String.format("%.2f %s", value, unit);
	}
}