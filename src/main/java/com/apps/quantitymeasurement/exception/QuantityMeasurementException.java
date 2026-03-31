package com.apps.quantitymeasurement.exception;

public class QuantityMeasurementException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public QuantityMeasurementException(String message) {
		super(message);
	}

	public QuantityMeasurementException(String message, Throwable cause) {
		super(message, cause);
	}

	public QuantityMeasurementException(Throwable cause) {
		super(cause);
	}
}