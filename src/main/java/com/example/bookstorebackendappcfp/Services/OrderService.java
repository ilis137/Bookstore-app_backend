package com.example.bookstorebackendappcfp.Services;


import com.example.bookstorebackendappcfp.DTO.CartResponseDTO;
import com.example.bookstorebackendappcfp.DTO.OrderDTO;
import com.example.bookstorebackendappcfp.Exception.BookException;
import com.example.bookstorebackendappcfp.Exception.CartException;
import com.example.bookstorebackendappcfp.Exception.OrderException;
import com.example.bookstorebackendappcfp.Exception.UserException;
import com.example.bookstorebackendappcfp.Model.Order;
import com.example.bookstorebackendappcfp.repository.OrderRepository;
import com.example.bookstorebackendappcfp.util.EmailSenderService;
import com.example.bookstorebackendappcfp.util.JWTUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService implements IOrderService{

    @Autowired
    IUserService userService;

    @Autowired
    ICartService cartService;

    @Autowired
    IBookService bookService;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    JWTUtil jwtUtil;

    @Autowired
    EmailSenderService emailSenderService;
    private static final ModelMapper modelMapper = new ModelMapper();
    @Override
    public List<OrderDTO> placeOrder(OrderDTO orderDTO, String authHeader) throws UserException, CartException, BookException {
       List<CartResponseDTO> cart=cartService.getByUserId(authHeader);
       if(!cart.isEmpty()){
           List<OrderDTO> orderDTOList=new ArrayList<OrderDTO>();
           for (CartResponseDTO cartItem:cart) {
               Order order=new Order(cartItem.getTotalPrice(), cartItem.getQuantity(), orderDTO.getName(), orderDTO.getAddress(), orderDTO.getCity(), orderDTO.getState(), orderDTO.getPincode(),orderDTO.getPhoneNumber(), orderDTO.getType(),cartItem.getUser(),cartItem.getBook());
               order.setCanceled(false);
               order.setCreationDate(LocalDate.now());
               Order savedOrder=orderRepository.save(order);
               orderDTOList.add(modelMapper.map(savedOrder,OrderDTO.class));
               cartService.deleteById(cartItem.getCartId());
               long newQuantity=cartItem.getBook().getQuantity() - order.getQuantity();
               bookService.changeQuantity(cartItem.getBook().getBookId(),newQuantity);
               emailSenderService.sendEmail(savedOrder.getUser().getEmail(), "Order placed!","Hii...."+savedOrder.getUser().getFirstName()+" ! \n\n Your order has been placed successfully! Order details are below: \n\n Order id:  "+savedOrder.getOrderId()+"\n\n Order date:  "+savedOrder.getCreationDate()+"\n\n Order Price:  "+savedOrder.getPrice()+"\n Order quantity:  "+savedOrder.getQuantity()+"\n Order address:  "+savedOrder.getAddress()+"\n Order user id:  "+savedOrder.getUser().getUserId()+"\n Order book id:  "+savedOrder.getBook().getBookId()+"\n Order cancellation status:  "+savedOrder.isCanceled());
           }

           return orderDTOList;
       }
       return null;
    }

    @Override
    public List<OrderDTO> getAll() {
        List<Order> orderList = orderRepository.findAll();
        return orderList.stream().map(order -> modelMapper.map(order, OrderDTO.class)).collect(Collectors.toList());
    }

    @Override
    public List<OrderDTO> getByUserId(String authHeader) throws UserException {
        String token = jwtUtil.parseToken(authHeader);
        long userId = jwtUtil.decodeToken(token);
        List<Order> orderList = orderRepository.findByUserId(userId);
        if(orderList!=null){
            return orderList.stream().map(order -> modelMapper.map(order, OrderDTO.class)).collect(Collectors.toList());
        }else {
            throw new UserException("user not found with id: "+userId);
        }
    }

    @Override
    public OrderDTO cancelOrder(Long orderId) throws OrderException {
        Order order=orderRepository.findById(orderId).orElseThrow(()->new OrderException("Error:order not found"));
        try {
            order.setCanceled(true);
            order=orderRepository.save(order);
            emailSenderService.sendEmail(order.getUser().getEmail(), "Order is canceled!","Hii...."+order.getUser().getFirstName()+" ! \n\n Your order has been deleted successfully! Order id: "+order.getOrderId());
            long newQuantity=order.getBook().getQuantity()+order.getQuantity();
            bookService.changeQuantity(order.getBook().getBookId(),newQuantity);
            return modelMapper.map(order,OrderDTO.class);
        }catch(Exception e){
            throw new OrderException("Error:order not found");
        }

    }

    @Override
    public boolean deleteById(Long orderId) throws OrderException {
        Order order=orderRepository.findById(orderId).orElseThrow(()->new OrderException("Error:order not found"));
        try {
            orderRepository.deleteById(orderId);
            if(!order.isCanceled()){
                long newQuantity=order.getBook().getQuantity()+order.getQuantity();
                bookService.changeQuantity(order.getBook().getBookId(),newQuantity);
            }
            emailSenderService.sendEmail(order.getUser().getEmail(), "Order is deleted!","Hii...."+order.getUser().getFirstName()+" ! \n\n Your order has been deleted successfully! Order id: "+order.getOrderId());
            return true;
        }catch(Exception e){
            throw new OrderException("Error:order not found");
        }
    }

    @Override
    public OrderDTO updateByOrderId(Long orderId,OrderDTO orderDTO) throws OrderException {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new OrderException("Order not found"));
        order.setName(orderDTO.getName());
        order.setPhoneNumber(orderDTO.getPhoneNumber());
        order.setAddress(orderDTO.getAddress());
        order.setCanceled(orderDTO.isCanceled());
        order.setQuantity(orderDTO.getQuantity());
        order.setPincode(orderDTO.getPincode());
        order.setType(orderDTO.getType());
        order.setCity(orderDTO.getCity());
        order.setState(orderDTO.getState());

        return modelMapper.map(orderRepository.save(order), OrderDTO.class);
    }
}
