package com.creditsuisse.london.forex_trader.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.creditsuisse.london.forex_trader.orders.ForexOrder;
import com.creditsuisse.london.forex_trader.orders.OrderError;
import com.creditsuisse.london.forex_trader.repositories.OrderRepository;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import services.OrderRepositoryService;

@RestController
public class OrderController {
	
	private final OrderRepositoryService orderRepositoryService;

	
	public OrderController(OrderRepositoryService orderRepositoryService) {
		this.orderRepositoryService = orderRepositoryService;
	}
	
	@RequestMapping(path="/addorder",method=RequestMethod.POST)
	public Object addOrder(@RequestBody ForexOrder order) 
	{
		return new ResponseEntity<Object>(orderRepositoryService.addOrder(order), HttpStatus.OK);
	}
	

	@RequestMapping(path="/getorder/{id}",method=RequestMethod.GET)
	public Object getOrderById(@PathVariable Long id) {
		return new ResponseEntity<ForexOrder>(this.orderRepositoryService.getOrderById(id),HttpStatus.OK);
	}
	

    @RequestMapping(path="/deleteorder/{id}",method=RequestMethod.DELETE)
    public Object deleteOrderById(@PathVariable Long id) {
    	return new ResponseEntity<Object>(orderRepositoryService.deleteOrderById(id), HttpStatus.OK);
    }
	

	

}
