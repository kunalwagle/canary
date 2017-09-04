package com.creditsuisse.london.forex_trader.controllers;

import java.util.ArrayList;
import java.util.List;

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
import com.creditsuisse.london.forex_trader.orders.ForexOrder;
import com.creditsuisse.london.forex_trader.orders.OrderError;
import com.creditsuisse.london.forex_trader.orders.StreamOrder;
import com.creditsuisse.london.forex_trader.orders.TradeType;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class OrderControllerTest {
    
    private ForexOrder happyMarketOrder;
    private ForexOrder happyLimitOrder;
    
    @LocalServerPort
    private int port;
 
    @Before
    public void initialise() {
    	RestAssured.port = port;
    	happyMarketOrder = new ForexOrder(3, 10, "2015-10-04_18:00:00.050", Currency.USD, Currency.GBP, TradeType.MARKET, BuySell.SELL);
    	happyLimitOrder = new ForexOrder(6, 22, "2016-10-09_13:14:00.050", Currency.GBP, Currency.USD, TradeType.LIMIT, BuySell.BUY);
    }
    
    private void ordersMatch(ForexOrder order, ForexOrder happyOrder) {
    	Assert.assertEquals(order.getPrice(), happyOrder.getPrice(), 0.0);
		Assert.assertEquals(order.getQuantity(), happyOrder.getQuantity(), 0);
		Assert.assertEquals(order.getTradeDate(), happyOrder.getTradeDate());
		Assert.assertEquals(order.getBuySell(), happyOrder.getBuySell());
		Assert.assertEquals(order.getDestination(), happyOrder.getDestination());
		Assert.assertEquals(order.getSource(), happyOrder.getSource());
		Assert.assertEquals(order.getTradeType(), happyOrder.getTradeType());
    }
	
	@Test
	public void returnsCorrectOrderForValidDataForMarketOrder() {
		ForexOrder order = RestAssured.given()
		.contentType(ContentType.JSON)
		.body(happyMarketOrder)
		.when()
		.post("/addorder")
		.as(ForexOrder.class);
		
		ordersMatch(order, happyMarketOrder);
		
	}
	
	@Test
	public void quantityFailsWhenZeroForMarketOrder() {
		happyMarketOrder.setQuantity(0);
		OrderError error = RestAssured.given()
		.contentType(ContentType.JSON)
		.body(happyMarketOrder)
		.when()
		.post("/addorder")
		.as(OrderError.class);
		Assert.assertEquals(error, OrderError.QUANTITY_ZERO);
	}
	
	@Test
	public void sourceCurrencyMustExistForMarketOrder() {
		happyMarketOrder.setSource(null);
		OrderError error = RestAssured.given()
		.contentType(ContentType.JSON)
		.body(happyMarketOrder)
		.when()
		.post("/addorder")
		.as(OrderError.class);
		Assert.assertEquals(error, OrderError.CURRENCY_MISSING);
	}
	
	@Test
	public void destinationCurrencyMustExistForMarketOrder() {
		happyMarketOrder.setDestination(null);
		OrderError error = RestAssured.given()
		.contentType(ContentType.JSON)
		.body(happyMarketOrder)
		.when()
		.post("/addorder")
		.as(OrderError.class);
		Assert.assertEquals(error, OrderError.CURRENCY_MISSING);
	}
	
	@Test
	public void currenciesCantBeIdenticalForMarketOrder() {
		happyMarketOrder.setDestination(Currency.USD);
		OrderError error = RestAssured.given()
		.contentType(ContentType.JSON)
		.body(happyMarketOrder)
		.when()
		.post("/addorder")
		.as(OrderError.class);
		Assert.assertEquals(error, OrderError.CURRENCY_IDENTICAL);
	}
	
	@Test
	public void priceMustBeGreaterThanZeroForMarketOrder() {
		happyMarketOrder.setPrice(0);
		OrderError error = RestAssured.given()
		.contentType(ContentType.JSON)
		.body(happyMarketOrder)
		.when()
		.post("/addorder")
		.as(OrderError.class);
		Assert.assertEquals(error, OrderError.PRICE_ZERO);
	}
	
	@Test
	public void returnsCorrectOrderForValidDataForLimitOrder() {
		ForexOrder order = RestAssured.given()
		.contentType(ContentType.JSON)
		.body(happyLimitOrder)
		.when()
		.post("/addorder")
		.as(ForexOrder.class);
		
		ordersMatch(order, happyLimitOrder);
	}
	
	@Test
	public void quantityFailsWhenZeroForLimitOrder() {
		happyMarketOrder.setQuantity(0);
		OrderError error = RestAssured.given()
		.contentType(ContentType.JSON)
		.body(happyMarketOrder)
		.when()
		.post("/addorder")
		.as(OrderError.class);
		Assert.assertEquals(error, OrderError.QUANTITY_ZERO);
	}
	
	@Test
	public void sourceCurrencyMustExistForLimitOrder() {
		happyMarketOrder.setSource(null);
		OrderError error = RestAssured.given()
		.contentType(ContentType.JSON)
		.body(happyMarketOrder)
		.when()
		.post("/addorder")
		.as(OrderError.class);
		Assert.assertEquals(error, OrderError.CURRENCY_MISSING);
	}
	
	@Test
	public void destinationCurrencyMustExistForLimitOrder() {
		happyMarketOrder.setDestination(null);
		OrderError error = RestAssured.given()
		.contentType(ContentType.JSON)
		.body(happyMarketOrder)
		.when()
		.post("/addorder")
		.as(OrderError.class);
		Assert.assertEquals(error, OrderError.CURRENCY_MISSING);
	}
	
	@Test
	public void currenciesCantBeIdenticalForLimitOrder() {
		happyMarketOrder.setDestination(Currency.USD);
		OrderError error = RestAssured.given()
		.contentType(ContentType.JSON)
		.body(happyMarketOrder)
		.when()
		.post("/addorder")
		.as(OrderError.class);
		Assert.assertEquals(error, OrderError.CURRENCY_IDENTICAL);
	}
	
	@Test
	public void priceMustBeGreaterThanZeroForLimitOrder() {
		happyMarketOrder.setPrice(0);
		OrderError error = RestAssured.given()
		.contentType(ContentType.JSON)
		.body(happyMarketOrder)
		.when()
		.post("/addorder")
		.as(OrderError.class);
		Assert.assertEquals(error, OrderError.PRICE_ZERO);
	}
	
	@Test
	public void ordersTradesByDateSuccessfully() {
		List<StreamOrder> streamOrders = new ArrayList<>();
		streamOrders.add(new StreamOrder("GBP/USD", "1000", "80", "2017-06-0114:00:00"));
		streamOrders.add(new StreamOrder("GBP/USD", "1000", "80", "2017-06-0714:00:00"));
		streamOrders.add(new StreamOrder("GBP/USD", "1000", "80", "2017-06-0514:00:00"));
		List<StreamOrder> result = OrderController.orderTrades(streamOrders);
		Assert.assertEquals("2017-06-0714:00:00", result.get(0).getDate());
		Assert.assertEquals("2017-06-0514:00:00", result.get(1).getDate());
		Assert.assertEquals("2017-06-0114:00:00", result.get(2).getDate());
	}
	
	@Test
	public void findsMostRecentCurrencyPairing() {
		List<StreamOrder> streamOrders = new ArrayList<>();
		streamOrders.add(new StreamOrder("GBP/USD", "1000", "80", "2017-06-0714:00:00"));
		streamOrders.add(new StreamOrder("EUR/GBP", "1000", "80", "2017-06-0514:00:00"));
		streamOrders.add(new StreamOrder("EUR/GBP", "1000", "80", "2017-06-0114:00:00"));
		List<StreamOrder> streamOrder = OrderController.findMostRecentCurrencyPairing(streamOrders, "EUR/GBP");
		Assert.assertEquals(false, streamOrder.contains(streamOrders.get(0)));
		Assert.assertEquals(2, streamOrder.size());
	}
	
	@Test
	public void findsLowerPrices() {
		List<StreamOrder> streamOrders = new ArrayList<>();
		streamOrders.add(new StreamOrder("EUR/GBP", "490", "80", "2017-06-0714:00:00"));
		streamOrders.add(new StreamOrder("EUR/GBP", "850", "80", "2017-06-0514:00:00"));
		streamOrders.add(new StreamOrder("EUR/GBP", "600", "80", "2017-06-0114:00:00"));
		List<StreamOrder> streamOrder = OrderController.lowerPricedTrades(streamOrders, 800);
		Assert.assertEquals(false, streamOrder.contains(streamOrders.get(1)));
		Assert.assertEquals(2, streamOrder.size());
		Assert.assertEquals(streamOrder.get(0), streamOrders.get(2));
	}
	
	@Test
	public void matchesLimitOrdersThatExceedCurrentLowestAskPrice() {
		boolean orderMatches = OrderController.matchOrders(happyLimitOrder);
		Assert.assertEquals(true, orderMatches);
	}
	
	@Test
	public void doesntMatchLimitOrdersIfThereAreNoneCheaper() {
		happyLimitOrder.setPrice(0);
		boolean orderMatches = OrderController.matchOrders(happyLimitOrder);
		Assert.assertEquals(false, orderMatches);
	}
	
	
	//Integration Tests
	
	@Test
	public void insertMarketOrderSuccessfully() {
		ForexOrder order = RestAssured.given()
				.contentType(ContentType.JSON)
				.body(happyMarketOrder)
				.when()
				.post("/addorder")
				.as(ForexOrder.class);
		long id = order.getId();
		ForexOrder receivedOrder = RestAssured.given()
				.contentType(ContentType.JSON)
				.when()
				.get("/getorder/" + id)
				.as(ForexOrder.class);
		ordersMatch(receivedOrder, order);
		Assert.assertEquals(id, receivedOrder.getId());
	}
	
	@Test
	public void insertLimitOrderSuccessfully() {
		ForexOrder order = RestAssured.given()
				.contentType(ContentType.JSON)
				.body(happyLimitOrder)
				.when()
				.post("/addorder")
				.as(ForexOrder.class);
		long id = order.getId();
		ForexOrder receivedOrder = RestAssured.given()
				.contentType(ContentType.JSON)
				.when()
				.get("/getorder/" + id)
				.as(ForexOrder.class);
		ordersMatch(receivedOrder, order);
		Assert.assertEquals(id, receivedOrder.getId());
	}
	

}
