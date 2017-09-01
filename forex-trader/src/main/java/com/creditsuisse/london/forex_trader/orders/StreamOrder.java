package com.creditsuisse.london.forex_trader.orders;

public class StreamOrder {
	
	private String currencyPair;
	private String price;
	private String lotSize;
	private String date;
	
	public StreamOrder(String currencyPair, String price, String lotSize, String date) {
		super();
		this.currencyPair = currencyPair;
		this.price = price;
		this.lotSize = lotSize;
		this.date = date;
	}

	public StreamOrder() {
		super();
	}

	public String getCurrencyPair() {
		return currencyPair;
	}

	public void setCurrencyPair(String currencyPair) {
		this.currencyPair = currencyPair;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getLotSize() {
		return lotSize;
	}

	public void setLotSize(String lotSize) {
		this.lotSize = lotSize;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}
	
	
	
	
	
}