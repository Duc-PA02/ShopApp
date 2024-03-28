package com.example.shopappbackend.services;

import com.example.shopappbackend.dtos.OrderDTO;
import com.example.shopappbackend.exceptions.DataNotFoundException;
import com.example.shopappbackend.models.Order;
import com.example.shopappbackend.models.OrderStatus;
import com.example.shopappbackend.models.User;
import com.example.shopappbackend.repositories.OrderRepository;
import com.example.shopappbackend.repositories.UserRepository;
import com.example.shopappbackend.responses.order.OrderResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService implements IOrderService{
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    @Override
    public OrderResponse createOrder(OrderDTO orderDTO) throws Exception {
        User user = userRepository.findById(orderDTO.getUserId()).orElseThrow(() -> new DataNotFoundException("Cannot found user with id = " + orderDTO.getUserId()));
        //convert OrderDTO => Order
        //dùng thư viện ModelMapper
        modelMapper.typeMap(OrderDTO.class, Order.class).addMappings(mapper -> mapper.skip(Order::setId));
        Order order = new Order();
        modelMapper.map(orderDTO, order);
        order.setUser(user);
        order.setOrderDate(LocalDateTime.now());
        order.setStatus(OrderStatus.PENDING);
        //kiểm tra shipping date phải >= ngày hôm nay
        LocalDate shippingDate = orderDTO.getShippingDate() == null
                ? LocalDate.now() : orderDTO.getShippingDate();
        if (shippingDate.isBefore(LocalDate.now())){
            throw new DataNotFoundException("Date must be at least today");
        }
        order.setShippingDate(shippingDate);
        order.setActive(true);
        orderRepository.save(order);
        modelMapper.typeMap(Order.class, OrderResponse.class);
        return modelMapper.map(order, OrderResponse.class);
    }

    @Override
    public OrderResponse updateOrder(int id, OrderDTO orderDTO) {
        return null;
    }

    @Override
    public void deleteOrder(int id) {

    }

    @Override
    public OrderResponse getOrderById(int id) {
        return null;
    }

    @Override
    public List<OrderResponse> getAllOrders(int userId) {
        return null;
    }
}
