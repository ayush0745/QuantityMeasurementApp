package com.apps.entity;

public class QuantityDTO {

	private double value;
	private IMeasurableUnit unit;

	public QuantityDTO(double value, IMeasurableUnit unit) {
		this.value = value;
		this.unit = unit;
	}

	public double getValue() {
		return value;
	}

	public IMeasurableUnit getUnit() {
		return unit;
	}

	public interface IMeasurableUnit {
		String getUnitName();

		String getMeasurementType();
	}

	enum LengthUnit implements IMeasurableUnit {
		FEET, INCHES, YARDS, CENTIMETERS;

		public String getUnitName() {
			return name();
		}

		public String getMeasurementType() {
			return "LENGTH";
		}
	}

	enum WeightUnit implements IMeasurableUnit {
		GRAM, KILOGRAM, POUND;

		public String getUnitName() {
			return name();
		}

		public String getMeasurementType() {
			return "WEIGHT";
		}
	}

	enum VolumeUnit implements IMeasurableUnit {
		LITRE, MILLILITRE, GALLON;

		public String getUnitName() {
			return name();
		}

		public String getMeasurementType() {
			return "VOLUME";
		}
	}

	enum TemperatureUnit implements IMeasurableUnit {
		CELSIUS, FAHRENHEIT, KELVIN;

		public String getUnitName() {
			return name();
		}

		public String getMeasurementType() {
			return "TEMPERATURE";
		}
	}

	public static QuantityDTO ofLength(double value, String unitName) {
		LengthUnit unit = LengthUnit.valueOf(unitName.toUpperCase().trim());
		return new QuantityDTO(value, unit);
	}

	public static QuantityDTO ofWeight(double value, String unitName) {
		WeightUnit unit = WeightUnit.valueOf(unitName.toUpperCase().trim());
		return new QuantityDTO(value, unit);
	}

	public static QuantityDTO ofVolume(double value, String unitName) {
		VolumeUnit unit = VolumeUnit.valueOf(unitName.toUpperCase().trim());
		return new QuantityDTO(value, unit);
	}

	public static QuantityDTO ofTemperature(double value, String unitName) {
		TemperatureUnit unit = TemperatureUnit.valueOf(unitName.toUpperCase().trim());
		return new QuantityDTO(value, unit);
	}
}