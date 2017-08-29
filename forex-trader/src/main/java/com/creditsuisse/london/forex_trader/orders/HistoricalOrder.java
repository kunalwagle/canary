package com.creditsuisse.london.forex_trader.orders;

public class HistoricalOrder {
	
	private Currency sourceCurrency;
	private Currency destinationCurrency;
	private float price; 
	private double quantity;
	private String date;
	
	public Currency getSourceCurrency() {
		return sourceCurrency;
	}
	public void setSourceCurrency(Currency sourceCurrency) {
		this.sourceCurrency = sourceCurrency;
	}
	public Currency getDestinationCurrency() {
		return destinationCurrency;
	}
	public void setDestinationCurrency(Currency destinationCurrency) {
		this.destinationCurrency = destinationCurrency;
	}
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	public double getQuantity() {
		return quantity;
	}
	public void setQuantity(double quantity) {
		this.quantity = quantity;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	} 
	
	
	

}
