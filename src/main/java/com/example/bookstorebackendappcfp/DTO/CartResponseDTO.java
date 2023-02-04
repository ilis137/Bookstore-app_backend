package com.example.bookstorebackendappcfp.DTO;


import com.example.bookstorebackendappcfp.Model.Book;
import com.example.bookstorebackendappcfp.Model.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartResponseDTO {

    private Long cartId;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private User user;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Book book;

    private long quantity;

    private long totalPrice;
}
