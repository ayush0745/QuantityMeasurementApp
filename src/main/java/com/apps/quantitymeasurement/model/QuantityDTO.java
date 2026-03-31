package com.apps.quantitymeasurement.model;

import java.util.logging.Logger;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "A quantity with a value and unit")
public class QuantityDTO {

	private static final Logger logger = Logger.getLogger(QuantityDTO.class.getName());

	public interface IMeasurableUnit {
		String getUnitName();

		String getMeasurementType();
	}

	public enum LengthUnit implements IMeasurableUnit {

		FEET, INCHES, YARDS, CENTIMETERS;

		public String getUnitName() {
			return name();
		}

		public String getMeasurementType() {
			return "LengthUnit";
		}
	}

	public enum VolumeUnit implements IMeasurableUnit {

		LITRE, MILLILITRE, GALLON;

		public String getUnitName() {
			return name();
		}

		public String getMeasurementType() {
			return "VolumeUnit";
		}
	}

	public enum WeightUnit implements IMeasurableUnit {

		MILLIGRAM, GRAM, KILOGRAM, POUND, TONNE;

		public String getUnitName() {
			return name();
		}

		public String getMeasurementType() {
			return "WeightUnit";
		}
	}

	public enum TemperatureUnit implements IMeasurableUnit {

		CELSIUS, FAHRENHEIT;

		public String getUnitName() {
			return name();
		}

		public String getMeasurementType() {
			return "TemperatureUnit";
		}
	}

	@NotNull(message = "Value cannot be empty")
	@Schema(example = "1.0")
	public double value;

	@NotNull(message = "Unit cannot be null")
	@Schema(example = "FEET", allowableValues = { "FEET", "INCHES", "YARDS", "CENTIMETERS", "LITRE", "MILLILITRE",
			"GALLON", "MILLIGRAM", "GRAM", "KILOGRAM", "POUND", "TONNE", "CELSIUS", "FAHRENHEIT" })
	public String unit;

	@NotNull(message = "Measurement type cannot be null")
	@Pattern(regexp = "LengthUnit|VolumeUnit|WeightUnit|TemperatureUnit", message = "Measurement type must be one of: LengthUnit, VolumeUnit, WeightUnit, TemperatureUnit")
	@Schema(example = "LengthUnit", allowableValues = { "LengthUnit", "VolumeUnit", "WeightUnit", "TemperatureUnit" })
	public String measurementType;

	public QuantityDTO() {
	}

	public QuantityDTO(double value, String unit, String measurementType) {
		this.value = value;
		this.unit = unit;
		this.measurementType = measurementType;
	}

	@AssertTrue(message = "Unit must be valid for the specified measurement type")
	public boolean isValidUnit() {

		logger.info("Validating unit: " + unit + " for measurement type: " + measurementType);

		try {

			switch (measurementType) {

			case "LengthUnit":
				LengthUnit.valueOf(unit);
				break;

			case "VolumeUnit":
				VolumeUnit.valueOf(unit);
				break;

			case "WeightUnit":
				WeightUnit.valueOf(unit);
				break;

			case "TemperatureUnit":
				TemperatureUnit.valueOf(unit);
				break;

			default:
				return false;
			}

		} catch (IllegalArgumentException e) {
			return false;
		}

		return true;
	}
}