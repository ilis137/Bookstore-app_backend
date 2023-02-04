package com.example.bookstorebackendappcfp.DTO;

import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartDTO {
    @Nullable
    long cartId;
    @Nullable
    private Long userId;
    @NotNull(message = "Book Id can not be null!")
    private Long bookId;
    @NotNull(message = "Quantity can not be null!")
    private int quantity;

   @Nullable
    private long totalPrice;
}
