package auction;

import static org.junit.Assert.*;

import org.junit.*;

public class SimpleOpponentBidderTest {

	private SimpleOpponentBidder bidder;

	@Before
	public void setUp() {
		bidder = new SimpleOpponentBidder();
		bidder.init(10, 100);
	}

	@Test
	public void testInitialBid() {
		assertEquals(20, bidder.placeBid());
	}

	@Test
	public void testBidNeverZero() {
		bidder.init(10, 1);
		assertTrue(bidder.placeBid() >= 1);
	}

	@Test
	public void testQuantityWonAfterWin() {
		bidder.bids(30, 10);
		assertEquals(2, bidder.ownQuantityWon);
	}

	@Test
	public void testQuantityWonAfterTie() {
		bidder.bids(30, 30);
		assertEquals(1, bidder.ownQuantityWon);
	}

	@Test
	public void testNoWinOnLoss() {
		bidder.bids(10, 30);
		assertEquals(0, bidder.ownQuantityWon);
	}

	@Test
	public void testWithremainingCashZero() {
		bidder.init(10, 0);
		assertEquals(0, bidder.placeBid());
	}
}
