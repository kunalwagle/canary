package com.creditsuisse.london.forex_trader.controllers;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.creditsuisse.london.forex_trader.orders.StreamOrder;

@RestController
public class StreamController {
	
	@Autowired
	Environment environment;
	
	@RequestMapping(path = "/stream/{start}/{end}", method = RequestMethod.GET)
	public List<StreamOrder> getOrders(@PathVariable String start, @PathVariable String end) {
		ResponseEntity<StreamOrder[]> orders = new RestTemplate().getForEntity("http://localhost:" + environment.getProperty("local.server.port") + "/emulatedstream", StreamOrder[].class);
		List<StreamOrder> stream = new ArrayList<StreamOrder>(Arrays.asList(orders.getBody()));
		return stream.stream().filter(order -> withinRange(order.getDate(), start, end)).collect(Collectors.toList());
	}
	
	private boolean withinRange(String date, String start, String end) {
		DateFormat smallFormat = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date originalDate = smallFormat.parse(date.substring(0, 10));	
			Date startDate = smallFormat.parse(start);
			Date endDate = smallFormat.parse(end);
			return !originalDate.before(startDate) && !originalDate.after(endDate);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
}
