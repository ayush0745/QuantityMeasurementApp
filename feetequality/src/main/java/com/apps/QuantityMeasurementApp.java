package com.apps;

import java.util.InputMismatchException;
import java.util.Scanner;

public class QuantityMeasurementApp {
	public static class Feet {
		private final double value;

		public Feet(double value) {
			this.value = value;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj) {
				return true;
			}
			if (obj == null || getClass() != obj.getClass()) {
				return false;
			}
			Feet other = (Feet) obj;
			return Double.compare(value, other.value) == 0;
		}

		@Override
		public int hashCode() {
			return Double.hashCode(value);
		}
	}

	public static void main(String args[]) {
		System.out.println("Enter two feet values");
		try (Scanner sc = new Scanner(System.in)) {
			Feet feet1 = new Feet(sc.nextDouble());
			Feet feet2 = new Feet(sc.nextDouble());

			boolean result = feet1.equals(feet2);
			System.out.println(feet1 + " and " + feet2);
			System.out.println("Equal: " + result);
		} catch (InputMismatchException e) {
			
			System.err.println(e.getMessage());
		}
	}
}