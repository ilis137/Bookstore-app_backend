package com.example.bookstorebackendappcfp.Controllers;

import com.example.bookstorebackendappcfp.DTO.BookDTO;
import com.example.bookstorebackendappcfp.DTO.ResponseDTO;
import com.example.bookstorebackendappcfp.Exception.BookException;
import com.example.bookstorebackendappcfp.Services.IBookService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/book")
@CrossOrigin("*")
public class BookController {

    @Autowired
    IBookService bookService;

    //Api to add books
    //@param BookDTO
    //@return  ResponseEntity<ResponseDTO>
    @PostMapping("/add")
    public ResponseEntity<ResponseDTO> addBook(@RequestBody @Valid BookDTO bookDTO) throws  MethodArgumentNotValidException {
        ResponseDTO responseDTO = ResponseDTO.Build("Added Book to store", bookService.addBookStock(bookDTO));
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }


    //api to update books by book id
    //@param BookDTO
    //@return  ResponseEntity<ResponseDTO>
    @PutMapping("/update/{bookId}")
    public ResponseEntity<ResponseDTO> updateBook(@RequestBody @Valid BookDTO bookDTO,@PathVariable int bookId) throws BookException,MethodArgumentNotValidException{
        ResponseDTO responseDTO = ResponseDTO.Build("Added Book to store", bookService.updateBookById(bookId,bookDTO));
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);

    }
     //api to delete books by book id
    //@param BookDTO
    //@return  ResponseEntity<ResponseDTO>
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ResponseDTO> deleteBook(@PathVariable int id) throws BookException,MethodArgumentNotValidException{
        ResponseDTO responseDTO=ResponseDTO.Build("Deleted book from store",bookService.deleteBookById(id));
        return new ResponseEntity<>(responseDTO,HttpStatus.OK);
    }


    /* api to fetch all books
     * @return  ResponseEntity<ResponseDTO>
     * 
     */
    @GetMapping("/view/all")
    public ResponseEntity<ResponseDTO> viewAllBooks() throws BookException,MethodArgumentNotValidException{
        ResponseDTO responseDTO=ResponseDTO.Build("all books from store",bookService.getAllBooks());
        return new ResponseEntity<>(responseDTO,HttpStatus.OK);
    }
     /* api to fetch a book by id

     * @param      id  -book id
     * @return  ResponseEntity<ResponseDTO>
 
     */
    @GetMapping("/view/{id}")
    public ResponseEntity<ResponseDTO> viewBook(@PathVariable int id) throws BookException,MethodArgumentNotValidException{
        ResponseDTO responseDTO=ResponseDTO.Build("book with id"+id+" from store",bookService.getBook(id));
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

     /* api to update the price of book by book id
     * @param bookId
     * @param price
     * @return  ResponseEntity<ResponseDTO>
     */
    @PutMapping("/changeprice/{bookId}/{price}")
    public ResponseEntity<ResponseDTO> changeBookPrice(@PathVariable int bookId,@PathVariable int price) throws BookException,MethodArgumentNotValidException{
        ResponseDTO responseDTO=ResponseDTO.Build("changed price of book from store",bookService.changePrice(bookId,price));
        return new ResponseEntity<>(responseDTO,HttpStatus.OK);
    }

     /* api to update the quantity of book by book id
     * @param bookId
     * @param quantity
     * @return  ResponseEntity<ResponseDTO>
     */
    @PutMapping("/changequantity/{bookId}/{quantity}")
    public ResponseEntity<ResponseDTO> changeQuantity(@PathVariable int bookId,@PathVariable int quantity) throws BookException,MethodArgumentNotValidException{
        ResponseDTO responseDTO=ResponseDTO.Build("changed quantity of book from store",bookService.changeQuantity(bookId,quantity));
        return new ResponseEntity<>(responseDTO,HttpStatus.OK);
    }
}
