package edu.yc.oats.algs;

public class Rational {

	private long numerator;
	private long denominator;

	public Rational(int numerator, int denominator) {

		//Denominator can't be 0.
		if (denominator == 0) {
			throw new IllegalArgumentException("Error: Denominator can't be 0.");
		}

		//Reduce by dividing numerator and denominator
		//by the greatest common divisor
		int gcd = gcd(numerator, denominator);
		numerator /= gcd;
		denominator /= gcd;

		this.numerator = numerator;
		this.denominator = denominator;
	}
	//Getter for denominator
	public long denominator() {
		return this.denominator;
	}
	//Getter for numerator
	public long numerator() {
		return this.numerator;
	}

	private int gcd(int n, int d) {
		if (d == 0)
			return n;
		int r = n % d;
		return gcd(d, r);
	}

	//A method to reduce a Rational object.
	// private Rational reduce(Rational number) {
	// 	int gcd = gcd(numerator, denominator);
	// 	numerator /= gcd;
	// 	denominator /= gcd;
	// 	return Rational;
	// }

	public Rational plus(Rational b) {
		//Find a common denominator.
		long newDen = this.denominator() * b.denominator();
		//Calculate the new numerator.
		long newNum = this.numerator() * b.denominator() + b.numerator() * this.denominator();
		return new Rational((int)newNum, (int)newDen);
	}

	public Rational minus(Rational b) {
		//Find a common denominator.
		long newDen = this.denominator() * b.denominator();
		//Calculate the new numerator.
		long newNum = this.numerator() * b.denominator() - b.numerator() * this.denominator();
		return new Rational((int)newNum, (int)newDen);
	}

	public Rational times(Rational b) {
		//Multiply the numerators and denominators.
		long newNum = this.numerator() * b.numerator();
		long newDen = this.denominator() * b.denominator();
		return new Rational((int)newNum, (int)newDen);
	}

	public Rational divides(Rational b) {
		//If dividing by 0, throw an exception.
		if (b.numerator() == 0) {
			throw new IllegalArgumentException("Cannot divide by 0.");
		}
		//Multiply by reciprocal.
		Rational temp = new Rational((int)b.denominator(), (int)b.numerator());
		return this.times(temp);
	}

	public boolean equals(Rational that) {
		//If the numerator and denominator are equal,
		//the rational numbers are equal.
		if (this.numerator() == that.numerator() && this.denominator() == that.denominator()) {
			return true;
		}
		return false;
	}

	public String toString() {

		//If denominator is positive,
		if (this.denominator() > 0) {
			//If the number is 0,
			if (this.numerator() == 0) {
				return "0";
			}
			//If the number is whole,
			if (this.denominator() == 1) {
				return Integer.toString((int)this.numerator());
			}
			//Return the fraction as a string.
			return Integer.toString((int)this.numerator()) + "/" + Integer.toString((int)this.denominator());
		}
		
		//If denominator is negative, move "-" to beginning.
		return "-" + Integer.toString((int)this.numerator()) + "/" + Integer.toString((int)this.denominator()).substring(1);

	}
}