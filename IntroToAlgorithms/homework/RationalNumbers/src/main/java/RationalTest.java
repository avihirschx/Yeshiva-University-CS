import java.util.Scanner;

public class RationalTest {

	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);

		//Create two rational numbers.
		System.out.println("Enter the numerator and denominator of the first rational number.");
		Rational n = new Rational(sc.nextLong(), sc.nextLong());
		System.out.println("Enter the numerator and denominator of the second rational number.");
		Rational m = new Rational(sc.nextLong(), sc.nextLong());

		//Print them (reduced)
		System.out.println("Your rational numbers are: " + n.toString() + " and " + m.toString() + ".");

		//Check if equal.
		if (n.equals(m)) {
			System.out.println("They are equal.");
		}
		else {
			System.out.println("They are not equal.");
		}

		//Add them.
		Rational sum = n.plus(m);
		System.out.println("The sum is: " + sum.toString() + ".");

		//Subtract them.
		Rational difference = n.minus(m);
		System.out.println("The difference is: " + difference.toString() + ".");

		//Multiply them.
		Rational product = n.times(m);
		System.out.println("The product is: " + product.toString() + ".");

		//Divide them.
		Rational quotient = n.divides(m);
		System.out.println("The quotient is: " + quotient.toString() + ".");
	}
}