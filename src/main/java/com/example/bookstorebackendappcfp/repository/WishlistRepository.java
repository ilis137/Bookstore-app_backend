package com.example.bookstorebackendappcfp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.bookstorebackendappcfp.Model.Wishlist;

public interface WishlistRepository extends JpaRepository<Wishlist,Long>{
  //fetch wishlist details by user id
  @Query(value = "select * from wishlist where wishlist.user_id=:userId",nativeQuery = true)
  List<Wishlist> findByUserId(Long userId);
}
