// Qinzhi Peng
package lab1d;

import java.util.Scanner;

public class Calculator {
	String expression; // stores the arithmetic expression input by the user
	double result = 0; // stores the result

	// do not change this method
	public static void main(String[] args) {
		Calculator c = new Calculator();
		c.getUserInput();
		c.calculate();
		System.out.printf(" = %f", c.result);
	}

	// get user input to initialize 'expression' variable
	public void getUserInput() {
		Scanner input = new Scanner(System.in);
		expression = input.nextLine();
		input.close();
	}

	public void calculate() {
		if (expression.equals(""))
			return;
		Scanner tokens = new Scanner(expression);
		double a = 0;
		String operator = null;
		result = Double.parseDouble(tokens.useDelimiter("\\s+").next());
		while (tokens.hasNext()) {
			String token = tokens.useDelimiter("\\s+").next();
			if (token.matches("[0-9]+")) {
				a = Double.parseDouble(token);
				switch (operator) {
				case "+":
					result = result + a;
					break;
				case "-":
					result = result - a;
					break;
				case "*":
					result = result * a;
					break;
				case "/":
					result = result / a;
					break;
				}

			} else {
				operator = token;
			}
		}
		tokens.close();
	}
}
