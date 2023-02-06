package net.mcreator.enhancing.procedures;

public class ClampProcedure {
	public static double execute(double current, double maximum, double minimum) {
		double temp = 0;
		temp = current;
		if (maximum < minimum) {
			System.out.println("Maximum must be smaller than minimum!");
			return 0;
		} else {
			if (temp < minimum) {
				temp = minimum;
			}
			if (temp > maximum) {
				temp = maximum;
			}
			return temp;
		}
	}
}
