package com.apps;

import java.util.function.DoubleBinaryOperator;

public class Quantity<U extends IMeasurable> {

	private enum ArithmeticOperation {
		ADD((a, b) -> a + b), SUBTRACT((a, b) -> a - b), DIVIDE((a, b) -> {
			if (b == 0.0) {
				throw new ArithmeticException("Cannot divide by 0");
			}
			return a / b;
		});

		private final DoubleBinaryOperator operation;

		private ArithmeticOperation(DoubleBinaryOperator operation) {
			this.operation = operation;
		}

		public double compute(double a, double b) {
			return operation.applyAsDouble(a, b);
		}
	}

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

	private void validateArithmeticOperations(Quantity<U> other, U targetUnit, boolean targetUnitRequired) {
		if (other == null) {
			throw new IllegalArgumentException("Quantity cannot be null");
		}

		if (!unit.getClass().equals(other.unit.getClass())) {
			throw new IllegalArgumentException("Cannot operate on different unit categories");
		}

		if (!Double.isFinite(this.value) || !Double.isFinite(other.value)) {
			throw new IllegalArgumentException("Values must be finite");
		}

		if (targetUnitRequired) {
			if (targetUnit == null) {
				throw new IllegalArgumentException("Target unit cannot be null");
			}
			if (!unit.getClass().equals(targetUnit.getClass())) {
				throw new IllegalArgumentException("Cannot convert between different unit categories");
			}
		}
	}

	private double performBaseArithmetic(Quantity<U> other, ArithmeticOperation operation) {
		this.unit.validateOperationSupport(operation.name());

		double base1 = unit.convertToBaseUnit(value);
		double base2 = other.unit.convertToBaseUnit(other.value);

		return operation.compute(base1, base2);
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
		validateTargetUnit(targetUnit);

		double baseValue = unit.convertToBaseUnit(value);
		double convertedValue = targetUnit.convertFromBaseUnit(baseValue);

		return new Quantity<U>(convertedValue, targetUnit);
	}

	/*
	 * adds quantities and returns with prior unit type
	 */
	public Quantity<U> add(Quantity<U> other) {
		return add(other, this.unit);
	}

	/*
	 * adds Length object and returns sum with specific unit type
	 */
	public Quantity<U> add(Quantity<U> other, U targetUnit) {

		validateArithmeticOperations(other, targetUnit, true);
		double base = performBaseArithmetic(other, ArithmeticOperation.ADD);
		double converted = targetUnit.convertFromBaseUnit(base);
		return new Quantity<>(converted, targetUnit);
	}

	/*
	 * Subtracts another quantity and returns result in this quantity's unit
	 */
	public Quantity<U> subtract(Quantity<U> other) {
		return subtract(other, this.unit);
	}

	/*
	 * Subtracts another quantity and returns result in specified target unit
	 */
	public Quantity<U> subtract(Quantity<U> other, U targetUnit) {
		validateArithmeticOperations(other, targetUnit, true);

		double base = performBaseArithmetic(other, ArithmeticOperation.SUBTRACT);
		double converted = targetUnit.convertFromBaseUnit(base);
		return new Quantity<>(converted, targetUnit);
	}

	/*
	 * Divides this quantity by another and returns ratio
	 */
	public double divide(Quantity<U> other) {
		validateArithmeticOperations(other, null, false);
		return performBaseArithmetic(other, ArithmeticOperation.DIVIDE);
	}

	/*
	 * validates Unit type
	 */
	public void validateTargetUnit(U unit) {
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