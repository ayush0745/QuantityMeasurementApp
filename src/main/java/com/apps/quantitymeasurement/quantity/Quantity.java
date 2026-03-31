package com.apps.quantitymeasurement.quantity;

import com.apps.quantitymeasurement.unit.IMeasurable;

public class Quantity<U extends IMeasurable> {

	private final double value;
	private final U unit;

	public Quantity(double value, U unit) {
		if (unit == null)
			throw new IllegalArgumentException("Unit cannot be null");

		if (!Double.isFinite(value))
			throw new IllegalArgumentException("Value must be finite");

		this.value = value;
		this.unit = unit;
	}

	public double getValue() {
		return value;
	}

	public U getUnit() {
		return unit;
	}

	@Override
	public String toString() {
		return String.format("%.2f %s", value, unit.getUnitName());
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Quantity<?> other))
			return false;
		return Double.compare(value, other.value) == 0 && unit.equals(other.unit);
	}
}