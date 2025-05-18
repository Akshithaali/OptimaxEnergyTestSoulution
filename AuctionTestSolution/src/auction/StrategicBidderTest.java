package auction;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StrategicBidderTest {

	private StrategicBidder bidder;

	@BeforeEach
	void setUp() {
		bidder = new StrategicBidder();
		bidder.init(10, 100); // Initialize with 10 quantity and 100 cash
	}

	@Test
	void testInitialization() {
		int bid = bidder.placeBid();
		assertTrue(bid > 0 && bid <= 100);
	}

	@Test
	void testFirstBidIs10Percent() {
		int bid = bidder.placeBid();
		assertEquals(10, bid); // 10% of 100
	}

	@Test
	void testBidWhenLowRemainingQuantity() {
		// Simulate rounds to reduce quantity
		for (int i = 0; i < 4; i++) {
			bidder.bids(10, 5);
		}
		int bid = bidder.placeBid();
		assertEquals(6, bidder.placeBid());
	}

	@Test
	void testBidsMethodWinLossTie() {
		bidder.bids(20, 10); // win
		assertEquals(2, bidder.getOwnQuantityWon());

		bidder.bids(10, 20); // loss
		assertEquals(2, bidder.getOwnQuantityWon());

		bidder.bids(15, 15); // tie
		assertEquals(3, bidder.getOwnQuantityWon());
	}

	@Test
	void testBidAdjustsAfterOpponentWins() {
		bidder.bids(5, 10); // loss
		int bid = bidder.placeBid();
		assertTrue(bid > 5); // should be more aggressive
	}

	@Test
	void testBidIsZeroWhenNoQuantityOrCash() {
		bidder.init(0, 100);
		assertEquals(0, bidder.placeBid());

		bidder.init(10, 0);
		assertEquals(0, bidder.placeBid());
	}

	@Test
	void testBidConservesCashWhenLeading() {
		// Simulate that bidder is leading by more than 2
		bidder.bids(10, 5);
		bidder.bids(10, 5);
		int bid = bidder.placeBid();
		assertTrue(bid < 10);
	}
}
