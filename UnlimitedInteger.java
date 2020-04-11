package m_A;

public class UnlimitedInteger {

	private String internalInt;

	public UnlimitedInteger(String op1) {
		check(op1);
		internalInt = op1;
	}

	public UnlimitedInteger plus(UnlimitedInteger op2) {
		return op2.plus(internalInt);
	}

	public UnlimitedInteger times(UnlimitedInteger op2) {
		return op2.times(internalInt);
	}

	public UnlimitedInteger plus(String op2) {
		String res = UnlimitedInteger.plus(internalInt, op2);
		return new UnlimitedInteger(res);
	}

	public UnlimitedInteger times(String op2) {
		String res = UnlimitedInteger.multi(internalInt, op2);
		return new UnlimitedInteger(res);
	}

	public UnlimitedInteger square() {
		String res = UnlimitedInteger.multi(internalInt, internalInt);
		return new UnlimitedInteger(res);
	}

	public String toString() {
		return internalInt;
	}

	private static void check(String op1) {

		char first = op1.charAt(0);
		if ((first != '-' && first != '+') && (first < '0' || first > '9'))
			throw new NumberFormatException();

		for (int i = 1; i < op1.length(); ++i) {

			if (op1.charAt(i) < '0' || op1.charAt(i) > '9')
				throw new NumberFormatException();
		}

	}

	private static String plus(String op1, String op2) {
		// Initialise string and int variables to be used later:
		String empty = "";
		String output = "";
		String sign = "";
		int maxlength = 0;
		int minlength = 0;
		int diff = 0;
		int carry = 0;
		check(op1);
		check(op2);
		// If both signed operands are positive or negative we continue with our plus()
		// method...

		// In case of both operands being negative, the result will be also negative:
		if (op1.charAt(0) == '-' && op2.charAt(0) == '-') {

			// Set the result's sign to be minus (negative value):
			sign = "-";

			// Remove the minus signs from operands to work easier within the plus() method:
			op1 = op1.substring(1);
			op2 = op2.substring(1);

			// If only one of the operands is negative and the other either signed ("+") or
			// un-signed positive, ...
		} else if (op1.charAt(0) == '-' || op2.charAt(0) == '-') {

			// ... call a different minus() method:
			output = minus(op1, op2);

			// The output will be signed positively or negatively ...
			// ... and may contain leading 0's, so to fix this...

			// ... store the output's sign:
			char outSign = output.charAt(0);

			// ... re-write the output's value un-signed:
			output = output.substring(1);

			// ... remove any leading 0's:
			while (output.charAt(0) == '0' && output.length() != 1) {
				output = output.substring(1);
			}

			if (output.length() == 0 && output.charAt(0) == '0') {
				return "0";
			}

			// If the output's value was positive, we don't need the "+" sign:
			if (outSign == '+') {
				return output;
			}

			// If the output's value was negative, we put back the "-" sign:
			return output = outSign + output;
		}

		// When taking as input two positive operands, check whether one or both of them
		// were signed ...
		// ... to make sure the result will also be signed positively:
		else if (op1.charAt(0) == '+') {
			// Remove plus signs from operands to work easier within the plus() method:
			op1 = op1.substring(1);

		} else if (op2.charAt(0) == '+') {

			// Remove plus signs from operands to work easier within the plus() method:
			op2 = op2.substring(1);

		}
		// 2 CASES: WHERE ONE STRING IS LONGER THAN THE OTHER

		// 1st case: The 1st operand is longer than the 2nd:
		if (op1.length() >= op2.length()) {

			// Calculate various values to be used for this case:
			maxlength = op1.length();
			minlength = op2.length();
			diff = maxlength - minlength;

			// Produce as many 0's as the two operands' length difference is and save them
			// in a 0-ed string:
			for (int i = 0; i < diff; i++) {
				empty += "0";
			}

			// Add the produced 0-ed string in front of the shorter operand, ...
			// ... making then sure that after this the two operands have the same values as
			// before ...
			// ... as well as the same length:
			for (int j = 0; j < op2.length(); j++) {
				empty += op2.charAt(j);
			}

			// A "reversed" loop iterating from last indexed digits to the left, ...
			// ... overcoming any carry bit problems:
			for (int k = maxlength - 1; k >= 0; k--) {

				// Temporarily store the current indexed digits' values in two local variables:
				int value1 = op1.charAt(k) - '0';
				int value2 = empty.charAt(k) - '0';

				// Add their values and any possible carry bits and store them in a result
				// integer variable:
				int result = value1 + value2 + carry;

				// Check whether the result's value exceeds 10, therefore subtracting 10 ...
				// ... and adding 1 to the carry bit variable, adding it to the next (1 position
				// left) digit:
				if (result >= 10) {
					result = result - 10;
					carry = 1;
				} else {
					carry = 0;
				}

				// If all digits have been successfully added and there 's still one carry bit,
				// ...
				// ... add it in front of the output's value
				// e.g.: 900 + 300 = 1200 instead of 200

				// Result digits were saved as integers, output of the plus() method has to be a
				// string:
				if (k == 0 && carry == 1) {
					output = "1" + result + output;
				} else
					output = result + output;

			}

			// 2nd case: When the 2nd operand is longer than the 1st:
		} else {
			// Calculate various values to be used for this case:
			maxlength = op2.length();
			minlength = op1.length();
			diff = maxlength - minlength;

			// Produce as many 0's as the two operands' length difference is and save them
			// in a 0-ed string:
			for (int i = 0; i < diff; i++) {
				empty += "0";
			}

			// Add the produced 0-ed string in front of the shorter operand, ...
			// ... making then sure that after this the two operands have the same values as
			// before ...
			// ... as well as the same length:
			for (int j = 0; j < op1.length(); j++) {
				empty += op1.charAt(j);
			}

			// The result is calculated and stored as in the previous case:
			for (int k = maxlength - 1; k >= 0; k--) {

				int value1 = empty.charAt(k) - '0';
				int value2 = op2.charAt(k) - '0';

				int result = value1 + value2 + carry;

				if (result >= 10) {
					result = result - 10;
					carry = 1;
				} else {
					carry = 0;
				}

				if (k == 0 && carry == 1) {
					output = "1" + result + output;
				} else
					output = result + output;

			}

		}

		// Return the output signed positively (one or both positive operands was
		// signed) ...
		// ... or negatively (addition of two negative operands) ...
		// ... or unsigned (addition of two unsigned positive operands):
		return sign + output;
	}

