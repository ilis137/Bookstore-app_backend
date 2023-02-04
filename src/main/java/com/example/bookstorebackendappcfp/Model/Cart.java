package com.example.bookstorebackendappcfp.Model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Entity
@Data
@Table(name = "cart_table")
@NoArgsConstructor
@RequiredArgsConstructor
public class Cart {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cartId;
    @OneToOne(cascade = {CascadeType.PERSIST},fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    @NonNull
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private User user;
    @ManyToOne(cascade = {CascadeType.PERSIST},fetch = FetchType.EAGER)
    @JoinColumn(name = "book_id")
    @NonNull
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Book book;

    @NonNull
    private long quantity;

    @NonNull
    private long totalPrice;
}