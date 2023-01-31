package com.example.bookstorebackendappcfp.Exception;

import com.example.bookstorebackendappcfp.DTO.BookDTO;

public class BookNotFoundException extends Exception{
    public BookNotFoundException(String message){
        super(message);
    }

}
