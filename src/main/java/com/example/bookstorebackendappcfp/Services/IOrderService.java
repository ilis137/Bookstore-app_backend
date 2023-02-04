package com.example.bookstorebackendappcfp.Services;

import com.example.bookstorebackendappcfp.DTO.OrderDTO;
import com.example.bookstorebackendappcfp.Exception.BookException;
import com.example.bookstorebackendappcfp.Exception.CartException;
import com.example.bookstorebackendappcfp.Exception.OrderException;
import com.example.bookstorebackendappcfp.Exception.UserException;

import java.util.List;

public interface IOrderService {



    public List<OrderDTO> placeOrder(OrderDTO orderDTO, String authHeader) throws UserException, CartException, BookException;


    public List<OrderDTO> getAll();


    public List<OrderDTO> getByUserId(String authHeader) throws UserException;


    public OrderDTO cancelOrder(Long orderId) throws OrderException;

    boolean deleteById(Long orderId) throws OrderException;

    OrderDTO updateByOrderId(Long orderId, OrderDTO orderDTO) throws OrderException;
}
