package com.example.bookstorebackendappcfp.Controllers;


import com.example.bookstorebackendappcfp.DTO.OrderDTO;
import com.example.bookstorebackendappcfp.DTO.ResponseDTO;
import com.example.bookstorebackendappcfp.Exception.BookException;
import com.example.bookstorebackendappcfp.Exception.CartException;
import com.example.bookstorebackendappcfp.Exception.OrderException;
import com.example.bookstorebackendappcfp.Exception.UserException;
import com.example.bookstorebackendappcfp.Services.IOrderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order")
@CrossOrigin("*")
public class OrderController {


    //Create dependency injection for orderService class
    @Autowired
    IOrderService orderService;

    //Create Api for Insert order details in the database
    @PostMapping("/placeOrder")
    public ResponseEntity<ResponseDTO> placeOrder(@Valid @RequestBody OrderDTO orderDTO, @RequestHeader(name = "Authorization") String authHeader) throws UserException, CartException, BookException {
        ResponseDTO responseOrderDTO = ResponseDTO.Build("Order details are submitted!", orderService.placeOrder(orderDTO, authHeader));
        return new ResponseEntity<>(responseOrderDTO, HttpStatus.CREATED);
    }

    //Create Api for Getting all order details present in the database
    @GetMapping("/getAll")
    public ResponseEntity<ResponseDTO> getAll() {
        List<OrderDTO> orderList = orderService.getAll();
        ResponseDTO responseOrderDTO = ResponseDTO.Build("All order details are found!", orderList);
        return new ResponseEntity<>(responseOrderDTO, HttpStatus.FOUND);
    }

    //Create Api for Getting particular order details which will be found by id
    @GetMapping("/getByUserId")
    public ResponseEntity<ResponseDTO> getById(@RequestHeader(name = "Authorization") String authHeader) throws UserException {
        List<OrderDTO> orderDTOList = orderService.getByUserId(authHeader);
        ResponseDTO responseOrderDTO = ResponseDTO.Build("Searched order details by id is found!", orderDTOList);
        return new ResponseEntity<>(responseOrderDTO, HttpStatus.FOUND);
    }

    @PutMapping("/updateByOrderId/{orderId}")
    public ResponseEntity<ResponseDTO> updateByOrderId(@PathVariable Long orderId,@Valid @RequestBody OrderDTO orderDTO) throws UserException, OrderException {
        ResponseDTO responseOrderDTO = ResponseDTO.Build("Searched order details by id is found!", orderService.updateByOrderId(orderId,orderDTO));
        return new ResponseEntity<>(responseOrderDTO, HttpStatus.FOUND);
    }

    //Create Api for Deleting particular order details which will be found by id
    @DeleteMapping("/cancel/{orderId}")
    public ResponseEntity<ResponseDTO> cancelById(@PathVariable Long orderId) throws OrderException {

        ResponseDTO responseOrderDTO = ResponseDTO.Build("Cart details is canceled!", orderService.cancelOrder(orderId));
        return new ResponseEntity<>(responseOrderDTO, HttpStatus.GONE);
    }

    @DeleteMapping("/delete/{orderId}")
    public ResponseEntity<ResponseDTO> deleteById(@PathVariable Long orderId) throws OrderException {
        ResponseDTO responseOrderDTO = ResponseDTO.Build("Cart details is canceled!", orderService.deleteById(orderId));
        return new ResponseEntity<>(responseOrderDTO, HttpStatus.GONE);
    }
}
