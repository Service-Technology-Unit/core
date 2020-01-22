package edu.ucdavis.ucdh.stu.core.utils;

import org.apache.commons.lang.StringUtils;

public class PasswordStrengthCalculator {
	private static PasswordStrengthCalculator passwordStrengthCalculator = new PasswordStrengthCalculator();

	private PasswordStrengthCalculator() {
	}

	public static PasswordStrengthCalculator getPasswordStrengthCalculator() {
		return passwordStrengthCalculator;
	}

	public static int passwordStrength(String username, String password, String confirmation) {
		return passwordStrengthCalculator.getPasswordStrength(username, password, confirmation);
	}

	public int getPasswordStrength(String username, String password, String confirmation) {
		int k = 1, h = 2, b = 3, a = 4, c = 5, g = 0;
		double j, e;
		if (StringUtils.isNotEmpty(confirmation) && !confirmation.equals(password)) {
			return c;
		}
		if (StringUtils.isEmpty(password)) {
			return k;
		}
		if (password.length() < 4) {
			return k;
		}
		if (password.equalsIgnoreCase(username)) {
			return h;
		}
		if (password.matches(".*[0-9].*")) {
			g += 10;
		}
		if (password.matches(".*[a-z].*")) {
			g += 26;
		}
		if (password.matches(".*[A-Z].*")) {
			g += 26;
		}
		if (password.matches(".*[^a-zA-Z0-9].*")) {
			g += 31;
		}
		j = Math.log(Math.pow(g, password.length()));
		e = (j / Math.log(2));
		if (e < 40) {
			return h;
		}
		if (e < 56) {
			return b;
		}
		return a;
	}
}
