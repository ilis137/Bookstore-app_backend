package com.example.bookstorebackendappcfp.Services;


import com.example.bookstorebackendappcfp.DTO.BookDTO;
import com.example.bookstorebackendappcfp.Exception.BookException;
import com.example.bookstorebackendappcfp.Model.Book;
import com.example.bookstorebackendappcfp.repository.BookRepository;
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
    public BookDTO updateBookById(long bookId, BookDTO bookDTO) throws BookException {
        Book book = bookRepo.findById(bookId).orElseThrow(() -> new BookException("Book not found"));
        book.setBookName(bookDTO.getBookName());
        book.setAuthor(bookDTO.getAuthor());
        book.setBookPrice(bookDTO.getBookPrice());
        book.setQuantity(bookDTO.getQuantity());
        book.setBookImage(bookDTO.getBookImage());
        return modelMapper.map(bookRepo.save(book),BookDTO.class);
    }

    @Override
    public boolean deleteBookById(long id) throws BookException {
        try {
            bookRepo.deleteById(id);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new BookException("Book not found");
        }

        return true;
    }

    @Override
    public List<BookDTO> getAllBooks() throws BookException {
      
        try {
            return bookRepo.findAll().stream().map(book->modelMapper.map(bookRepo.save(book),BookDTO.class)).collect(Collectors.toList());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new BookException("Books not found");
        }

    }

    @Override
    public BookDTO getBook(long id) throws BookException {
        Book book= bookRepo.findById(id).orElseThrow(() -> new BookException("Error: Book is not found."));
        return modelMapper.map(book,BookDTO.class);
    }

    @Override
    public BookDTO changePrice(long bookId,long price) throws BookException {
        Book book = bookRepo.findById(bookId).orElseThrow(() -> new BookException("Book not found"));
        book.setBookPrice(price);
        book=bookRepo.save(book);
        return modelMapper.map(book,BookDTO.class);
    }

    @Override
    public BookDTO changeQuantity(long bookId, long quantity) throws BookException {
        Book book = bookRepo.findById(bookId).orElseThrow(() -> new BookException("Book not found"));
        book.setQuantity(quantity);
        book=bookRepo.save(book);
        return modelMapper.map(book,BookDTO.class);
    }
}
