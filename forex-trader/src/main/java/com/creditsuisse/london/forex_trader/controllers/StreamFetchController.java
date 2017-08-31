package com.creditsuisse.london.forex_trader.controllers;

import java.io.FileInputStream;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.creditsuisse.london.forex_trader.orders.StreamOrder;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class StreamFetchController {
	
	@RequestMapping(path="/emulatedstream", method=RequestMethod.GET)
	public Object getStream() {
		ObjectMapper objectMapper = new ObjectMapper();
		JavaType type = objectMapper.getTypeFactory().constructCollectionType(List.class, StreamOrder.class);
		try {
			List <StreamOrder> result = objectMapper.readValue(new FileInputStream("./MOCK_DATA.json"), type);
			return new ResponseEntity<List<StreamOrder>>(result, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		
	}
	
}
