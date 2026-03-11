package com.apps;

public enum LengthUnit {
	FEET(1.0), INCHES(1.0 / 12.0), YARDS(3.0), CENTIMETERS(0.0328084);

	private final double conversionFactor;

	private LengthUnit(double conversionFactor) {
	
		this.conversionFactor = conversionFactor;
	}

	public double getConversionFactor() {
		return conversionFactor;
	}

	public double convertToBaseUnit(double value) {
		if (!Double.isFinite(value)) {
			throw new IllegalArgumentException("Value must be finite");
		}
		return value * conversionFactor;
	}

	public double convertFromBaseUnit(double value) {
		if (!Double.isFinite(value)) {
			throw new IllegalArgumentException("Value must be finite");
		}
		return value / conversionFactor;
	}
}