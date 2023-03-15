package com.example.bookstorebackendappcfp.Controllers;

import com.example.bookstorebackendappcfp.DTO.CartDTO;
import com.example.bookstorebackendappcfp.DTO.CartResponseDTO;
import com.example.bookstorebackendappcfp.DTO.ResponseDTO;
import com.example.bookstorebackendappcfp.Exception.BookException;
import com.example.bookstorebackendappcfp.Exception.CartException;
import com.example.bookstorebackendappcfp.Exception.UserException;
import com.example.bookstorebackendappcfp.Services.ICartService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
@CrossOrigin(origins = "*")
public class CartController {
    @Autowired
    ICartService cartService;

    /* 
     * api to add a new book to cart
     * @param CartDTO cartDTO
     * @param authHeader
     * @return  ResponseEntity<ResponseDTO>
     */
    @PostMapping("/add")
    public ResponseEntity<ResponseDTO> insert(@Valid @RequestBody CartDTO cartDTO,@RequestHeader(name="Authorization") String authHeader) throws UserException, BookException {

        ResponseDTO responseCartDTO= ResponseDTO.Build("Your cart details are added!", cartService.add(cartDTO,authHeader));
        return new ResponseEntity<>(responseCartDTO, HttpStatus.OK);
    }

    /* 
     * api to add a new book to cart
     * 
     * @return  ResponseEntity<ResponseDTO>
     */
    @GetMapping("/get/all")
    public ResponseEntity<ResponseDTO> getAll(){
        List<CartResponseDTO> cartList=cartService.getAll();
        ResponseDTO responseCartDTO= ResponseDTO.Build("All cart details are found!",cartList);
        return new ResponseEntity<>(responseCartDTO,HttpStatus.OK);
    }

    
    /* 
     * api to add a fetch book in cart by user token
     * @param authHeader ,user token
     *
     * @return  ResponseEntity<ResponseDTO>
     */
    @GetMapping("/getByUserId")
    public ResponseEntity<ResponseDTO> getAllByUserId(@RequestHeader(name="Authorization") String authHeader) throws UserException {
        List<CartResponseDTO> cartResponseDTOList=cartService.getByUserId(authHeader);
        ResponseDTO responseCartDTO= ResponseDTO.Build("Searched cart details by user id is found!",cartResponseDTOList);
        return new ResponseEntity<>(responseCartDTO,HttpStatus.OK);
    }


    
    /* 
     * api to delete a book in cart by cart id
     * @param id ,id of cart item
     *
     * @return  ResponseEntity<ResponseDTO>
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ResponseDTO> deleteById(@PathVariable Long id) throws CartException {
        cartService.deleteById(id);
        ResponseDTO responseCartDTO= ResponseDTO.Build("Cart details is deleted for cart id "+id,true);
        return new ResponseEntity<>(responseCartDTO,HttpStatus.OK);
    }
      /* 
     * api to update a book in cart by cart id
     *  @param id ,id of cart item
     * @param quantity ,new quantity of  book in cart
     *
     * @return  ResponseEntity<ResponseDTO>
     */
    @PutMapping("/updateQuantity/{id}")
    public ResponseEntity<ResponseDTO> updateQuantity(@PathVariable Long id,@RequestParam Long quantity) throws CartException {
        ResponseDTO responseCartDTO= ResponseDTO.Build("Your cart quantity is updated for cart id "+id,cartService.UpdateQuantity(id,quantity));
        return new ResponseEntity<>(responseCartDTO,HttpStatus.OK);
    }
}
