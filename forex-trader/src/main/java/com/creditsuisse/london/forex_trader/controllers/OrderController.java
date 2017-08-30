package com.creditsuisse.london.forex_trader.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.creditsuisse.london.forex_trader.orders.Order;
import com.creditsuisse.london.forex_trader.orders.OrderError;
import com.creditsuisse.london.forex_trader.repositories.OrderRepository;

@RestController
public class OrderController {
	
	private final OrderRepository orderRepository;
	
	public OrderController(OrderRepository orderRepository) {
		this.orderRepository = orderRepository;
	}
	
	@RequestMapping(path="/addorder",method=RequestMethod.POST)
	public Object addOrder(@RequestBody Order order) {
		OrderError errorType = generateDataErrorString(order);
		if (errorType != null) {
			return new ResponseEntity<OrderError>(errorType, HttpStatus.BAD_REQUEST);
		}
		orderRepository.save(order);
		return new ResponseEntity<Order>(order, HttpStatus.ACCEPTED);
	}

	private OrderError generateDataErrorString(Order order) {
		if (order.getQuantity() <= 0) {
			return OrderError.QUANTITY_ZERO;
		}
		
		if (order.getSource() == null || order.getDestination() == null) {
			return OrderError.CURRENCY_MISSING;
		}
		
		if (order.getSource() == order.getDestination()) {
			return OrderError.CURRENCY_IDENTICAL;
		}
		
		if (order.getPrice() <= 0) {
			return OrderError.PRICE_ZERO;
		}
		
		return null;
	}

}
