package com.example.shopappbackend.services;

import com.example.shopappbackend.dtos.OrderDetailDTO;
import com.example.shopappbackend.exceptions.DataNotFoundException;
import com.example.shopappbackend.models.Order;
import com.example.shopappbackend.models.OrderDetail;
import com.example.shopappbackend.models.Product;
import com.example.shopappbackend.repositories.OrderDetailRepository;
import com.example.shopappbackend.repositories.OrderRepository;
import com.example.shopappbackend.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderDetailService implements IOrderDetailService{
    private final OrderDetailRepository orderDetailRepository;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    @Override
    public OrderDetail createOrderDetail(OrderDetailDTO orderDetailDTO) throws Exception {
        Order order = orderRepository.findById(orderDetailDTO.getOrderId()).orElse(null);
        if (order == null){
            throw new DataNotFoundException("Cannot find order with id = " + orderDetailDTO.getOrderId());
        }
        Product product = productRepository.findById(orderDetailDTO.getProductId()).orElse(null);
        if (product == null){
            throw new DataNotFoundException("Cannot find product with id = " + orderDetailDTO.getProductId());
        }
        OrderDetail orderDetail = OrderDetail.builder()
                .order(order)
                .product(product)
                .price(orderDetailDTO.getPrice())
                .numberOfProducts(orderDetailDTO.getNumberOfProducts())
                .totalMoney(orderDetailDTO.getTotalMoney())
                .color(orderDetailDTO.getColor())
                .build();
        orderDetailRepository.save(orderDetail);
        return orderDetail;
    }

    @Override
    public OrderDetail getOrderDetail(int id) throws Exception {
        return orderDetailRepository.findById(id).orElseThrow(() -> new DataNotFoundException("Cannot find orderDetail with id = " + id));
    }

    @Override
    public OrderDetail updateOrderDetail(int id, OrderDetailDTO orderDetailDTO) throws Exception {
        OrderDetail existingOrderDetail = orderDetailRepository.findById(id).orElse(null);
        if (existingOrderDetail == null){
            throw new DataNotFoundException("Cannot find order detail with id = " + id);
        }
        Order existingOrder = orderRepository.findById(orderDetailDTO.getOrderId()).orElse(null);
        if (existingOrder == null){
            throw new DataNotFoundException("Cannot find order with id = " + orderDetailDTO.getOrderId());
        }
        Product existingProduct = productRepository.findById(orderDetailDTO.getProductId()).orElse(null);
        if (existingProduct == null){
            throw new DataNotFoundException("Cannot find product with id = " + orderDetailDTO.getProductId());
        }
        existingOrderDetail.setOrder(existingOrder);
        existingOrderDetail.setProduct(existingProduct);
        existingOrderDetail.setPrice(orderDetailDTO.getPrice());
        existingOrderDetail.setNumberOfProducts(orderDetailDTO.getNumberOfProducts());
        existingOrderDetail.setTotalMoney(orderDetailDTO.getTotalMoney());
        existingOrderDetail.setColor(orderDetailDTO.getColor());
        return orderDetailRepository.save(existingOrderDetail);
    }

    @Override
    public void deleteOrderDetail(int id) {
        orderDetailRepository.deleteById(id);
    }

    @Override
    public List<OrderDetail> findByOrderId(int orderId) {
        return orderDetailRepository.findByOrderId(orderId);
    }
}
