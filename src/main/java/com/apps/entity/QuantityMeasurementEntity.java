package com.apps.entity;

import java.io.Serializable;
import java.util.Objects;

public class QuantityMeasurementEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	public double thisValue;
	public String thisUnit;
	public String thisMeasurementType;

	public double thatValue;
	public String thatUnit;
	public String thatMeasurementType;

	public String operation;

	public double resultValue;
	public String resultUnit;
	public String resultMeasurementType;
	public String resultString;

	public boolean isError;
	public String errorMessage;

	public QuantityMeasurementEntity() {
	}

	private QuantityMeasurementEntity(QuantityModel<? extends IMeasurable> thisQuantity,
			QuantityModel<? extends IMeasurable> thatQuantity, String operation) {
		this.thisValue = thisQuantity.getValue();
		this.thisUnit = thisQuantity.getUnit().getUnitName();
		this.thisMeasurementType = thisQuantity.getUnit().getMeasurementType();

		this.thatValue = thatQuantity.getValue();
		this.thatUnit = thatQuantity.getUnit().getUnitName();
		this.thatMeasurementType = thatQuantity.getUnit().getMeasurementType();

		this.operation = operation;
		this.isError = false;
	}

	public QuantityMeasurementEntity(QuantityModel<? extends IMeasurable> thisQuantity,
			QuantityModel<? extends IMeasurable> thatQuantity, String operation, String result) {
		this(thisQuantity, thatQuantity, operation);
		this.resultString = result;
	}

	public QuantityMeasurementEntity(QuantityModel<? extends IMeasurable> thisQuantity,
			QuantityModel<? extends IMeasurable> thatQuantity, String operation,
			QuantityModel<? extends IMeasurable> result) {
		this(thisQuantity, thatQuantity, operation);
		this.resultValue = result.getValue();
		this.resultUnit = result.getUnit().getUnitName();
		this.resultMeasurementType = result.getUnit().getMeasurementType();
	}

	public QuantityMeasurementEntity(QuantityModel<? extends IMeasurable> thisQuantity,
			QuantityModel<? extends IMeasurable> thatQuantity, String operation, String errorMessage, boolean isError) {
		this(thisQuantity, thatQuantity, operation);
		this.errorMessage = errorMessage;
		this.isError = isError;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}
		QuantityMeasurementEntity that = (QuantityMeasurementEntity) obj;

		return Double.compare(this.thisValue, that.thisValue) == 0
				&& Double.compare(this.thatValue, that.thatValue) == 0
				&& Double.compare(this.resultValue, that.resultValue) == 0 && isError == that.isError
				&& Objects.equals(this.thisUnit, that.thisUnit)
				&& Objects.equals(this.thisMeasurementType, that.thisMeasurementType)
				&& Objects.equals(this.thatUnit, that.thatUnit)
				&& Objects.equals(this.thatMeasurementType, that.thatMeasurementType)
				&& Objects.equals(this.operation, that.operation) && Objects.equals(this.resultUnit, that.resultUnit)
				&& Objects.equals(this.resultMeasurementType, that.resultMeasurementType)
				&& Objects.equals(this.resultString, that.resultString)
				&& Objects.equals(this.errorMessage, that.errorMessage);
	}

	@Override
	public String toString() {

		if (isError) {
			return "Operation: " + operation + " | This: " + thisValue + " " + thisUnit + " | That: " + thatValue + " "
					+ thatUnit + " | ERROR: " + errorMessage;
		}

		if (resultString != null) {
			return "Operation: " + operation + " | This: " + thisValue + " " + thisUnit + " | That: " + thatValue + " "
					+ thatUnit + " | Result: " + resultString;
		}

		return "Operation: " + operation + " | This: " + thisValue + " " + thisUnit + " | That: " + thatValue + " "
				+ thatUnit + " | Result: " + resultValue + " " + resultUnit;
	}
}