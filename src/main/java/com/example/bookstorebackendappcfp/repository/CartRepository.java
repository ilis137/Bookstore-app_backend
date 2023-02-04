package com.example.bookstorebackendappcfp.repository;

import com.example.bookstorebackendappcfp.Model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {
    @Query(value = "select * from cart_table where cart_table.user_id=:userId",nativeQuery = true)
    List<Cart> findByUserId(Long userId);
}
