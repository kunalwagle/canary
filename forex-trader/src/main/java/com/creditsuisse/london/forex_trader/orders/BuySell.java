package com.creditsuisse.london.forex_trader.orders;

public enum BuySell {
	BUY("buy"),
	SELL("sell");
	
	private String type;
	
	private BuySell(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}
}
