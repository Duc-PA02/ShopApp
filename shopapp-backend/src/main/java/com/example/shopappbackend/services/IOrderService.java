package com.example.shopappbackend.services;

import com.example.shopappbackend.dtos.CategoryDTO;
import com.example.shopappbackend.dtos.OrderDTO;
import com.example.shopappbackend.models.Category;
import com.example.shopappbackend.models.Order;
import com.example.shopappbackend.responses.order.OrderResponse;

import java.util.List;

public interface IOrderService {
    Order createOrder(OrderDTO orderDTO) throws Exception;
    Order updateOrder(int id, OrderDTO orderDTO) throws Exception;
    void deleteOrder(int id);
    Order getOrder(int id);
    List<Order> findByUserId(int userId);
}
