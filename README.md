[ReadmeFile.pdf](https://github.com/user-attachments/files/20273253/ReadmeFile.pdf)


**PROJECT OVERVIEW**

This is a strategic two-player auction game where a fixed quantity (x QU) of a product is auctioned in rounds of 2 QU each. Both players (or bidding agents) start with a fixed amount of money (y MU).

  •	In each round, players submit secret bids for the current 2 QU.
  
  •	After both bids are placed, they are revealed:
  
    ->	The player with the higher bid wins the 2 QU.
    
    ->	In case of a tie, each player receives 1 QU.
    
  •	Both players must pay their bid amounts, regardless of the outcome.
  
  •	The auction continues until all x QU is distributed.

**Objective**

Each player aims to collect more QU than their opponent. If both collect the same amount, the winner is the player with more remaining MU.

**Key Challenges**

  •	Budget Management: Players must balance aggressive bidding to win QU while saving enough MU for future rounds or tiebreakers.
  
  •	Predictive Strategy: Since bids are hidden, players need to anticipate the opponent’s behaviour based on past rounds.
  
  •	Fairness & Tie-Breaking: The game uses remaining MU as a tie-breaking mechanism, promoting fair competition.
  
**Possible Outcomes**

  1.	Bidder 1 wins by collecting more QU.
  2.	Bidder 2 wins by collecting more QU.
  3.	Both bidders collect equal QU, but Bidder 1 wins with more remaining MU.
  4.	Both bidders collect equal QU, but Bidder 2 wins with more remaining MU.
  5.	Both bidders end in a complete tie: equal QU and equal MU.
     
**Implemented Strategy**

The game includes two bidders:

  •	Bidder 1: Uses a strategic approach.
  
  •	Bidder 2: Uses a simple method, always bidding 20% of their remaining MU.

  **Strategic Bidder (Bidder 1)**
  
      Implemented in StrategicBidder.java, this bidder analyses different game scenarios and opponent behaviour before deciding on each bid:
          •	Round 1: Bid 10% of remaining MU.
          •	Final Round (Only 2 QU left): Bid aggressively to secure the final units.
          •	Lost Previous Round:
                ->	Become slightly cautious.
                -> Adjust bid using:  newBid = lastOwnBid + bidDifference + 3% of remaining MU
          •	Opponent is Aggressive:
                ->	Calculate opponent’s aggression level.
                ->	Increase bid by 10% over opponent’s average bid.                
          •	Behind in Quantity:
                ->	Increase bid by 12% for every QU deficit.
                ->	Example: If 2 QU behind, increase bid by 24%.                
          •	Leading in Quantity:
                ->	Save money for tiebreaker.
                ->	Reduce current bid by 70%.
                
  **Simple Bidder (Bidder 2)**
  
        Implemented in SimpleOpponentBidder.java, this bidder always bids 20% of their remaining MU, without any strategic adjustments.

        
**PROJECT STRUCTURE**

**Directory Path:** AuctionTestSolution/src/auction

**Package:** auction

**Components**

  •	Bidder.java: Interface defining the structure of a bidder.
  
  •	StrategicBidder.java: Implementation of the strategic bidder.
  
  •	SimpleOpponentBidder.java: Implementation of the simple opponent.
  
  •	AuctionTestMainClass.java: Main class to simulate auction rounds.
  
  •	StrategicBidderTest.java: Unit tests for the strategic bidder.
  
  •	SimpleOpponentBidderTest.java: Unit tests for the simple bidder.
  

**System Requirements**

  •	Java 11 or higher
  
  •	Maven or any other Java build tool
  
  •	JUnit 5 for running tests
  
**How to Execute the Program**

  1.	Open the project in your preferred Java IDE (Eclipse IDE is recommended).
  2.	Ensure all dependencies are properly configured (using Maven or your chosen build tool).
  3.	Navigate to AuctionTestMainClass.java inside the auction package.
  4.	Run the AuctionTestMainClass to simulate an auction between the strategic and simple bidders.
  5.	Preferred Execution Method:
     
        ->	Import the project into Eclipse IDE.
    	
        ->	Right-click on AuctionTestMainClass.java → Run As → Java Application.
    	
        ->	To run tests, right-click on the test classes (StrategicBidderTest.java, SimpleOpponentBidderTest.java) → Run As → JUnit Test.
    	
**Note:**
As per the problem statement, one of the bidders is intended to represent "Me." So I had designed the program accordingly, I have implemented a custom program representing myself as a strategic bidder. This bidder is designed with intelligent bidding strategies aimed at consistently winning the auction by adapting to the opponent's behaviour and the auction dynamics.
