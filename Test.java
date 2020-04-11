package m_A;

import Terminal.Terminal;

public class Test {
	public static void main(String[] args) {
		UnlimitedInteger a = new UnlimitedInteger(Terminal.getString("Enter value for a:"));
		UnlimitedInteger b = new UnlimitedInteger(Terminal.getString("Enter value for b:"));
		UnlimitedInteger c = new UnlimitedInteger(Terminal.getString("Enter value for c:"));
		UnlimitedInteger x = new UnlimitedInteger(Terminal.getString("Enter value for x:"));

		// ax^2+bx+c
		UnlimitedInteger result = (x.square().times(a).plus(x.times(b).toString()).plus(c));

		System.out.println(a + " * " + x + "^2 + " + b + " * " + x + " + " + x + " = " + result);

	}
}
