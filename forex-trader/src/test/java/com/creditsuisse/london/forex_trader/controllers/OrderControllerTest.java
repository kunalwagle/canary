package com.creditsuisse.london.forex_trader.controllers;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.creditsuisse.london.forex_trader.App;

import io.restassured.RestAssured;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class OrderControllerTest {
	
	@Autowired
    private MockMvc mockMvc;
	
	@Value("${local.server.port}")
	private int serverPort;

	@Before
	public void init() {
		RestAssured.port = serverPort;
	}
	
	@Test
	public void randomTestToSeeIfWeCanWriteATest() {
		given()
	}
	
	@Test
	public void returnsNullWhenCalled() {
		
	}

}
