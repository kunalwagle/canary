package com.creditsuisse.london.forex_trader.controllers;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.creditsuisse.london.forex_trader.orders.BuySell;
import com.creditsuisse.london.forex_trader.orders.Currency;
import com.creditsuisse.london.forex_trader.orders.Order;
import com.creditsuisse.london.forex_trader.orders.Type;

public class OrderControllerTest {
	
    private OrderController orderController;
    
    private final Order happyOrder = new Order(3, 10, "2015-10-04_18:00:00.050", Currency.USD, Currency.GBP, Type.MARKET, BuySell.BUY);
    
    
    
    @Before
    public void initialise() {
    	orderController = new OrderController();
    }
	
	@Test
	public void randomTestToSeeIfWeCanWriteATest() {
		Order order = orderController.addOrder(happyOrder);
		Assert.assertEquals(order.getPrice(), happyOrder.getPrice(), 0.0);
		Assert.assertEquals(order.getQuantity(), happyOrder.getQuantity(), 0);
		Assert.assertEquals(order.getDate(), happyOrder.getDate());
		Assert.assertEquals(order.getBuySell(), happyOrder.getBuySell());
		Assert.assertEquals(order.getDestination(), happyOrder.getDestination());
		Assert.assertEquals(order.getSource(), happyOrder.getSource());
		Assert.assertEquals(order.getType(), happyOrder.getType());
	}

}
