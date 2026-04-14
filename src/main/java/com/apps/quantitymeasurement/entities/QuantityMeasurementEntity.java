package com.apps.quantitymeasurement.entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import com.apps.quantitymeasurement.model.QuantityModel;
import com.apps.quantitymeasurement.unit.IMeasurable;

@Entity
@Table(name = "quantity_measurement_entity")
public class QuantityMeasurementEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private double thisValue;
	private String thisUnit;
	private String thisMeasurementType;

	private double thatValue;
	private String thatUnit;
	private String thatMeasurementType;

	private String operation;

	private double resultValue;
	private String resultUnit;
	private String resultMeasurementType;

	private String resultString;

	private boolean isError;

	private String errorMessage;

	@Column(updatable = false)
	private LocalDateTime createdAt;

	private LocalDateTime updatedAt;

	public QuantityMeasurementEntity() {}

	public QuantityMeasurementEntity(QuantityModel<IMeasurable> thisModel, QuantityModel<IMeasurable> thatModel,
			String operation, String resultString) {
		this.thisValue = thisModel.getValue();
		this.thisUnit = thisModel.getUnit().getUnitName();
		this.thisMeasurementType = thisModel.getUnit().getMeasurementType();
		if (thatModel != null) {
			this.thatValue = thatModel.getValue();
			this.thatUnit = thatModel.getUnit().getUnitName();
			this.thatMeasurementType = thatModel.getUnit().getMeasurementType();
		}
		this.operation = operation;
		this.resultString = resultString;
	}

	@PrePersist
	public void onCreate() {
		createdAt = LocalDateTime.now();
		updatedAt = LocalDateTime.now();
	}

	@PreUpdate
	public void onUpdate() {
		updatedAt = LocalDateTime.now();
	}

	public Long getId() { return id; }
	public void setId(Long id) { this.id = id; }
	public double getThisValue() { return thisValue; }
	public void setThisValue(double thisValue) { this.thisValue = thisValue; }
	public String getThisUnit() { return thisUnit; }
	public void setThisUnit(String thisUnit) { this.thisUnit = thisUnit; }
	public String getThisMeasurementType() { return thisMeasurementType; }
	public void setThisMeasurementType(String thisMeasurementType) { this.thisMeasurementType = thisMeasurementType; }
	public double getThatValue() { return thatValue; }
	public void setThatValue(double thatValue) { this.thatValue = thatValue; }
	public String getThatUnit() { return thatUnit; }
	public void setThatUnit(String thatUnit) { this.thatUnit = thatUnit; }
	public String getThatMeasurementType() { return thatMeasurementType; }
	public void setThatMeasurementType(String thatMeasurementType) { this.thatMeasurementType = thatMeasurementType; }
	public String getOperation() { return operation; }
	public void setOperation(String operation) { this.operation = operation; }
	public double getResultValue() { return resultValue; }
	public void setResultValue(double resultValue) { this.resultValue = resultValue; }
	public String getResultUnit() { return resultUnit; }
	public void setResultUnit(String resultUnit) { this.resultUnit = resultUnit; }
	public String getResultMeasurementType() { return resultMeasurementType; }
	public void setResultMeasurementType(String resultMeasurementType) { this.resultMeasurementType = resultMeasurementType; }
	public String getResultString() { return resultString; }
	public void setResultString(String resultString) { this.resultString = resultString; }
	public boolean isError() { return isError; }
	public void setError(boolean isError) { this.isError = isError; }
	public String getErrorMessage() { return errorMessage; }
	public void setErrorMessage(String errorMessage) { this.errorMessage = errorMessage; }
	public LocalDateTime getCreatedAt() { return createdAt; }
	public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
	public LocalDateTime getUpdatedAt() { return updatedAt; }
	public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
