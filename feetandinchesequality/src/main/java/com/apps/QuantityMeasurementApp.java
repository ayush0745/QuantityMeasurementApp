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

	public static class Inches {
		private final double value;

		public Inches(double value) {
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
			Inches other = (Inches) obj;
			return Double.compare(value, other.value) == 0;
		}

		@Override
		public int hashCode() {
			return Double.hashCode(value);
		}
	}

	public static boolean demonstrateFeetEquality(double value1, double value2) {
		return new Feet(value1).equals(new Feet(value2));
	}

	public static boolean demonstrateInchesEquality(double value1, double value2) {
		return new Inches(value1).equals(new Inches(value2));
	}

	public static void main(String args[]) {
		try (Scanner sc = new Scanner(System.in)) {
			System.out.println("Enter two feet values");
			double feet1 = sc.nextDouble();
			double feet2 = sc.nextDouble();
			System.out.println(
					feet1 + " feet and " + feet2 + " feet are equal? " + demonstrateFeetEquality(feet1, feet2));

			System.out.println("Enter two inch values");
			double inch1 = sc.nextDouble();
			double inch2 = sc.nextDouble();
			System.out.println(
					feet1 + " inches and " + feet2 + " inches are equal? " + demonstrateInchesEquality(inch1, inch2));
		} catch (InputMismatchException e) {
			
			System.err.println(e.getMessage());
		}
	}
}