package com.creditsuisse.london.forex_trader;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.creditsuisse.london.forex_trader.controllers.OrderController;
import com.creditsuisse.london.forex_trader.orders.BuySell;
import com.creditsuisse.london.forex_trader.orders.Currency;
import com.creditsuisse.london.forex_trader.orders.ForexOrder;
import com.creditsuisse.london.forex_trader.orders.TradeType;

/**
 * Hello world!
 *
 */
@SpringBootApplication
public class App 
{
    public static void main( String[] args )
    {
        SpringApplication.run(App.class, args);
    }
    
}
