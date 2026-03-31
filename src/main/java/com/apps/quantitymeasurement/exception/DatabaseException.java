package com.apps.quantitymeasurement.exception;

public class DatabaseException extends QuantityMeasurementException {
	public DatabaseException(String message) {
		super(message);
	}

	public DatabaseException(String message, Throwable cause) {
		super(message, cause);
	}

	public static DatabaseException connectionFailed(String details, Throwable cause) {
		return new DatabaseException("Database Connection Failed: " + details, cause);
	}

	public static DatabaseException queryFailed(String query, Throwable cause) {
		return new DatabaseException("Query Execution Failed: " + query, cause);
	}

	public static DatabaseException transactionFailed(String operation, Throwable cause) {
		return new DatabaseException("Transaction Failed during: " + operation, cause);
	}
}