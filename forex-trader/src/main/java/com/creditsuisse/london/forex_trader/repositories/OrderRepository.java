package com.creditsuisse.london.forex_trader.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.creditsuisse.london.forex_trader.orders.ForexOrder;

//@Repository
public interface OrderRepository extends CrudRepository<ForexOrder, Long> {

}
