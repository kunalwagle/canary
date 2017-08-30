package com.creditsuisse.london.forex_trader.controllers;

import org.apache.http.HttpStatus;
import org.apache.http.impl.bootstrap.HttpServer;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

import com.creditsuisse.london.forex_trader.App;
import com.creditsuisse.london.forex_trader.orders.BuySell;
import com.creditsuisse.london.forex_trader.orders.Currency;
import com.creditsuisse.london.forex_trader.orders.Order;
import com.creditsuisse.london.forex_trader.orders.OrderError;
import com.creditsuisse.london.forex_trader.orders.Type;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class OrderControllerTest {
    
    private Order happyOrder;
    
    @LocalServerPort
    private int port;
 
    @Before
    public void initialise() {
    	RestAssured.port = port;
    	happyOrder = new Order(3, 10, "2015-10-04_18:00:00.050", Currency.USD, Currency.GBP, Type.MARKET, BuySell.BUY);
    }
	
	@Test
	public void returnsCorrectOrderForValidData() {
		Order order = RestAssured.given()
		.contentType(ContentType.JSON)
		.body(happyOrder)
		.when()
		.post("/addorder")
		.as(Order.class);
		
		Assert.assertEquals(order.getPrice(), happyOrder.getPrice(), 0.0);
		Assert.assertEquals(order.getQuantity(), happyOrder.getQuantity(), 0);
		Assert.assertEquals(order.getDate(), happyOrder.getDate());
		Assert.assertEquals(order.getBuySell(), happyOrder.getBuySell());
		Assert.assertEquals(order.getDestination(), happyOrder.getDestination());
		Assert.assertEquals(order.getSource(), happyOrder.getSource());
		Assert.assertEquals(order.getType(), happyOrder.getType());
	}
	
	@Test
	public void quantityFailsWhenZero() {
		happyOrder.setQuantity(0);
		OrderError error = RestAssured.given()
		.contentType(ContentType.JSON)
		.body(happyOrder)
		.when()
		.post("/addorder")
		.as(OrderError.class);
		Assert.assertEquals(error, OrderError.QUANTITY_ZERO);
	}
	
	@Test
	public void sourceCurrencyMustExist() {
		happyOrder.setSource(null);
		OrderError error = RestAssured.given()
		.contentType(ContentType.JSON)
		.body(happyOrder)
		.when()
		.post("/addorder")
		.as(OrderError.class);
		Assert.assertEquals(error, OrderError.CURRENCY_MISSING);
	}
	
	@Test
	public void destinationCurrencyMustExist() {
		happyOrder.setDestination(null);
		OrderError error = RestAssured.given()
		.contentType(ContentType.JSON)
		.body(happyOrder)
		.when()
		.post("/addorder")
		.as(OrderError.class);
		Assert.assertEquals(error, OrderError.CURRENCY_MISSING);
	}
	
	@Test
	public void currenciesCantBeIdentical() {
		happyOrder.setDestination(Currency.USD);
		OrderError error = RestAssured.given()
		.contentType(ContentType.JSON)
		.body(happyOrder)
		.when()
		.post("/addorder")
		.as(OrderError.class);
		Assert.assertEquals(error, OrderError.CURRENCY_IDENTICAL);
	}
	
	@Test
	public void priceMustBeGreaterThanZero() {
		happyOrder.setPrice(0);
		OrderError error = RestAssured.given()
		.contentType(ContentType.JSON)
		.body(happyOrder)
		.when()
		.post("/addorder")
		.as(OrderError.class);
		Assert.assertEquals(error, OrderError.PRICE_ZERO);
	}

}
