package com.apps.service;

import com.apps.entity.IMeasurable;
import com.apps.entity.LengthUnit;
import com.apps.entity.QuantityDTO;
import com.apps.entity.QuantityMeasurementEntity;
import com.apps.entity.QuantityModel;
import com.apps.entity.TemperatureUnit;
import com.apps.entity.VolumeUnit;
import com.apps.entity.WeightUnit;
import com.apps.exception.QuantityMeasurementException;
import com.apps.repository.IQuantityMeasurementRepository;

public class QuantityMeasurementServiceImpl implements IQuantityMeasurementService {

	private IQuantityMeasurementRepository repository;
	private static final double EPSILON = 0.00001;

	public QuantityMeasurementServiceImpl(IQuantityMeasurementRepository repository) {
		this.repository = repository;
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

		validateArithmeticOperands(thisModel, thatModel);

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

		String measurementType = thisDTO.getUnit().getMeasurementType();
		return switch (measurementType) {
		case "LENGTH" -> QuantityDTO.ofLength(convertedValue, targetUnit.getUnitName());
		case "WEIGHT" -> QuantityDTO.ofWeight(convertedValue, targetUnit.getUnitName());
		case "VOLUME" -> QuantityDTO.ofVolume(convertedValue, targetUnit.getUnitName());
		case "TEMPERATURE" -> QuantityDTO.ofTemperature(convertedValue, targetUnit.getUnitName());
		default -> throw new QuantityMeasurementException("Invalid measurement type");
		};
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

		return new QuantityDTO(finalValue, targetDTO.getUnit());
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

		QuantityDTO.IMeasurableUnit dtoUnit = dto.getUnit();
		String type = dtoUnit.getMeasurementType();
		String unitName = dtoUnit.getUnitName();

		IMeasurable measurableUnit;

		switch (type) {
		case "LENGTH" -> {
			measurableUnit = LengthUnit.valueOf(unitName);
		}
		case "WEIGHT" -> {
			measurableUnit = WeightUnit.valueOf(unitName);
		}
		case "VOLUME" -> {
			measurableUnit = VolumeUnit.valueOf(unitName);
		}
		case "TEMPERATURE" -> {
			measurableUnit = TemperatureUnit.valueOf(unitName);
		}
		default -> {
			throw new QuantityMeasurementException("Invalid measurement type");
		}
		}
		return new QuantityModel<>(dto.getValue(), measurableUnit);
	}
}