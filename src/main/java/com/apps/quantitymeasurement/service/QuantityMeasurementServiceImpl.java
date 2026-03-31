package com.apps.quantitymeasurement.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.apps.quantitymeasurement.exception.QuantityMeasurementException;
import com.apps.quantitymeasurement.model.*;
import com.apps.quantitymeasurement.repository.QuantityMeasurementDatabaseRepository;
import com.apps.quantitymeasurement.unit.*;

@Service
public class QuantityMeasurementServiceImpl implements IQuantityMeasurementService {

	private static final Logger logger = LoggerFactory.getLogger(QuantityMeasurementServiceImpl.class);

	private QuantityMeasurementDatabaseRepository repository;
	private static final double EPSILON = 0.00001;

	public QuantityMeasurementServiceImpl(QuantityMeasurementDatabaseRepository repository) {
		this.repository = repository;
		logger.info("Quantity Measurement Service initialized with repository : " + repository);
	}

	private enum Operation {
		COMPARISON, CONVERSION, ARITHMETIC;
	}

	private enum ArithmeticOperation {
		ADD, SUBTRACT;
	}

	@Override
	public boolean compare(QuantityDTO thisDTO, QuantityDTO thatDTO) {

		QuantityModel<IMeasurable> thisModel = getQuantityModel(thisDTO);
		QuantityModel<IMeasurable> thatModel = getQuantityModel(thatDTO);
		double base1 = thisModel.getUnit().convertToBaseUnit(thisModel.getValue());
		double base2 = thatModel.getUnit().convertToBaseUnit(thatModel.getValue());

		boolean result = Math.abs(base1 - base2) < EPSILON;

		repository.save(new QuantityMeasurementEntity(thisModel, thatModel, Operation.COMPARISON.name(),
				String.valueOf(result)));

		return result;
	}

	@Override
	public QuantityDTO convert(QuantityDTO thisDTO, String targetUnitName) {

		QuantityModel<IMeasurable> source = getQuantityModel(thisDTO);

		IMeasurable sourceUnit = source.getUnit();
		IMeasurable targetUnit = sourceUnit.getUnitInstance(targetUnitName);
		validateMeasurementType(sourceUnit, targetUnit);

		double baseValue = sourceUnit.convertToBaseUnit(source.getValue());
		double convertedValue = targetUnit.convertFromBaseUnit(baseValue);

		QuantityModel<IMeasurable> targetModel = new QuantityModel<>(convertedValue, targetUnit);

		repository.save(new QuantityMeasurementEntity(source, targetModel, Operation.CONVERSION.name(),
				String.valueOf(convertedValue)));

		return new QuantityDTO(convertedValue, targetUnit.getUnitName(), sourceUnit.getMeasurementType());
	}

	@Override
	public QuantityDTO add(QuantityDTO thisDTO, QuantityDTO thatDTO) {
		return add(thisDTO, thatDTO, thisDTO);
	}

	@Override
	public QuantityDTO add(QuantityDTO thisDTO, QuantityDTO thatDTO, QuantityDTO targetDTO) {

		return performArithmetic(thisDTO, thatDTO, targetDTO, ArithmeticOperation.ADD);
	}

	@Override
	public QuantityDTO subtract(QuantityDTO thisDTO, QuantityDTO thatDTO) {
		return subtract(thisDTO, thatDTO, thisDTO);
	}

	@Override
	public QuantityDTO subtract(QuantityDTO thisDTO, QuantityDTO thatDTO, QuantityDTO targetDTO) {

		return performArithmetic(thisDTO, thatDTO, targetDTO, ArithmeticOperation.SUBTRACT);
	}

	@Override
	public double divide(QuantityDTO thisDTO, QuantityDTO thatDTO) {

		QuantityModel<IMeasurable> thisModel = getQuantityModel(thisDTO);
		QuantityModel<IMeasurable> thatModel = getQuantityModel(thatDTO);

		validateArithmeticOperands(thisModel, thatModel);

		double base1 = thisModel.getUnit().convertToBaseUnit(thisModel.getValue());
		double base2 = thatModel.getUnit().convertToBaseUnit(thatModel.getValue());

		if (base2 == 0) {
			throw new QuantityMeasurementException("Division by zero not allowed");
		}

		double result = base1 / base2;

		repository.save(new QuantityMeasurementEntity(thisModel, thatModel, Operation.ARITHMETIC.name(),
				String.valueOf(result)));

		return result;
	}

	private QuantityDTO performArithmetic(QuantityDTO thisDTO, QuantityDTO thatDTO, QuantityDTO targetDTO,
			ArithmeticOperation operation) {

		QuantityModel<IMeasurable> thisModel = getQuantityModel(thisDTO);
		QuantityModel<IMeasurable> thatModel = getQuantityModel(thatDTO);
		QuantityModel<IMeasurable> targetModel = getQuantityModel(targetDTO);

		validateArithmeticOperands(thisModel, thatModel);
		validateArithmeticOperands(thisModel, targetModel);

		double base1 = thisModel.getUnit().convertToBaseUnit(thisModel.getValue());
		double base2 = thatModel.getUnit().convertToBaseUnit(thatModel.getValue());

		double resultBase = (operation == ArithmeticOperation.ADD) ? base1 + base2 : base1 - base2;

		double finalValue = targetModel.getUnit().convertFromBaseUnit(resultBase);

		repository.save(new QuantityMeasurementEntity(thisModel, thatModel, Operation.ARITHMETIC.name(),
				String.valueOf(finalValue)));

		return new QuantityDTO(finalValue, targetDTO.getUnit(), targetDTO.getMeasurementType());
	}

	private <U extends IMeasurable> void validateArithmeticOperands(QuantityModel<U> q1, QuantityModel<U> q2) {

		if (!q1.getUnit().getClass().equals(q2.getUnit().getClass())) {
			throw new QuantityMeasurementException("Different measurement types not allowed");
		}

		if (q1.getUnit() instanceof TemperatureUnit) {
			throw new QuantityMeasurementException("Arithmetic operations not allowed for temperature");
		}
	}

	private void validateMeasurementType(IMeasurable sourceUnit, IMeasurable target) {

		if (!sourceUnit.getMeasurementType().equals(target.getMeasurementType())) {
			throw new IllegalArgumentException("Cannot convert different measurement types");
		}
	}

	private QuantityModel<IMeasurable> getQuantityModel(QuantityDTO dto) {

		String type = dto.getMeasurementType();
		String unitName = dto.getUnit();

		IMeasurable measurableUnit;

		switch (type) {

		case "LengthUnit" -> {
			measurableUnit = LengthUnit.valueOf(unitName);
		}

		case "WeightUnit" -> {
			measurableUnit = WeightUnit.valueOf(unitName);
		}

		case "VolumeUnit" -> {
			measurableUnit = VolumeUnit.valueOf(unitName);
		}

		case "TemperatureUnit" -> {
			measurableUnit = TemperatureUnit.valueOf(unitName);
		}

		default -> {
			throw new QuantityMeasurementException("Invalid measurement type");
		}
		}

		return new QuantityModel<>(dto.getValue(), measurableUnit);
	}
}