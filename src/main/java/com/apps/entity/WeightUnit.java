package com.apps.entity;

public enum WeightUnit implements IMeasurable {
	KILOGRAM(1.0), GRAM(0.001), POUND(0.453592);

	private final double conversionFactor;

	private WeightUnit(double conversionFactor) {
		this.conversionFactor = conversionFactor;
	}

	@Override
	public double getConversionFactor() {
		return conversionFactor;
	}

	@Override
	public String getUnitName() {
		return name();
	}

	@Override
	public double convertToBaseUnit(double value) {
		if (!Double.isFinite(value)) {
			throw new IllegalArgumentException("Value must be finite");
		}
		return value * getConversionFactor();
	}

	@Override
	public double convertFromBaseUnit(double value) {
		if (!Double.isFinite(value)) {
			throw new IllegalArgumentException("Value must be finite");
		}
		return value / getConversionFactor();
	}

	@Override
	public String getMeasurementType() {
		return this.getClass().getName();
	}

	@Override
	public IMeasurable getUnitInstance(String unitName) {
		for (WeightUnit unit : WeightUnit.values()) {
			if (unit.getUnitName().equalsIgnoreCase(unitName)) {
				return unit;
			}
		}
		throw new IllegalArgumentException("Invalid length unit " + unitName);
	}
}