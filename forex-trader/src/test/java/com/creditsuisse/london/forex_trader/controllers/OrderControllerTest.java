package com.creditsuisse.london.forex_trader.controllers;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.creditsuisse.london.forex_trader.orders.Order;

public class OrderControllerTest {
	
    private OrderController orderController;
    
    @Before
    public void initialise() {
    	orderController = new OrderController();
    }
    
	
	@Test
	public void randomTestToSeeIfWeCanWriteATest() {
		Order order = orderController.addOrder();
		Assert.assertEquals(order.getPrice(), 100.0, 0.0);
	}

}
