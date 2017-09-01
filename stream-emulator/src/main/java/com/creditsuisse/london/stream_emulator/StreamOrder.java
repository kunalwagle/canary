package com.creditsuisse.london.stream_emulator;

public class StreamOrder {
	
	private String currencyPair;
	private String price;
	private String lotSize;
	private String time;
	
	public StreamOrder(String currencyPair, String price, String lotSize, String time) {
		super();
		this.currencyPair = currencyPair;
		this.price = price;
		this.lotSize = lotSize;
		this.time = time;
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

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}
	
	
	
	
	
}