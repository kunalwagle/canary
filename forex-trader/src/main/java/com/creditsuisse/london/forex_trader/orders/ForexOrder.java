package com.creditsuisse.london.forex_trader.orders;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class ForexOrder implements Serializable {

	private static final long serialVersionUID = 864528648383226476L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	
	private int quantity;
	private float price;
	private String tradeDate;
	private boolean isCompleted;
	
	@Enumerated(EnumType.STRING)
	private Currency source;
	@Enumerated(EnumType.STRING)
	private Currency destination;
	@Enumerated(EnumType.STRING)
	private TradeType tradeType;
	@Enumerated(EnumType.STRING)
	private BuySell buySell;
	
	public ForexOrder() {
		super();
	}
	
	public ForexOrder(int quantity, float price, String tradeDate, Currency source, Currency destination, TradeType tradeType,
			BuySell buySell, boolean isCompleted) {
		this();
		this.quantity = quantity;
		this.price = price;
		this.tradeDate = tradeDate;
		this.source = source;
		this.destination = destination;
		this.tradeType = tradeType;
		this.buySell = buySell;
		this.setCompleted(isCompleted);
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

	public String getTradeDate() {
		return tradeDate;
	}

	public void setTradeDate(String tradeDate) {
		this.tradeDate = tradeDate;
	}

	public Currency getSource() {
		return source;
	}

	public void setSource(Currency source) {
		this.source = source;
	}

	public Currency getDestination() {
		return destination;
	}

	public void setDestination(Currency destination) {
		this.destination = destination;
	}

	public TradeType getTradeType() {
		return tradeType;
	}

	public void setTradeType(TradeType tradeType) {
		this.tradeType = tradeType;
	}

	public BuySell getBuySell() {
		return buySell;
	}

	public void setBuySell(BuySell buySell) {
		this.buySell = buySell;
	}

	public boolean isCompleted() {
		return isCompleted;
	}

	public void setCompleted(boolean isCompleted) {
		this.isCompleted = isCompleted;
	}


	
	

}
