package com.example.bookstorebackendappcfp.Model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="book_table")
@Data
@RequiredArgsConstructor
@NoArgsConstructor
public class Book {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int bookId;

    @NonNull
    private String bookName;

    @NonNull
    private String author;

    @NonNull
    private int bookPrice;

    @NonNull
    private int quantity;

    @NonNull
    private String bookImage;
}
