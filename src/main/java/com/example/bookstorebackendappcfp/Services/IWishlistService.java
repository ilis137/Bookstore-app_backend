package com.example.bookstorebackendappcfp.Services;

import java.util.List;

import com.example.bookstorebackendappcfp.DTO.WishlistDTO;
import com.example.bookstorebackendappcfp.DTO.WishlistResponseDTO;
import com.example.bookstorebackendappcfp.Exception.BookException;
import com.example.bookstorebackendappcfp.Exception.UserException;
import com.example.bookstorebackendappcfp.Exception.WishlistException;

public interface IWishlistService {
  public WishlistResponseDTO addToWishlist(String authHeaader,WishlistDTO WishlistDTO) throws UserException, BookException;
  public List<WishlistResponseDTO> getWishlist(String authHeader) throws UserException;
  public void deleteFromWishlist(long id) throws UserException, WishlistException;
}
