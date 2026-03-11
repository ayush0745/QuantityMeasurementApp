package com.apps;

public interface IMeasurable {
	double getConversionFactor();

	double convertToBaseUnit(double value);

	double convertFromBaseUnit(double value);

	String getUnitName();
}