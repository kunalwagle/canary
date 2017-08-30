package com.creditsuisse.london.forex_trader.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.creditsuisse.london.forex_trader.orders.Order;
import com.creditsuisse.london.forex_trader.orders.OrderError;

@RestController
public class OrderController {
	
	@RequestMapping(path="/addorder",method=RequestMethod.POST)
	public Object addOrder(@RequestBody Order order) {
		OrderError errorType = generateDataErrorString(order);
		if (errorType != null) {
			return new ResponseEntity<OrderError>(errorType, HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<Order>(order, HttpStatus.ACCEPTED);
	}

	private OrderError generateDataErrorString(Order order) {
		if (order.getQuantity() < 1) {
			return OrderError.QUANTITY_ZERO;
		}
		
		return null;
	}

}
