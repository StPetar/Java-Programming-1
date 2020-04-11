package monitored_assignment;

import java.util.Date;

public class Monitored_Assignment {

	public static void main(String[] args) {
		String s1 = Terminal.getString("Enter a numeric string:");
		String s2 = Terminal.getString("Enter a numeric string:");
		String s3 = Terminal.getString("Enter a numeric string:");

		Date start = new Date();
		String result = "";
		System.out.println(plus(plus(s1, s2),s3));
/*
		int range = 10000;
		for (int i = -range; i < range; i++) {
			for (int j = -range; j < range; j++) {
				try {
					result = plus(i + "", j + "");
				} catch (Exception e) {
					result = e.getMessage();
				}
				//System.out.println(result);
				if (!result.equals((i + j) + "")) {
					System.out.println("Error adding " + i + ", " + j + " result: " + result);
					return;
				}
			}
		}
*/	
		Date end = new Date();
		System.out.println("done in: " + (end.getTime() - start.getTime()) + "ms");

	}

	public static String plus(String op1, String op2) {
		// INITIALISE STRING AND INTS THAT ARE GOING TO BE USED
		String empty = "";
		String output = "";
		String sign = "";
		int maxlength = 0;
		int minlength = 0;
		int diff = 0;
		int carry = 0;

		// IF ONLY ONE OF THE NUMBERS IS NEGATIVE WE CALL THE MINUS METHOD TO SUBTRACT
		// THEM
		if (op1.charAt(0) == '-' && op2.charAt(0) == '-') {
			// SET FINAL SIGN TO - AND CONTINUE WITH THE ADDITION OF BOTH NUMBERS
			sign = "-";
			// REMOVE MINUS FROM BOTH STRINGS IF POSSIBLE
			op1 = op1.replace("-", "");
			op2 = op2.replace("-", "");
			// PLUSES DONT CHANGE ANYTHING SO JUST REMOVE THEM
			op1 = op1.replace("+", "");
			op2 = op2.replace("+", "");
		} else if (op1.charAt(0) == '-' || op2.charAt(0) == '-') {

			output = minus(op1, op2);
			char outSign = output.charAt(0);

			output = output.substring(1, output.length());

			while (output.charAt(0) == '0' && output.length() != 1) {
				output = output.substring(1, output.length());
			}
			if (output.length() == 0 && output.charAt(0) == '0') {
				return "0";

			}
			if (outSign == '+') {
				return output;
			}
			return output = outSign + output;
		}

		// IF BOTH VALUES ARE NEGATIVE WE CONTINUE WITH THE PLUS METHOD AS NORMAL

		// IF NO NUMBER IS NEGATIVE AND EITHER ONE OF THE NUMBERS HAS A PLUS SIGN WE
		// WANT THE OUTPUT TO HAVE ONE AS WELL
		else if (op1.charAt(0) == '+' || op2.charAt(0) == '+') {
			// CHANGE THE FINAL SIGN TO MINUS
			sign = "+";

			// REMOVE PLUS SIGNS
			op1 = op1.replace("+", "");
			op2 = op2.replace("+", "");

		} else
			// CHECK IF THE ENTERED VALUES CONSIST ONLY OF NUMBERS
			// SINCE THERE ARE NO PLUS OR MINUS SIGNS LEFT THE REMAINING CHARACTERS OF THE
			// STRINGS SHOULD ONLY BE NUMBERS
			for (int i = 0; i < op1.length(); ++i) {

				if (op1.charAt(i) < '0' || op1.charAt(i) > '9')
					throw new NumberFormatException();
			}
		for (int j = 0; j < op2.length(); ++j) {

			if (op2.charAt(j) < '0' || op2.charAt(j) > '9')
				throw new NumberFormatException();
		}

		// 2 CONDITIONS: WHERE 1ST STRING IS LONGER THAN THE 2ND AND VICE-VERSA
		if (op1.length() >= op2.length()) {

			// CALCULATE VALUES THAT ARE GOING TO BE USED
			maxlength = op1.length();
			minlength = op2.length();
			diff = maxlength - minlength;

			// ADDS 0'S AT THE BEGINNING OF THE SHORTER STRING EQUAL TO THE DIFFERENCE IN
			// THE LENGTH OF BOTH STRINGS
			for (int i = 0; i < diff; i++) {
				empty += "0";
			}
			// COPIES THE ORIGINAL STRING AFTER THE 0'S ADDED ABOVE RESULTING IN BOTH
			// STRINGS BEING THE SAME LENGTH
			for (int j = 0; j < op2.length(); j++) {
				empty += op2.charAt(j);
			}
			// A LOOP THAT GOES FROM THE LAST INDEX FIRST TO OVERCOME PROBLEMS WITH CARRY
			// BITS
			for (int k = maxlength - 1; k >= 0; k--) {

				// THE VALUES FROM BOTH STRINGS AT THE SAME POSITION IN THE STRING ADDED
				// TOGETHER WITH THE CARRY AND SAVED INTO THE INT RESULT
				int value1 = op1.charAt(k) - '0';
				int value2 = empty.charAt(k) - '0';

				int result = value1 + value2 + carry;

				// CHECKS IF THE RESULT VALUE IS HIGHER THAN 10, MEANING THERE IS A CARRY OVER
				// OF 1 FOR THE NEXT LOOP
				if (result >= 10) {
					result = result - 10;
					carry = 1;
				} else {
					carry = 0;
				}

				// IF THIS IS THE LAST LOOP AND THERE IS STILL A CARRY ITS ADDED TO THE FRONT OF
				// THE FINAL OUTPUT STRING
				// *FIXES 900+300=200 INSTEAD OF 1200 RESULT*

				// RESULT IS STORED AS AN INT SO OUTPUT IS ADDED TO IT TO CONVERT IT INTO A
				// STRING
				if (k == 0 && carry == 1) {
					output = "1" + result + output;
				} else
					output = result + output;

			}
			// THE OTHER OPTION IS WHEN THE 2ND STRING IS LONGER THAN THE FIRST
		} else {

			maxlength = op2.length();
			minlength = op1.length();
			diff = maxlength - minlength;

			// 0'S ARE ADDED TO THE SHORTER STRING UNTIL BOTH OF THEM HAVE THE SAME LENGTH
			for (int i = 0; i < diff; i++) {
				empty += "0";
			}
			// AND THEN THE ORIGINAL STRING IS ADDED AFTER THE 0'S
			for (int j = 0; j < op1.length(); j++) {
				empty += op1.charAt(j);
			}
			// THE RESULT IS CALCULATED AND STORED AS BEFORE
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
		return sign + output;
	}

	public static String minus(String op1, String op2) {
		// INITIALISE STRING AND INTS THAT ARE GOING TO BE USED
		String empty = "";
		String output = "";
		String sign = "";
		String tmp = "";
		int maxlength = 0;
		int minlength = 0;
		int diff = 0;
		int carry = 0;
		int result = 0;
		// ALWAYS KEEPS op2 AS THE - VALUE
		if (op1.charAt(0) == '-') {
			tmp = op1;
			op1 = op2;
			op2 = tmp;
		}
		// REMOVE SIGNS FROM STRINGS
		op2 = op2.replace("-", "");
		op1 = op1.replace("+", "");
		op1 = op1.replace("-", "");
		op2 = op2.replace("+", "");
		// CHECK IF THE STRINGS CONTAIN ONLY NUMBERS
		for (int i = 0; i < op1.length(); ++i) {

			if (op1.charAt(i) < '0' || op1.charAt(i) > '9')
				throw new NumberFormatException();
		}
		for (int j = 0; j < op2.length(); ++j) {

			if (op2.charAt(j) < '0' || op2.charAt(j) > '9')
				throw new NumberFormatException();
		}

		if (op1.length() > op2.length()) {
			sign = "+";
			// CALCULATE VALUES THAT ARE GOING TO BE USED
			maxlength = op1.length();
			minlength = op2.length();
			diff = maxlength - minlength;

			// ADDS 0'S AT THE BEGINNING OF THE SHORTER STRING EQUAL TO THE DIFFERENCE IN
			// THE LENGTH OF BOTH STRINGS
			for (int i = 0; i < diff; i++) {
				empty += "0";
			}
			// COPIES THE ORIGINAL STRING AFTER THE 0'S ADDED ABOVE RESULTING IN BOTH
			// STRINGS BEING THE SAME LENGTH
			for (int j = 0; j < op2.length(); j++) {
				empty += op2.charAt(j);
			}
			// A LOOP THAT GOES FROM THE LAST INDEX FIRST TO OVERCOME PROBLEMS WITH CARRY
			// BITS
			for (int k = maxlength - 1; k >= 0; k--) {

				// THE VALUES FROM BOTH STRINGS AT THE SAME POSITION IN THE STRING ADDED
				// TOGETHER WITH THE CARRY AND SAVED INTO THE INT RESULT
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
		} else if (op2.length() > op1.length()) {
			sign = "-";
			tmp = op2;
			op2 = op1;
			op1 = tmp;

			// CALCULATE VALUES THAT ARE GOING TO BE USED
			maxlength = op1.length();
			minlength = op2.length();
			diff = maxlength - minlength;

			// ADDS 0'S AT THE BEGINNING OF THE SHORTER STRING EQUAL TO THE DIFFERENCE IN
			// THE LENGTH OF BOTH STRINGS
			for (int i = 0; i < diff; i++) {
				empty += "0";
			}
			// COPIES THE ORIGINAL STRING AFTER THE 0'S ADDED ABOVE RESULTING IN BOTH
			// STRINGS BEING THE SAME LENGTH
			for (int j = 0; j < op2.length(); j++) {
				empty += op2.charAt(j);
			}
			// A LOOP THAT GOES FROM THE LAST INDEX FIRST TO OVERCOME PROBLEMS WITH CARRY
			// BITS
			for (int k = maxlength - 1; k >= 0; k--) {

				// THE VALUES FROM BOTH STRINGS AT THE SAME POSITION IN THE STRING ADDED
				// TOGETHER WITH THE CARRY AND SAVED INTO THE INT RESULT
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
		/*
		 * else { if (op1.charAt(0) < op2.charAt(0)) { tmp = op2; op2 = op1; op1 = tmp;
		 * }
		 */
		else if (op1.length() == op2.length()) {

			int i = 0;

			while (op1.charAt(i) == op2.charAt(i) && i != op1.length() - 1) {
				++i;
			}
			if (op1.charAt(i) < op2.charAt(i)) {
				sign = "-";
				tmp = op2;
				op2 = op1;
				op1 = tmp;
				
				for (int k = op1.length() - 1; k >= 0; k--) {
					
					// THE VALUES FROM BOTH STRINGS AT THE SAME POSITION IN THE STRING ADDED
					// TOGETHER WITH THE CARRY AND SAVED INTO THE INT RESULT
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
			}}
				else {
			// A LOOP THAT GOES FROM THE LAST INDEX FIRST TO OVERCOME PROBLEMS WITH CARRY
			// BITS
			for (int k = op1.length() - 1; k >= 0; k--) {
				sign = "+";
				// THE VALUES FROM BOTH STRINGS AT THE SAME POSITION IN THE STRING ADDED
				// TOGETHER WITH THE CARRY AND SAVED INTO THE INT RESULT
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
}