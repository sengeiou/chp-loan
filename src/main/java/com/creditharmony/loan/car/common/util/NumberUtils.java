package com.creditharmony.loan.car.common.util;

public class NumberUtils {

	private NumberUtils() {
	}

	public static String doubleTrans(double d) {
		if (Math.round(d) - d == 0) {
			return String.valueOf((long) d);
		}
		return String.valueOf(d);
	}
}