	private static String minus(String op1, String op2) {
		// Initialise string and int variables to be used later within this method:
		String empty = "";
		String output = "";
		String sign = "";
		String tmp = "";
		int maxlength = 0;
		int minlength = 0;
		int diff = 0;
		int carry = 0;
		int result = 0;
		check(op1);
		check(op2);
		// Make sure that the 2nd operand will always be the negative one:
		if (op1.charAt(0) == '-') {
			tmp = op1;
			op1 = op2;
			op2 = tmp;
		}

		// Remove signs to work easier within the minus() method:

		op2 = op2.substring(1);

		// 3 CASES: WHERE ONE STRING IS LONGER THAN THE OTHER OR ALREADY HAVE THE SAME
		// LENGTH

		// 1st case: The 1st operand is longer than the 2nd:
		if (op1.length() > op2.length()) {
			// Calculate various values to be used for this case:
			maxlength = op1.length();
			minlength = op2.length();
			diff = maxlength - minlength;

			// Produce as many 0's as the two operands' length difference is and save them
			// in a 0-ed string:
			for (int i = 0; i < diff; i++) {
				empty += "0";
			}

			// Add the produced 0-ed string in front of the shorter operand, ...
			// ... making then sure that after this the two operands have the same values as
			// before ...
			// ... as well as the same length:
			for (int j = 0; j < op2.length(); j++) {
				empty += op2.charAt(j);
			}
			// A "reversed" loop iterating from last indexed digits to the left, ...
			// ... overcoming any carry bit problems:
			for (int k = maxlength - 1; k >= 0; k--) {

				// Temporarily store the current indexed digits' values in two local variables:
				int value1 = op1.charAt(k) - '0';
				int value2 = empty.charAt(k) - '0';

				// Subtract any possible carry bit:
				value1 -= carry;

				// Check whether the 1st operand's value is less than the 2nd operand's,
				// therefore adding 10 ...
				// ... and adding 1 to the carry bit variable, subtracting it from the next (1
				// position left) digit
				// Proceed with the subtraction of the two digits:
				if (value1 < value2) {
					value1 += 10;
					result = value1 - value2;
					carry = 1;
				} else {
					result = value1 - value2;
					carry = 0;
				}
				output = result + output;

			}

			// 2nd case: The 2nd operand is longer than the 1st:
		} else if (op2.length() > op1.length()) {
			sign = "-";
			tmp = op2;
			op2 = op1;
			op1 = tmp;

			// Calculate various values to be used for this case:
			maxlength = op1.length();
			minlength = op2.length();
			diff = maxlength - minlength;

			// Produce as many 0's as the two operands' length difference is and save them
			// in a 0-ed string:
			for (int i = 0; i < diff; i++) {
				empty += "0";
			}

			// Add the produced 0-ed string in front of the shorter operand, ...
			// ... making then sure that after this the two operands have the same values as
			// before ...
			// ... as well as the same length:
			for (int j = 0; j < op2.length(); j++) {
				empty += op2.charAt(j);
			}

			// A "reversed" loop iterating from last indexed digits to the left, ...
			// ... overcoming any carry bit problems:
			for (int k = maxlength - 1; k >= 0; k--) {

				// The result is calculated and stored as in the previous case:
				int value1 = op1.charAt(k) - '0';
				int value2 = empty.charAt(k) - '0';

				value1 -= carry;

				if (value1 < value2) {
					value1 += 10;
					result = value1 - value2;
					carry = 1;
				} else {
					result = value1 - value2;
					carry = 0;
				}

				output = result + output;

			}
		}

		// 3rd case: When the two operands have already the same length:
		else if (op1.length() == op2.length()) {

			int i = 0;

			// To find out which of the two operands has the smallest value, ...
			// ... if the first digits are identical, find the MSB position for which they
			// aren't:
			while (op1.charAt(i) == op2.charAt(i) && i != op1.length() - 1) {
				++i;
			}

			// If 1st operand is the one with the smallest absolute value:
			if (op1.charAt(i) < op2.charAt(i)) {
				// Make sure the result to be negative:
				sign = "-";

				// Swap values for easiness:
				tmp = op2;
				op2 = op1;
				op1 = tmp;

				// Iterate through all digits (right to left):
				for (int k = op1.length() - 1; k >= 0; k--) {

					// The result is calculated and stored as in the previous cases:
					int value1 = op1.charAt(k) - '0';
					int value2 = op2.charAt(k) - '0';

					value1 -= carry;

					if (value1 < value2) {
						value1 += 10;
						result = value1 - value2;
						carry = 1;
					} else {
						result = value1 - value2;
						carry = 0;
					}

					output = result + output;
				}

				// If 2nd operand is the one with the smallest absolute value:
			} else {

				// Iterate through all digits (right to left):
				for (int k = op1.length() - 1; k >= 0; k--) {
					// Make sure the result to be positive:
					sign = "+";

					// The result is calculated and stored as in the previous cases:
					int value1 = op1.charAt(k) - '0';
					int value2 = op2.charAt(k) - '0';

					value1 -= carry;

					if (value1 < value2) {
						value1 += 10;
						result = value1 - value2;
						carry = 1;
					} else {
						result = value1 - value2;
						carry = 0;
					}

					output = result + output;
				}
			}
		}
		return sign + output;
	}

