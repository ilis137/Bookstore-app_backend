package com.example.bookstorebackendappcfp.Services;


import com.example.bookstorebackendappcfp.DTO.BookDTO;
import com.example.bookstorebackendappcfp.Exception.BookNotFoundException;
import com.example.bookstorebackendappcfp.Model.Book;
import com.example.bookstorebackendappcfp.repository.BookRepository;
import com.example.bookstorebackendappcfp.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookService implements IBookService{
    @Autowired
    private BookRepository bookRepo;

    private static final ModelMapper modelMapper = new ModelMapper();
    @Override
    public BookDTO addBookStock(BookDTO bookDTO) {
        Book book = new Book(bookDTO.getBookName(), bookDTO.getAuthor(),bookDTO.getBookPrice(),bookDTO.getQuantity(),bookDTO.getBookImage());
        bookRepo.save(book);
        return modelMapper.map(book,BookDTO.class);
    }

    @Override
    public BookDTO updateBookById(int bookId, BookDTO bookDTO) throws BookNotFoundException {
        Book book = bookRepo.findById(bookId).orElseThrow(() -> new BookNotFoundException("Book not found"));
        book.setBookName(bookDTO.getBookName());
        book.setAuthor(bookDTO.getAuthor());
        book.setBookPrice(bookDTO.getBookPrice());
        book.setQuantity(bookDTO.getQuantity());
        book.setBookImage(bookDTO.getBookImage());
        return modelMapper.map(bookRepo.save(book),BookDTO.class);
    }

    @Override
    public boolean deleteBookById(int id) throws BookNotFoundException {
        // }
        try {
            bookRepo.deleteById(id);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new BookNotFoundException("Book not found");
        }

        return true;
    }

    @Override
    public List<BookDTO> getAllBooks(int startPage, int size) throws BookNotFoundException {
        Pageable pageable = PageRequest.of(startPage, size);
        try {
            return bookRepo.findAll(pageable).toList().stream().map(book->modelMapper.map(bookRepo.save(book),BookDTO.class)).collect(Collectors.toList());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new BookNotFoundException("Books not found");
        }

    }

    @Override
    public BookDTO getBook(int id) throws BookNotFoundException {
        Book book= bookRepo.findById(id).orElseThrow(() -> new BookNotFoundException("Error: Book is not found."));
        return modelMapper.map(book,BookDTO.class);
    }

    @Override
    public BookDTO changePrice(int bookId,int price) throws BookNotFoundException {
        Book book = bookRepo.findById(bookId).orElseThrow(() -> new BookNotFoundException("Book not found"));
        book.setBookPrice(price);
        book=bookRepo.save(book);
        return modelMapper.map(book,BookDTO.class);
    }

    @Override
    public BookDTO changeQuantity(int bookId, int quantity) throws BookNotFoundException {
        Book book = bookRepo.findById(bookId).orElseThrow(() -> new BookNotFoundException("Book not found"));
        book.setQuantity(quantity);
        book=bookRepo.save(book);
        return modelMapper.map(book,BookDTO.class);
    }
}