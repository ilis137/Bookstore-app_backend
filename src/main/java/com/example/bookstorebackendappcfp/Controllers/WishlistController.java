package com.example.bookstorebackendappcfp.Controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import com.example.bookstorebackendappcfp.DTO.ResponseDTO;
import com.example.bookstorebackendappcfp.DTO.WishlistDTO;
import com.example.bookstorebackendappcfp.DTO.WishlistResponseDTO;
import com.example.bookstorebackendappcfp.Exception.BookException;
import com.example.bookstorebackendappcfp.Exception.UserException;
import com.example.bookstorebackendappcfp.Exception.WishlistException;
import com.example.bookstorebackendappcfp.Services.IWishlistService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/wishlist")
@CrossOrigin("*")
public class WishlistController {
  
    @Autowired
    IWishlistService wishlistService;

    /*
     * api to add a book to wish list of user
     * 
     * @param wishlistDTO, book data
     * 
     * @return ResponseEntity<ResponseDTO>
     */
  @PostMapping("/add")
  public ResponseEntity<ResponseDTO> insert(@Valid @RequestBody WishlistDTO wishlistDTO,@RequestHeader(name="Authorization") String authHeader) throws UserException, BookException{
      ResponseDTO responseCartDTO= ResponseDTO.Build("Your cart details are added!", wishlistService.addToWishlist(authHeader,wishlistDTO));
      return new ResponseEntity<>(responseCartDTO, HttpStatus.OK);
  }

    /*
     * api to get wishlist for user 
     * 
     * @param authHeader, user token
     * 
     * @return ResponseEntity<ResponseDTO>
     */
  @GetMapping("/getByUserId")
  public ResponseEntity<ResponseDTO> getAllByUserId(@RequestHeader(name="Authorization") String authHeader) throws UserException {
      List<WishlistResponseDTO> cartResponseDTOList=wishlistService.getWishlist(authHeader);
      ResponseDTO responseCartDTO= ResponseDTO.Build("Searched cart details by user id is found!",cartResponseDTOList);
      return new ResponseEntity<>(responseCartDTO,HttpStatus.OK);
  }
    /*
     * api to remove a item from wishlist for user
     * 
     * @param id, wishlist item id
     * 
     * @return ResponseEntity<ResponseDTO>
     */
  @DeleteMapping("/delete/{id}")
  public ResponseEntity<ResponseDTO> deleteById(@PathVariable Long id) throws WishlistException, UserException {
    wishlistService.deleteFromWishlist(id);
      ResponseDTO responseCartDTO= ResponseDTO.Build("Cart details is deleted for cart id "+id,true);
      return new ResponseEntity<>(responseCartDTO,HttpStatus.OK);
  }
}
