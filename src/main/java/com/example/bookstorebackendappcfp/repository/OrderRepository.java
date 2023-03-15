package com.example.bookstorebackendappcfp.repository;

import com.example.bookstorebackendappcfp.Model.Cart;
import com.example.bookstorebackendappcfp.Model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order,Long> {
    //fetch order details by user id
    @Query(value = "select * from order_table where order_table.user_id=:userId",nativeQuery = true)
    List<Order> findByUserId(Long userId);
}