	private static String multi(String op1, String op2) {
		// Initialise string and int variables to be used later:
		String sign = "";
		String temp = "";
		String buffer = "";
		String output = "";
		String total = "";
		int carry = 0;

		check(op1);
		check(op2);

		// In case of both operands being negative, the result will be positive:
		if (op1.charAt(0) == '-' && op2.charAt(0) == '-') {

			// DON'T SET the result's sign to be plus (positive value):
			sign = "";

			// Remove the minus signs from operands to work easier within the multi()
			// method:
			op1 = op1.substring(1);
			op2 = op2.substring(1);

			// If only one of the operands is negative and the other either signed ("+") or
			// un-signed positive, ...
		} else if (op1.charAt(0) == '-') {

			// Set the result's sign to be minus (negative value):
			sign = "-";
			op1 = op1.substring(1);
		} else if (op2.charAt(0) == '-') {

			// Set the result's sign to be minus (negative value):
			sign = "-";

			op2 = op2.substring(1);
		}
		// following lines remove any pluses

		if (op1.charAt(0) == '+') {
			op1 = op1.substring(1);
		}

		if (op2.charAt(0) == '+') {
			op2 = op2.substring(1);
		}
		// Determine which string is longer and have it as op1, in order to create the
		// totals[] array:

		// In case of op2 being longer than op1, swap them:
		if (op2.length() > op1.length()) {
			temp = op1;
			op1 = op2;
			op2 = temp;
		}

		// Declare the totals[] array:
		String[] totals = new String[op1.length()];

		// Main body of multi() method:

		for (int i = op1.length() - 1; i >= 0; i--) {

			// Reset output:
			output = "";

			for (int j = op2.length() - 1; j >= 0; j--) {

				// Multiplication digit by digit:
				int value1 = op1.charAt(i) - '0';
				int value2 = op2.charAt(j) - '0';

				int result = value1 * value2 + carry;

				if (result >= 10 && j > 0) {
					carry = result / 10;
					result = result % 10;
				} else {
					carry = 0;
				}
				output = result + output;
			}
			totals[i] = output + buffer;
			buffer += "0";
		}

		// As if multiplying in paper, add the digit-by-digit multiplication results, to
		// get the final result:
		total = totals[0];
		for (int i = 1; i < totals.length; i++) {
			total = plus(total, totals[i]);
		}

		// Return the (signed) result after calling the removeZeroes() method:
		return sign +

				removeZeroes(total);

	}

	public static String removeZeroes(String output) {

		// removeZeroes() method as its name declares it, removes any leading zeroes
		// from a number:
		while (output.charAt(0) == '0' && output.length() >= 1) {
			output = output.substring(1);
		}

		// With the only exception of the number being zero:
		if (output.length() == 1 && output.charAt(0) == '0') {
			return "0";
		}

		return output;
	}

}