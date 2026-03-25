package com.apps.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.apps.entity.QuantityMeasurementEntity;
import com.apps.exception.QuantityMeasurementException;

public class QuantityMeasurementJdbcRepository implements IQuantityMeasurementRepository {

	private static final String DEFAULT_JDBC_URL = "jdbc:h2:file:./quantity_measurement_db;AUTO_SERVER=TRUE";
	private static final String DEFAULT_USER = "sa";
	private static final String DEFAULT_PASSWORD = "";

	private static final String INIT_SQL = """
			CREATE TABLE IF NOT EXISTS quantity_measurements (
			  id IDENTITY PRIMARY KEY,
			  this_value DOUBLE,
			  this_unit VARCHAR(64),
			  this_measurement_type VARCHAR(128),
			  that_value DOUBLE,
			  that_unit VARCHAR(64),
			  that_measurement_type VARCHAR(128),
			  operation VARCHAR(32),
			  result_value DOUBLE,
			  result_unit VARCHAR(64),
			  result_measurement_type VARCHAR(128),
			  result_string VARCHAR(256),
			  is_error BOOLEAN,
			  error_message VARCHAR(256)
			)
			""";

	private static final String INSERT_SQL = """
			INSERT INTO quantity_measurements (
			  this_value, this_unit, this_measurement_type,
			  that_value, that_unit, that_measurement_type,
			  operation,
			  result_value, result_unit, result_measurement_type, result_string,
			  is_error, error_message
			) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
			""";

	private static final String SELECT_ALL_SQL = """
			SELECT
			  this_value, this_unit, this_measurement_type,
			  that_value, that_unit, that_measurement_type,
			  operation,
			  result_value, result_unit, result_measurement_type, result_string,
			  is_error, error_message
			FROM quantity_measurements
			""";

	private final String jdbcUrl;
	private final String user;
	private final String password;

	private static QuantityMeasurementJdbcRepository instance;

	private QuantityMeasurementJdbcRepository(String jdbcUrl, String user, String password) {
		this.jdbcUrl = jdbcUrl;
		this.user = user;
		this.password = password;
		initSchema();
	}

	public static QuantityMeasurementJdbcRepository getInstance() {
		if (instance == null) {
			instance = new QuantityMeasurementJdbcRepository(DEFAULT_JDBC_URL, DEFAULT_USER, DEFAULT_PASSWORD);
		}
		return instance;
	}

	private Connection getConnection() throws SQLException {
		return DriverManager.getConnection(jdbcUrl, user, password);
	}

	private void initSchema() {
		try (Connection connection = getConnection(); Statement st = connection.createStatement()) {
			st.execute(INIT_SQL);
		} catch (SQLException e) {
			throw new QuantityMeasurementException("Failed to initialize database schema", e);
		}
	}

	@Override
	public void save(QuantityMeasurementEntity entity) {
		try (Connection connection = getConnection(); PreparedStatement ps = connection.prepareStatement(INSERT_SQL)) {
			ps.setDouble(1, entity.thisValue);
			ps.setString(2, entity.thisUnit);
			ps.setString(3, entity.thisMeasurementType);

			ps.setDouble(4, entity.thatValue);
			ps.setString(5, entity.thatUnit);
			ps.setString(6, entity.thatMeasurementType);

			ps.setString(7, entity.operation);

			ps.setDouble(8, entity.resultValue);
			ps.setString(9, entity.resultUnit);
			ps.setString(10, entity.resultMeasurementType);
			ps.setString(11, entity.resultString);

			ps.setBoolean(12, entity.isError);
			ps.setString(13, entity.errorMessage);

			ps.executeUpdate();
		} catch (SQLException e) {
			throw new QuantityMeasurementException("Failed to save quantity measurement", e);
		}
	}

	@Override
	public List<QuantityMeasurementEntity> getAllMeasurements() {
		List<QuantityMeasurementEntity> results = new ArrayList<>();
		try (Connection connection = getConnection();
				PreparedStatement ps = connection.prepareStatement(SELECT_ALL_SQL);
				ResultSet rs = ps.executeQuery()) {
			while (rs.next()) {
				QuantityMeasurementEntity entity = new QuantityMeasurementEntity();

				entity.thisValue = rs.getDouble(1);
				entity.thisUnit = rs.getString(2);
				entity.thisMeasurementType = rs.getString(3);

				entity.thatValue = rs.getDouble(4);
				entity.thatUnit = rs.getString(5);
				entity.thatMeasurementType = rs.getString(6);

				entity.operation = rs.getString(7);

				entity.resultValue = rs.getDouble(8);
				entity.resultUnit = rs.getString(9);
				entity.resultMeasurementType = rs.getString(10);
				entity.resultString = rs.getString(11);

				entity.isError = rs.getBoolean(12);
				entity.errorMessage = rs.getString(13);

				results.add(entity);
			}
			return results;
		} catch (SQLException e) {
			throw new QuantityMeasurementException("Failed to fetch quantity measurements", e);
		}
	}
}

