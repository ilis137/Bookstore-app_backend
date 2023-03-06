package com.example.bookstorebackendappcfp.Services;

import com.example.bookstorebackendappcfp.DTO.BookDTO;
import com.example.bookstorebackendappcfp.Exception.BookException;

import java.util.List;

public interface IBookService {
    BookDTO addBookStock(BookDTO bookDTO);

    BookDTO updateBookById(long bookId, BookDTO bookDTO) throws BookException;

    boolean deleteBookById(long id) throws BookException;

    List<BookDTO> getAllBooks() throws BookException;

    BookDTO getBook(long id) throws BookException;

    BookDTO changePrice(long bookId,long price) throws BookException;

    BookDTO changeQuantity(long bookId, long quantity) throws BookException;
}
