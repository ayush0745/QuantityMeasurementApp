package com.apps.quantitymeasurement.unit;

import java.util.function.Function;

public enum TemperatureUnit implements IMeasurable {

	CELSIUS(c -> c, c -> c),

	FAHRENHEIT(f -> (f - 32) * 5 / 9, c -> (c * 9 / 5) + 32),

	KELVIN(k -> k - 273.15, c -> c + 273.15);

	private final Function<Double, Double> toCelsius;
	private final Function<Double, Double> fromCelsius;

	TemperatureUnit(Function<Double, Double> toCelsius, Function<Double, Double> fromCelsius) {
		this.toCelsius = toCelsius;
		this.fromCelsius = fromCelsius;
	}

	@Override
	public double convertToBaseUnit(double value) {
		return toCelsius.apply(value);
	}

	@Override
	public double convertFromBaseUnit(double value) {
		return fromCelsius.apply(value);
	}

	@Override
	public double getConversionFactor() {
		return 1;
	}

	@Override
	public String getUnitName() {
		return this.name();
	}

	private final SupportsArithmetic supportsArithmetic = () -> false;

	@Override
	public boolean supportsArithmetic() {
		return supportsArithmetic.isSupported();
	}

	@Override
	public void validateOperationSupport(String operation) {
		throw new UnsupportedOperationException(operation + " is not supported for Temperature measurements.");
	}

	@Override
	public String getMeasurementType() {
		return this.getClass().getName();
	}

	@Override
	public IMeasurable getUnitInstance(String unitName) {
		for (TemperatureUnit unit : TemperatureUnit.values()) {
			if (unit.getUnitName().equalsIgnoreCase(unitName)) {
				return unit;
			}
		}
		throw new IllegalArgumentException("Invalid length unit " + unitName);
	}
}