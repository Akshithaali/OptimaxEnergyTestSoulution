package auction;

/**
 * A simple opponent bidder. This bidder always bids 20% of his remaining cash.
 */

class SimpleOpponentBidder implements Bidder {
	int remainingCash;
	int ownQuantityWon;

	@Override
	public void init(int quantity, int cash) {
		this.remainingCash = cash;
		this.ownQuantityWon = 0;
	}

	@Override
	public int placeBid() {
		if (remainingCash == 0)
			return 0;
		int bid = (remainingCash * 20) / 100; // Always bid 20% of remaining cash
		return Math.max(1, bid); // Bid at least 1 MU
	}

	@Override
	public void bids(int own, int other) {
		remainingCash = remainingCash - own;
		if (own > other) {
			ownQuantityWon = ownQuantityWon + 2;
		} else if (other == own) {
			ownQuantityWon = ownQuantityWon + 1;
		}
	}
}