package com.promineotech.jeep.service;


import com.promineotech.jeep.entity.Order;
import com.promineotech.jeep.entity.OrderRequest;

public interface JeepOrderServivce {

  Order createOrder(OrderRequest orderRequest);

 

}
