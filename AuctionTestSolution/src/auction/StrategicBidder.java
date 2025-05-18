package auction;

/**
 * A Strategic bidder. This bidder analysis different game scenarios and
 * opponent behavior before deciding on each bid:
 */

public class StrategicBidder implements Bidder {

	private int totalQuantity;
	private int totalCash;
	private int remainingQuantity;
	private int remainingCash;
	private int opponentRemainingCash;
	private int ownQuantityWon;
	private int opponentQuantityWon;
	private int lastOwnBid;
	private int lastOpponentBid;
	private int roundsCompleted;

	/** This is a function used for initializing variables */
	@Override
	public void init(int quantity, int cash) {
		this.totalQuantity = quantity;
		this.totalCash = cash;
		this.remainingQuantity = quantity;
		this.remainingCash = cash;
		this.opponentRemainingCash = cash;
		this.ownQuantityWon = 0;
		this.opponentQuantityWon = 0;
		this.roundsCompleted = 0;
	}

	/**
	 * Calculates and returns a bid based on strategy and available resources. 
	 * Returns 0 if bidding is not possible due to insufficient quantity or funds.
	 */

	@Override
	public int placeBid() {
		/*
		 * Auction Ends.. When either RremainingQuantity is Zero(No item Left) Or
		 * RemainingCash is Zero(No money to bid).
		 */

		if (remainingQuantity == 0 || remainingCash == 0) {
			return 0;
		}

		int basicBid = calculateBasicBid();

		int adjustedBid = adjustBidBasedOnStrategy(basicBid);

		int finalBid = Math.min(adjustedBid, remainingCash); // Ensure don't bid more than we have

		lastOwnBid = finalBid;
		return finalBid;
	}

	private int calculateBasicBid() {
		/*
		 * To start, since having no idea about opponent strategy choosing arbitrary
		 * number (10% of remainingCash) to start with.
		 */
		int basicBid = Math.max(1, remainingCash / 10);

		// If quantity is 2, give larger bid amount to increase probability of winning
		if (remainingQuantity <= 2) {
			basicBid = (remainingCash * 70) / 100;
			remainingCash = remainingCash - basicBid;
		}

		return basicBid;
	}

	private int adjustBidBasedOnStrategy(int basicBid) {

		if (roundsCompleted == 0) {
			return basicBid; // First round - use basic bid

		}

		// Case 1: Indicating opponent won last round,Should be less aggressive.
		if (lastOwnBid < lastOpponentBid) {

			int lastbidDifference = lastOpponentBid - lastOwnBid;
			int increasedBid = lastOwnBid + lastbidDifference + (remainingCash * 3) / 100;
			int maxAllowedBid = remainingCash / 2; // Cap bid at 50% of remaining cash
			basicBid = Math.min(increasedBid, maxAllowedBid);

		}

		// Case 2 : If opponent is more aggressive, increase bid to win the match
		double opponentAggressionPercent = ((totalCash - opponentRemainingCash) * 100) / totalCash;
		double ownAggressionPercent = ((totalCash - remainingCash) * 100) / totalCash;

		if (opponentAggressionPercent > ownAggressionPercent && roundsCompleted > 0) {

			int opponentAverageBid = (totalCash - opponentRemainingCash) / roundsCompleted;
			int adjustedBid = opponentAverageBid + (opponentAverageBid * 10) / 100; // increase bid by +10%
			basicBid = Math.max(basicBid, adjustedBid);
		}

		// Case 3 : If won quantity is less, be more aggressive.
		if (getOwnQuantityWon() < opponentQuantityWon) {

			int shortageQuantity = opponentQuantityWon - getOwnQuantityWon();
			int increasedBid = basicBid + (basicBid * 12 * shortageQuantity) / 100; // Increase bid by 12%
			int maxAllowedBid = remainingCash / 2; // Cap bid at 50% of remaining cash
			basicBid = Math.min(increasedBid, maxAllowedBid);
		}

		// Case 4 : If quantity in hand is significantly greater than opponent, save cash
		if (getOwnQuantityWon() > opponentQuantityWon + 2) {

			basicBid = Math.max(1, (basicBid * 70) / 100); // reduce to 70%
		}

		setRemainingCash(remainingCash);
		return basicBid;
	}

	@Override
	public void bids(int own, int other) {

		/*
		 * The product is awarded to who has offered the most MU; if both bid the same,
		 * then both get 1 QU.
		 */

		int lastBidAmount = own;
		int opponentLastBidAmount = other;

		if (lastBidAmount > opponentLastBidAmount) { // If Bid amount is big, won the round
			ownQuantityWon = ownQuantityWon + 2;
			setOwnQuantityWon(ownQuantityWon);

		} else if (lastBidAmount < opponentLastBidAmount) { // If Bid amount is less, Opponent won the round

			opponentQuantityWon = opponentQuantityWon + 2;

		} else { // Tie - both get 1 QU

			ownQuantityWon = ownQuantityWon + 1;
			setOwnQuantityWon(ownQuantityWon);
			opponentQuantityWon = opponentQuantityWon + 1;
		}

		// Update tracking variables
		remainingQuantity = remainingQuantity - 2;
		lastOwnBid = own;
		lastOpponentBid = other;
		remainingCash = remainingCash - own;
		setRemainingCash(remainingCash);
		roundsCompleted++;

	}

	public int getOwnQuantityWon() {
		return ownQuantityWon;
	}

	public void setOwnQuantityWon(int ownQuantityWon) {
		this.ownQuantityWon = ownQuantityWon;
	}

	public void setRemainingCash(int ownQuantityWon) {
		this.remainingCash = remainingCash;
	}

	public int getRemainingCash() {
		return remainingCash;
	}
}
