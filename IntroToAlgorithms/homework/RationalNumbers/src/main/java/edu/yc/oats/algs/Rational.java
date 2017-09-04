package edu.yc.oats.algs;

public class Rational {

	private long numerator;
	private long denominator;

	public Rational(long numerator, long denominator){

		//Denominator can't be 0.
		if (denominator == 0) {
			throw new IllegalArgumentException("Error: Denominator can't be 0.");
		}

		//Reduce by dividing numerator and denominator
		//by the greatest common divisor
		long gcd = gcd(numerator, denominator);
		numerator /= gcd;
		denominator /= gcd;

		//Set the instance variables.
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

	//Euclid's algorithm to get gcd
	//of numerator and denominator.
	private long gcd(long n, long d) {
		if (d == 0)
			return n;
		long r = n % d;
		return gcd(d, r);
	}

	public Rational plus(Rational b) {
		//Find a common denominator.
		long newDen = this.denominator() * b.denominator();
		//Calculate the new numerator.
		long newNum = this.numerator() * b.denominator() + b.numerator() * this.denominator();
		//Return result as a reduced rational number.
		return new Rational(newNum, newDen);
	}

	public Rational minus(Rational b) {
		//Find a common denominator.
		long newDen = this.denominator() * b.denominator();
		//Calculate the new numerator.
		long newNum = this.numerator() * b.denominator() - b.numerator() * this.denominator();
		//Return result as a reduced rational number.
		return new Rational(newNum, newDen);
	}

	public Rational times(Rational b) {
		//Multiply the numerators and denominators.
		long newNum = this.numerator() * b.numerator();
		long newDen = this.denominator() * b.denominator();
		//Return result as a reduced rational number.
		return new Rational(newNum, newDen);
	}

	public Rational divides(Rational b) {
		//If dividing by 0, throw an exception.
		if (b.numerator() == 0) {
			throw new IllegalArgumentException("Cannot divide by 0.");
		}

		//Store b's reciprocal as "temp"
		Rational temp = new Rational(b.denominator(), b.numerator());

		//Return product of this and temp.
		return this.times(temp);
	}

	public boolean equals(Rational that) {
		//If the numerator and denominator are equal,
		//then the rational numbers are equal.
		if (this.numerator() == that.numerator() && this.denominator() == that.denominator()) {
			return true;
		}
		//Otherwise, they're not equal.
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
				return Long.toString(this.numerator());
			}
			//Return the fraction as a string.
			return Long.toString(this.numerator()) + "/" + Long.toString(this.denominator());
		}
		
		//If denominator is negative, move "-" to beginning.
		return "-" + Long.toString(this.numerator()) + "/" + Long.toString(this.denominator()).substring(1);
	}
}