package com.creditsuisse.london.forex_trader.orders;

import java.io.Serializable;

public class Order implements Serializable {

	private static final long serialVersionUID = 1L;

	private long id;
	private int quantity;
	private float price;
	private String date;
	private Currency source;
	private Currency destination;
	private Type type;
	private BuySell buySell;
	
	public Order() {
		
	}
	
	public Order(int quantity, float price, String date, Currency source, Currency destination, Type type,
			BuySell buySell) {
		super();
		this.quantity = quantity;
		this.price = price;
		this.date = date;
		this.source = source;
		this.destination = destination;
		this.type = type;
		this.buySell = buySell;
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
	public Currency getSource() {
		return source;
	}
	public void setSource(Currency currency) {
		this.source = currency;
	}
	public Currency getDestination() {
		return destination;
	}
	public void setDestination(Currency currency) {
		this.destination = currency;
	}
	public BuySell getBuySell() {
		return buySell;
	}
	public void setBuySell(BuySell buySell) {
		this.buySell = buySell;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}
	
	

}
