package com.apps.entity;

@FunctionalInterface
interface SupportsArithmetic {
	boolean isSupported();
}

public interface IMeasurable {
	double getConversionFactor();

	double convertToBaseUnit(double value);

	double convertFromBaseUnit(double value);

	String getUnitName();

	public String getMeasurementType();

	public IMeasurable getUnitInstance(String unitName);

	SupportsArithmetic supportsArithmetic = () -> true;

	default boolean supportsArithmetic() {
		return supportsArithmetic.isSupported();
	}

	default void validateOperationSupport(String operation) {
		if (!supportsArithmetic()) {
			throw new UnsupportedOperationException(operation + " not supported for unit type: " + getUnitName());
		}
	}
}