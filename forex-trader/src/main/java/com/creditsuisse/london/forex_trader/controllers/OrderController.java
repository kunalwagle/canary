package com.creditsuisse.london.forex_trader.controllers;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
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
import com.creditsuisse.london.forex_trader.orders.StreamOrder;
import com.creditsuisse.london.forex_trader.repositories.OrderRepository;

@RestController
public class OrderController {

	private OrderRepository orderRepository;

	public OrderController(OrderRepository orderRepository) {
		this.orderRepository = orderRepository;
	}

	@Autowired
	Environment environment;

	@RequestMapping(path = "/addorder", method = RequestMethod.POST)
	public Object addOrder(@RequestBody ForexOrder order) {
		OrderError errorType = generateDataErrorString(order);
		if (errorType != null) {
			return new ResponseEntity<OrderError>(errorType, HttpStatus.BAD_REQUEST);
		}
		this.orderRepository.save(order);
		return new ResponseEntity<ForexOrder>(order, HttpStatus.ACCEPTED);
	}

	@RequestMapping(path = "/getorder/{id}", method = RequestMethod.GET)
	public Object getOrderById(@PathVariable Long id) {
		return this.orderRepository.findOne(id);
	}

	private static OrderError generateDataErrorString(ForexOrder order) {
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

	@SuppressWarnings("unchecked")
	public static boolean matchOrders(ForexOrder order) {
		List<StreamOrder> stream = ((ResponseEntity<List<StreamOrder>>) new StreamFetchController().getStream()).getBody();
		stream = findMostRecentCurrencyPairing(stream, order.getSource() + "/" + order.getDestination());
		stream = lowerPricedTrades(stream, order.getPrice());
		stream = orderTrades(stream);
		return !stream.isEmpty();
	}

	public static List<StreamOrder> lowerPricedTrades(List<StreamOrder> streamOrders, float price) {
		List<StreamOrder> result = streamOrders.stream().filter(order -> Double.parseDouble(order.getPrice()) < price)
				.collect(Collectors.toList());
		Collections.sort(result, new Comparator<StreamOrder>() {

			@Override
			public int compare(StreamOrder o1, StreamOrder o2) {
				Double price1 = Double.parseDouble(o1.getPrice());
				Double price2 = Double.parseDouble(o2.getPrice());
				return price2.compareTo(price1);
			}

		});

		return result;
	}

	public static List<StreamOrder> orderTrades(List<StreamOrder> streamOrders) {

		List<DatedStreamOrder> datedOrders = streamOrders.stream().map(DatedStreamOrder::new)
				.collect(Collectors.toList());

		Collections.sort(datedOrders);

		return datedOrders.stream().map(DatedStreamOrder::getStreamOrder).collect(Collectors.toList());
	}

	private static class DatedStreamOrder implements Comparable<DatedStreamOrder> {

		private StreamOrder streamOrder;
		private Date date;

		public DatedStreamOrder(StreamOrder streamOrder) {
			super();
			this.streamOrder = streamOrder;
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-ddHH:mm:ss");
			Date date;
			try {
				date = dateFormat.parse(streamOrder.getDate());
				this.date = date;
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}

		public StreamOrder getStreamOrder() {
			return streamOrder;
		}

		public Date getDate() {
			return date;
		}

		@Override
		public int compareTo(DatedStreamOrder o) {
			return o.getDate().compareTo(date);
		}

	}

	public static List<StreamOrder> findMostRecentCurrencyPairing(List<StreamOrder> streamOrders, String currencyPair) {
		return streamOrders.stream().filter(order -> order.getCurrencyPair().equals(currencyPair))
				.collect(Collectors.toList());
	}

}
