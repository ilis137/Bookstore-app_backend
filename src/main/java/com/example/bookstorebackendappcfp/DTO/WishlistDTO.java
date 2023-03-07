package com.example.bookstorebackendappcfp.DTO;
import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.example.bookstorebackendappcfp.Model.Book;
import com.example.bookstorebackendappcfp.Model.User;
import jakarta.validation.constraints.NotNull;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class WishlistDTO {
  @Nullable
  long wishlistId;
 
  @NotNull(message = "Book Id can not be null!")
  private long bookId;
  
}
