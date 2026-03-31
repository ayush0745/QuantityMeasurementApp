package com.apps.quantitymeasurement.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

import com.apps.quantitymeasurement.unit.IMeasurable;

@Entity
@Table(name = "quantity_measurement_entity")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
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
}