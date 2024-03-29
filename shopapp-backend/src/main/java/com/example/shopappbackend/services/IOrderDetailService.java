package com.example.shopappbackend.services;

import com.example.shopappbackend.dtos.OrderDetailDTO;
import com.example.shopappbackend.models.OrderDetail;

import java.util.List;

public interface IOrderDetailService {
    OrderDetail createOrderDetail(OrderDetailDTO orderDetailDTO) throws Exception;
    OrderDetail getOrderDetail(int id) throws Exception;
    OrderDetail updateOrderDetail(int id, OrderDetailDTO orderDetailDTO) throws Exception;
    void deleteOrderDetail(int id);
    List<OrderDetail> findByOrderId(int orderId);
}
