package com.creditsuisse.london.forex_trader.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.creditsuisse.london.forex_trader.orders.Order;

@RestController
public class OrderController {
	
	@RequestMapping(path="/placeorder",method=RequestMethod.POST)
	public Order addOrder() {
		Order order = new Order();
		order.setPrice(100);
		return order;
	}

}
