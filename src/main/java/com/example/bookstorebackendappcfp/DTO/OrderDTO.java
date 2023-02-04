package com.example.bookstorebackendappcfp.DTO;

import com.example.bookstorebackendappcfp.Model.Book;
import com.example.bookstorebackendappcfp.Model.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {
    @Nullable
    private Long orderId;
    @Nullable
    private LocalDate creationDate;

    @Nullable
    private long price;
    @Nullable
    private long quantity;

    @NotBlank(message = "name cannot be empty")
    private String name;
    @NotBlank(message = "address cannot be empty")
    private String address;

    @NotBlank(message = "city cannot be empty")
    private String city;

    @NotBlank(message = "state cannot be empty")
    private String state;

    @NotBlank(message = "pincode cannot be empty")
    private String pincode;

    @NotBlank(message = "phone number cannot be empty")
    private String phoneNumber;

    @NotBlank(message = "type cannot be empty")
    private String type;
    @Nullable
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private User user;

    @Nullable
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Book book;

    @Nullable
    private boolean canceled;
}
