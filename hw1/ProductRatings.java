public class ProductRatings
{
	private int numberOfProducts;
	private int numberOfPossibleRatings;
	private int[][] ratingCount;
	private int[][] ratings;
	private int[] counter;

	private int[] starCounter;
	//private int[] superCounter;
	private boolean[] isStar;
	private boolean[] isSuper;

	public ProductRatings(int productQuantity, int ratingQuantity)
	{
		this.numberOfProducts = productQuantity;
		this.numberOfPossibleRatings = ratingQuantity;
		this.ratingCount = new int[this.numberOfProducts][20];
		this.ratings = new int[this.numberOfProducts][this.numberOfPossibleRatings];
		this.counter = new int[this.numberOfProducts];

		this.starCounter = new int[this.numberOfProducts];
		//this.superCounter = new int[this.numberOfProducts];
		this.isStar = new boolean[this.numberOfProducts];
		this.isSuper = new boolean[this.numberOfProducts];
	}

	/**
	 * @param productID the product that received this rating. 
	 * @param rating the product received
	 */
	public void addNewRating(int productID, int rating)
	{
		//Add this rating and update counter.
		this.ratings[productID][rating - 1] += 1;
		this.counter[productID]++;
		this.ratingCount[productID][this.counter[productID] % 20] = rating;

		//Check if the highest rating is most common over last 20 ratings.
		if (this.getMostCommonRating20(productID) == numberOfPossibleRatings) {
			if (!this.isStar[productID]) {
				this.starCounter[productID] = 20;
				this.printStar(productID);
				this.isStar[productID] = true;
			}
			else {
				this.starCounter[productID]++;
				//Check if it's a superstar
				if (this.starCounter[productID] == 60) {
					this.printSuper(productID);
					this.isSuper[productID] = true;
				}
				else if (this.starCounter[productID] > 60 && this.isSuper[productID] == true) {
					if (this.starCounter[productID] % 30 == 0) {
						this.printSuper(productID);
					}
				}
			}
		}
		else {
			this.starCounter[productID] = 0;
			this.isStar[productID] = false;
			this.isSuper[productID] = false;
		}

	}

	/**
	 * Return an array with the most common rating ever,
	 * and the most common rating of the last 20 ratings.
	 * @param productID the product we're interested in.
	 */
	public int getMostCommonRating(int productID)
	{
		int mostCommonRating = -1;
		int timesReceived = -1;
		for (int i = 0; i < this.numberOfPossibleRatings; i++) {
			if (this.ratings[productID][i] > timesReceived) {
				mostCommonRating = i + 1;
				timesReceived = this.ratings[productID][i]; 
			}
		}
		return mostCommonRating;
	}

	public int getMostCommonRating20(int productID)
	{
		if (this.counter[productID] >= 20) {
			//Find most common rating over last 20 ratings for this product.
			int[] freqRatings = new int[this.numberOfPossibleRatings];

			//Fill an array of length(number of possible ratings)
			//with the frequency of each rating (of last 20 ratings).
			for (int i = 0; i < 20; i++) {
				int tempRating = ratingCount[productID][i];
				freqRatings[tempRating - 1]++;
			}
			//Determine highest number in freqRatings.
			int mostCommonRating20 = -1;
			int maxFreq = -1;
			for (int j = 0; j < this.numberOfPossibleRatings; j++) {
				if (freqRatings[j] > maxFreq) {
					mostCommonRating20 = j + 1;
					maxFreq = freqRatings[j];
				}
			}
			return mostCommonRating20;
		}
		return -1;
	}

	public int[] getFullRatingInfo(int productID)
	{
		int[] ratingInfo = new int[6];
		ratingInfo[0] = this.getMostCommonRating(productID);
		for (int i = 0; i < 5; i++) {
			ratingInfo[i+1] = this.ratings[productID][i];
		}
		return ratingInfo;
	}

	public void resetProduct(int productID)
	{
		for (int i = 0; i < this.numberOfPossibleRatings; i++) {
			this.ratings[productID][i] = 0;
		}
		for (int j = 0; j < 20; j++) {
			this.ratingCount[productID][j] = 0;
		}
		this.counter[productID] = 0;
		this.starCounter[productID] = 0;
		//this.superCounter[productID] = 0;
		this.isStar[productID] = false;
		this.isSuper[productID] = false;
	}

	public void resetAll()
	{
		for (int i = 0; i < this.numberOfProducts; i++) {
			this.resetProduct(i);
		}
	}

	public void printStar(int productID) {
		System.out.println("Product " + productID + " is a star!");
	}

	public void printSuper(int productID) {
		System.out.println("Product " + productID + " is a SUPERSTAR!");
	}

	public static void main(String[] args) {

		//Create ProductRatings for 2 products,
		//with 5 possible ratings.
		ProductRatings p = new ProductRatings(2, 5);

		//After 90 ratings of 5 for product 0,
		//should print star ONCE and super TWICE.
		for (int i = 0; i < 90; i++) {
			p.addNewRating(0, 5);
		}
		//Remove star and superstar status.
		for (int i = 0; i < 91; i++) {
			p.addNewRating(0, 4);
		}
		//Become a star again, so print out star again.
		for (int i = 0; i < 20; i++) {
			p.addNewRating(0, 5);
		}

		//After 90 ratings of 5 for product 1,
		//should print star ONCE and super ONCE.
		for (int i = 0; i < 89; i++) {
			p.addNewRating(1, 5);
		}

		//Reset product 1.
		p.resetProduct(1);

		//It should NOT print super again,
		//but it should print star ONCE.
		for (int i = 0; i < 20; i++) {
			p.addNewRating(1, 5);
		}

		//Reset all.
		p.resetAll();

		//Print star ONCE for each product again.
		for (int i = 0; i < 20; i++) {
			p.addNewRating(0, 5);
		}
		for (int i = 0; i < 20; i++) {
			p.addNewRating(1, 5);
		}

	}
}