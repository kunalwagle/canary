package com.creditsuisse.london.forex_trader.orders;

public enum Type {
	MARKET("market"),
	LIMIT("limit");
	
	private String type;
	
	private Type(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}
}
