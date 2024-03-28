package com.example.shopappbackend.services;

import com.example.shopappbackend.dtos.CategoryDTO;
import com.example.shopappbackend.dtos.OrderDTO;
import com.example.shopappbackend.models.Category;
import com.example.shopappbackend.models.Order;
import com.example.shopappbackend.responses.order.OrderResponse;

import java.util.List;

public interface IOrderService {
    OrderResponse createOrder(OrderDTO orderDTO) throws Exception;
    OrderResponse updateOrder(int id, OrderDTO orderDTO);
    void deleteOrder(int id);
    OrderResponse getOrderById(int id);
    List<OrderResponse> getAllOrders(int userId);
}
