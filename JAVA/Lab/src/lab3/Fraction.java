//Qinzhi Peng; qinzhip
package lab3;

public class Fraction {
	int numerator;
	int denominator;

	Fraction() {
		numerator = 1;
		denominator = 1;
	}

	Fraction(int numerator, int denominator) {
		this.numerator = numerator;
		this.denominator = denominator;
	}

	public String toString() {
		String stringFract;
		stringFract = Integer.toString(numerator) + "/" + Integer.toString(denominator);
		return stringFract;
	}

	double toDecimal() {
		double actualFract;
		actualFract = (double) numerator / denominator;
		return actualFract;
	}

	Fraction add(Fraction f2) {
		Fraction f3 = new Fraction();
		f3.numerator = this.numerator * f2.denominator + this.denominator * f2.numerator;
		f3.denominator = this.denominator * f2.denominator;
		int GCD = findGCD(f3.numerator, f3.denominator);
		f3.numerator = f3.numerator / GCD;
		f3.denominator = f3.denominator / GCD;
		return f3;
	}

	int findGCD(int n, int d) {
		if (n == 0) {
			return 1;
		} else if (d == 0) {
			return n;
		} else {
			return findGCD(d, n % d);
		}
	}

}
