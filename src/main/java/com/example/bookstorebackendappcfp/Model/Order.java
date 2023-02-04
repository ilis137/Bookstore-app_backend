package com.example.bookstorebackendappcfp.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@RequiredArgsConstructor
@Table(name = "order_table")
public class Order {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;


    private LocalDate creationDate ;

    @NonNull
    private long price;

    @NonNull
    private long quantity;
    @NonNull
    private String name;
    @NonNull
    private String address;

    @NonNull
    private String city;

    @NonNull
    private String state;

    @NonNull
    private String pincode;

    @NonNull
    private String phoneNumber;

    @NonNull
    private String type;
    @OneToOne(cascade = {CascadeType.PERSIST})
    @JoinColumn(name = "user_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @NonNull
    private User user;
    @ManyToOne(cascade = {CascadeType.PERSIST})
    @JoinColumn(name = "book_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @NonNull
    private Book book;

    private boolean canceled;


    }
