package com.example.bookstorebackendappcfp.DTO;

import jakarta.annotation.Nullable;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookDTO {

    @Nullable
    private int bookId;
    @NotNull(message = "Book name should not be null")
    private String bookName;

    @NotNull(message = "author name should not be null")
    private String author;

    @NotNull(message = "book prize should not be null")
    private int bookPrice;

    @NotNull
    @Min(value = 1, message = "quantity should not be zero")
    private int quantity;

    @NotNull
    private String bookImage;
}

