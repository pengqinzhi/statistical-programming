//Qinzhi Peng; qinzhip
package lab3;

public class MixedFraction extends Fraction {
	int naturalNumber;

	MixedFraction(int naturalNumber, int numerator, int denominator) {
		super(numerator, denominator);
		this.naturalNumber = naturalNumber;
	}

	@Override
	public String toString() {
		String stringFract;
		stringFract = Integer.toString(naturalNumber) + " " + Integer.toString(numerator) + "/"
				+ Integer.toString(denominator);
		return stringFract;
	}

	@Override
	double toDecimal() {
		double actualFract;
		actualFract = (double) (naturalNumber * denominator + numerator) / denominator;
		return actualFract;
	}

	Fraction toFraction() {
		Fraction f = new Fraction();
		f.numerator = this.naturalNumber * this.denominator + this.numerator;
		f.denominator = this.denominator;
		return f;
	}

	Fraction add(MixedFraction mf) {
		Fraction f3 = new Fraction();
		f3 = this.toFraction().add(mf.toFraction());
		return f3;
	}

}
