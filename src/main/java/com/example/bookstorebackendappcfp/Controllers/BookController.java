package com.example.bookstorebackendappcfp.Controllers;

import com.example.bookstorebackendappcfp.DTO.BookDTO;
import com.example.bookstorebackendappcfp.DTO.ResponseDTO;
import com.example.bookstorebackendappcfp.Exception.BookNotFoundException;
import com.example.bookstorebackendappcfp.Exception.UserNotFoundException;
import com.example.bookstorebackendappcfp.Services.IBookService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/book")
public class BookController {

    @Autowired
    IBookService bookService;

    @PostMapping("/add")
    public ResponseEntity<ResponseDTO> addBook(@RequestBody @Valid BookDTO bookDTO) throws  MethodArgumentNotValidException {
        ResponseDTO responseDTO = ResponseDTO.Build("Added Book to store", bookService.addBookStock(bookDTO));
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @PutMapping("/update/{bookId}")
    public ResponseEntity<ResponseDTO> updateBook(@RequestBody @Valid BookDTO bookDTO,@PathVariable int bookId) throws BookNotFoundException,MethodArgumentNotValidException{
        ResponseDTO responseDTO = ResponseDTO.Build("Added Book to store", bookService.updateBookById(bookId,bookDTO));
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);

    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ResponseDTO> deleteBook(@PathVariable int id) throws BookNotFoundException,MethodArgumentNotValidException{
        ResponseDTO responseDTO=ResponseDTO.Build("Deleted book from store",bookService.deleteBookById(id));
        return new ResponseEntity<>(responseDTO,HttpStatus.OK);
    }



    @GetMapping("/view/all")
    public ResponseEntity<ResponseDTO> viewAllBooks(@RequestParam int startPage,@RequestParam int size) throws BookNotFoundException,MethodArgumentNotValidException{
        ResponseDTO responseDTO=ResponseDTO.Build("all books from store",bookService.getAllBooks(startPage,size));
        return new ResponseEntity<>(responseDTO,HttpStatus.OK);
    }

    @GetMapping("/view/{id}")
    public ResponseEntity<ResponseDTO> viewBook(@PathVariable int id) throws BookNotFoundException,MethodArgumentNotValidException{
        ResponseDTO responseDTO=ResponseDTO.Build("book with id"+id+" from store",bookService.getBook(id));
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @PutMapping("/changeprice/{bookId}/{price}")
    public ResponseEntity<ResponseDTO> changeBookPrice(@PathVariable int bookId,@PathVariable int price) throws BookNotFoundException,MethodArgumentNotValidException{
        ResponseDTO responseDTO=ResponseDTO.Build("changed price of book from store",bookService.changePrice(bookId,price));
        return new ResponseEntity<>(responseDTO,HttpStatus.OK);
    }

    @PutMapping("/changequantity/{bookId}/{quantity}")
    public ResponseEntity<ResponseDTO> changeQuantity(@PathVariable int bookId,@PathVariable int quantity) throws BookNotFoundException,MethodArgumentNotValidException{
        ResponseDTO responseDTO=ResponseDTO.Build("changed quantity of book from store",bookService.changeQuantity(bookId,quantity));
        return new ResponseEntity<>(responseDTO,HttpStatus.OK);
    }
}
