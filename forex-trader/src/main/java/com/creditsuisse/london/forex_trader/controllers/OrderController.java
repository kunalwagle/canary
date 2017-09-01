package com.creditsuisse.london.forex_trader.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.creditsuisse.london.forex_trader.orders.ForexOrder;
import com.creditsuisse.london.forex_trader.orders.OrderError;
import com.creditsuisse.london.forex_trader.repositories.OrderRepository;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@RestController
public class OrderController {
	
	private final OrderRepository orderRepository;
	
	public OrderController(OrderRepository orderRepository) {
		this.orderRepository = orderRepository;
	}
	
	@HystrixCommand(fallbackMethod = "orderFallback")
	@RequestMapping(path="/addorder",method=RequestMethod.POST)
	public Object addOrder(@RequestBody ForexOrder order) 
	{
		OrderError errorType = generateDataErrorString(order);
		if (errorType != null) {
			return new ResponseEntity<OrderError>(errorType, HttpStatus.BAD_REQUEST);
		}
		this.orderRepository.save(order);
		return new ResponseEntity<ForexOrder>(order, HttpStatus.ACCEPTED);
	}
	
	
	@HystrixCommand(fallbackMethod = "orderFallback")
	@RequestMapping(path="/getorder/{id}",method=RequestMethod.GET)
	public Object getOrderById(@PathVariable Long id) {
		return this.orderRepository.findOne(id);
	}
	
	
	@HystrixCommand(fallbackMethod = "deleteOrderFallback")
    @RequestMapping(path="/deleteorder/{id}",method=RequestMethod.DELETE)
    public Object deleteOrderById(@PathVariable Long id) {
    	ForexOrder order = this.orderRepository.findOne(id);
    	if (order.isCompleted()) {
            return new ResponseEntity<OrderError>(OrderError.CANNOT_DELETE_COMPLETED_ORDER, HttpStatus.BAD_REQUEST);
        }   
        this.orderRepository.delete(id);
        if (!orderRepository.exists(id)) {
        	return new ResponseEntity<HttpStatus>(HttpStatus.ACCEPTED);
        }
        return null;
    }
	
	public Object orderFallback(@RequestBody ForexOrder order) {
		return new ResponseEntity<HttpStatus>(HttpStatus.SERVICE_UNAVAILABLE);
	}
	
	public Object deleteOrderFallback(@PathVariable Long id) {
		return new ResponseEntity<HttpStatus>(HttpStatus.SERVICE_UNAVAILABLE);
	}

	private OrderError generateDataErrorString(ForexOrder order) {
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
