package model;

class CardsTradeMove extends PlayerMove {
	private static class TradeCounter implements MatchStartListener {
		public int tradeCount = 0;
		
		@Override 
		public void onMatchStart(Match m) {
			tradeCount = 0;
		}
		
		public TradeCounter() {
			Match.addMatchStartListener(this);
		}
	}
	private static TradeCounter tradeCounter = new TradeCounter();
	
	private static int[] tradeBonusAmount = new int[] {
		4, 6, 8, 10, 12, 15
	};
	
	/**
	 * Returns true if the specified trio of cards is either made of three distinct cards or three
	 * equal cards. 
	 */
	private static boolean evaluateCardTrioBonus(TerritoryCard c1, TerritoryCard c2, TerritoryCard c3) {		
        if (c1.getGeometry() != c2.getGeometry() && c1.getGeometry() != c3.getGeometry()) {
            return true;
        }
        if (c1.getGeometry() == c2.getGeometry() && c1.getGeometry() == c3.getGeometry()) {
            return true;
        }
        return false;
    }
	
	public void tradeTerritoryCards(TerritoryCard card1, TerritoryCard card2, TerritoryCard card3) {
		if (evaluateCardTrioBonus(card1, card2, card3)) {			
			if (player.ownsTerritory(card1.getTerritory())) {
				card1.getTerritory().addSoldiers(2);
			}
			if (player.ownsTerritory(card2.getTerritory())) {
				card2.getTerritory().addSoldiers(2);
			}
			if (player.ownsTerritory(card3.getTerritory())) {
				card3.getTerritory().addSoldiers(2);
			}
			
			int bonus;
			
			if (tradeCounter.tradeCount >= tradeBonusAmount.length) {
				bonus = tradeBonusAmount[tradeBonusAmount.length - 1] + (tradeCounter.tradeCount - tradeBonusAmount.length + 1) * 5;
			} else {
				bonus = tradeBonusAmount[tradeCounter.tradeCount];
			}	
			
			player.addSoldiers(bonus);

			tradeCounter.tradeCount++;
		}
	}
	
	public CardsTradeMove(Player p) {
		super(p);
	}
}