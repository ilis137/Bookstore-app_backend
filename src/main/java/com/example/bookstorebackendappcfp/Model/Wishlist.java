package com.example.bookstorebackendappcfp.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Entity
@Table(name="wishlist")
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class Wishlist {
  @Id
  @Column
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  long wishlistId;
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
}
