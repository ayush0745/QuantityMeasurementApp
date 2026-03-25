package com.apps.repository;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import com.apps.entity.QuantityMeasurementEntity;

class AppendableObjectOutputStream extends ObjectOutputStream {
	public AppendableObjectOutputStream(OutputStream out) throws IOException {
		super(out);
	}

	@Override
	protected void writeStreamHeader() throws IOException {
		File file = new File(QuantityMeasurementCacheRepository.FILE_NAME);
		if (!file.exists() || file.length() == 0) {
			super.writeStreamHeader();
		} else {
			reset();
		}
	}
}

public class QuantityMeasurementCacheRepository implements IQuantityMeasurementRepository {

	public static final String FILE_NAME = "quantity_measurement_repo.ser";

	private List<QuantityMeasurementEntity> quantityMeasurementEntityCache;

	private static QuantityMeasurementCacheRepository instance;

	private QuantityMeasurementCacheRepository() {
		quantityMeasurementEntityCache = new ArrayList<>();
		loadFromDisk();
	}

	public static QuantityMeasurementCacheRepository getInstance() {
		if (instance == null) {
			instance = new QuantityMeasurementCacheRepository();
		}
		return instance;
	}

	@Override
	public void save(QuantityMeasurementEntity entity) {
		quantityMeasurementEntityCache.add(entity);
		saveToDisk(entity);
	}

	@Override
	public List<QuantityMeasurementEntity> getAllMeasurements() {
		return new ArrayList<>(quantityMeasurementEntityCache);
	}

	private void saveToDisk(QuantityMeasurementEntity entity) {
		try (FileOutputStream fos = new FileOutputStream(FILE_NAME, true);
				AppendableObjectOutputStream oos = new AppendableObjectOutputStream(fos)) {
			oos.writeObject(entity);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void loadFromDisk() {
		File file = new File(FILE_NAME);
		if (!file.exists()) {
			return;
		}

		try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
			while (true) {
				try {
					QuantityMeasurementEntity entity = (QuantityMeasurementEntity) ois.readObject();
					quantityMeasurementEntityCache.add(entity);
				} catch (EOFException e) {
					break;
				}
			}
			System.out.println(
					"Loaded " + quantityMeasurementEntityCache.size() + " quantity measurement entities from storage");
		} catch (IOException | ClassNotFoundException e) {
			System.out.println("Error loading quantity measurement entities " + e.getMessage());
		}
	}
}