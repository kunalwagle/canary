package com.creditsuisse.london.forex_trader.orders;

public class Order {
	
	private long id;
	private int quantity;
	private float price;
	private String date;
	private Currency sourceCurrency;
	private Currency destinationCurrency;
	private BuySell buySell;
	private float total;
	
	public Order() {
		
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public Currency getSourceCurrency() {
		return sourceCurrency;
	}
	public void setsourceCurrency(Currency currency) {
		this.sourceCurrency = currency;
	}
	
	public Currency getdestinationCurrency() {
		return destinationCurrency;
	}
	public void setdestinationCurrency(Currency currency) {
		this.destinationCurrency = currency;
	}
	
	public BuySell getBuySell() {
		return buySell;
	}
	public void setBuySell(BuySell buySell) {
		this.buySell = buySell;
	}
	public float getTotal() {
		return total;
	}
	public void setTotal(float total) {
		this.total = total;
	}
	
	

}
