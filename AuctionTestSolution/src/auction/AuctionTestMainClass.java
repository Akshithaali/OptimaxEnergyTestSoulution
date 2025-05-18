package auction;

public class AuctionTestMainClass {

	public static void main(String[] args) {

		// Initialize auction parameters
		int totalQuantity = 10; // x -> Total quantity units to auction
		int totalCash = 100; // y -> Monetary units each bidder has

		// Create bidders
		Bidder adaptiveBidder = new StrategicBidder();
		Bidder simpleOpponent = new SimpleOpponentBidder();

		// Initialize bidders
		adaptiveBidder.init(totalQuantity, totalCash);
		simpleOpponent.init(totalQuantity, totalCash);

		int remainingQuantity = totalQuantity;
		int round = 1;

		System.out.println("Starting auction with " + totalQuantity + " QU and " + totalCash + " MU per bidder\n");

		while (remainingQuantity > 0) {

			System.out.println("\nRound " + round + " (Remaining QU: " + remainingQuantity + ")");

			// Get bids
			int bid1 = adaptiveBidder.placeBid();
			int bid2 = simpleOpponent.placeBid();

			System.out.println("Adaptive Bidder Amount: " + bid1 + "  Opponent Amount: " + bid2);

			// Process bids
			adaptiveBidder.bids(bid1, bid2);
			simpleOpponent.bids(bid2, bid1);

			// Finding winner of the current round

			if (bid1 > bid2) {
				System.out.println("...Adaptive Bidder wins 2 QU...");
			} else if (bid2 > bid1) {
				System.out.println("...Simple Opponent wins 2 QU...");
			} else {
				System.out.println("...Tie - both get 1 QU...");
			}

			remainingQuantity = remainingQuantity - 2;
			round++;
		}

		// Final results and Printing winner details
		int adaptiveBidderFinalQuantity = ((StrategicBidder) adaptiveBidder).getOwnQuantityWon();
		int adaptiveBidderRemainingCash = ((StrategicBidder) adaptiveBidder).getRemainingCash();
		int simpleOpponentFinlQuantity = ((SimpleOpponentBidder) simpleOpponent).ownQuantityWon;
		int simpleOpponentRemainingCash = ((SimpleOpponentBidder) simpleOpponent).remainingCash;

		System.out.println("\nAdaptive Bidder won: " + adaptiveBidderFinalQuantity + ", Remaining Cash: "
				+ adaptiveBidderRemainingCash);

		System.out.println("Simple Opponent won: " + simpleOpponentFinlQuantity + ", Remaining Cash: "
				+ simpleOpponentRemainingCash);

		if (adaptiveBidderFinalQuantity > simpleOpponentFinlQuantity) {

			System.out.println("\nAdaptive Bidder wins the auction");

		} else if (simpleOpponentFinlQuantity > adaptiveBidderFinalQuantity) {

			System.out.println("\nSimple Opponent wins the auction");

		} else {

			// Tie in quantity – compare remaining cash
			if (adaptiveBidderRemainingCash > simpleOpponentRemainingCash) {

				System.out.println("\nAdaptive Bidder wins the auction by having more remaining MU");

			} else if (simpleOpponentRemainingCash > adaptiveBidderRemainingCash) {

				System.out.println("\nSimple Opponent wins the auction by having more remaining MU");

			} else {

				System.out.println("\nIt’s a perfect tie in both quantity and cash");
			}
		}
	}
}