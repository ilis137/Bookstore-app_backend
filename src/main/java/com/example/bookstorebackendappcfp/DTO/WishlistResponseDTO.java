package com.example.bookstorebackendappcfp.DTO;

import com.example.bookstorebackendappcfp.Model.Book;
import com.example.bookstorebackendappcfp.Model.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.micrometer.common.lang.Nullable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WishlistResponseDTO {
  @Nullable
  long wishlistId;
  @Nullable
  @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
  private User user;
  @Nullable
  @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
  private Book book;

}
