package com.example.bookstorebackendappcfp.Services;

import com.example.bookstorebackendappcfp.DTO.CartDTO;
import com.example.bookstorebackendappcfp.DTO.CartResponseDTO;
import com.example.bookstorebackendappcfp.Exception.BookException;
import com.example.bookstorebackendappcfp.Exception.CartException;
import com.example.bookstorebackendappcfp.Exception.UserException;

import java.util.List;

public interface ICartService {
    CartResponseDTO add(CartDTO cartDTO, String authHeader) throws UserException, BookException;

    List<CartResponseDTO> getAll();

    List<CartResponseDTO> getByUserId(String authHeader) throws UserException;

    void deleteById(Long id) throws CartException;

    CartResponseDTO UpdateQuantity( Long id,Long Quantity) throws CartException;
}
