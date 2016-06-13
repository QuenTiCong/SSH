package com.java.service;

import java.util.List;

import com.java.entity.Order;
import com.java.entity.PageBean;

public interface OrderService {

    public void saveOrder(Order order);
	
	public List<Order> findOrder(Order s_order,PageBean pageBean);
	
	public Long getOrderCount(Order s_order);
	
	public void updateOrderStatus(int status,String orderNo);
	
	public Order getOrderById(int id);
}
