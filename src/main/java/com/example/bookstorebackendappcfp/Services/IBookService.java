package com.example.bookstorebackendappcfp.Services;

import com.example.bookstorebackendappcfp.DTO.BookDTO;
import com.example.bookstorebackendappcfp.Exception.BookNotFoundException;

import java.util.List;

public interface IBookService {
    BookDTO addBookStock(BookDTO bookDTO);

    BookDTO updateBookById(int bookId, BookDTO bookDTO) throws BookNotFoundException;

    boolean deleteBookById(int id) throws BookNotFoundException;

    List<BookDTO> getAllBooks(int startPage, int size) throws BookNotFoundException;

    BookDTO getBook(int id) throws BookNotFoundException;

    BookDTO changePrice(int bookId,int price) throws BookNotFoundException;

    BookDTO changeQuantity(int bookId, int quantity) throws BookNotFoundException;
}
