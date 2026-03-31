package com.apps.quantitymeasurement.unit;

public enum VolumeUnit implements IMeasurable {
	LITRE(1.0), MILLILITRE(0.001), GALLON(3.78541);

	private final double conversionFactor;

	private VolumeUnit(double conversionFactor) {
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
		for (VolumeUnit unit : VolumeUnit.values()) {
			if (unit.getUnitName().equalsIgnoreCase(unitName)) {
				return unit;
			}
		}
		throw new IllegalArgumentException("Invalid length unit " + unitName);
	}
}