package com.example.bookstorebackendappcfp.Services;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.bookstorebackendappcfp.DTO.WishlistDTO;
import com.example.bookstorebackendappcfp.DTO.WishlistResponseDTO;
import com.example.bookstorebackendappcfp.Exception.BookException;
import com.example.bookstorebackendappcfp.Exception.UserException;
import com.example.bookstorebackendappcfp.Exception.WishlistException;
import com.example.bookstorebackendappcfp.Model.Book;
import com.example.bookstorebackendappcfp.Model.User;
import com.example.bookstorebackendappcfp.Model.Wishlist;
import com.example.bookstorebackendappcfp.repository.BookRepository;
import com.example.bookstorebackendappcfp.repository.UserRepository;
import com.example.bookstorebackendappcfp.repository.WishlistRepository;
import com.example.bookstorebackendappcfp.util.JWTUtil;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class WishlistService implements IWishlistService{
    
    @Autowired
    private UserRepository userRepo;
    @Autowired
    private WishlistRepository wishlistRepository;
    @Autowired
    private BookRepository bookRepo;

    @Autowired
    JWTUtil jwtUtil;

    private static final ModelMapper modelMapper = new ModelMapper();
      /*
     * add new wishlist item into the database by calling wishlistRepository method
     * 
     * @param wishlistDTO,wishlist details
     * 
     * @return WishlistResponseDTO
     */
    @Override
    public WishlistResponseDTO addToWishlist(String authHeader,WishlistDTO WishlistDTO) throws UserException, BookException{
        String token = jwtUtil.parseToken(authHeader);
        long userId = jwtUtil.decodeToken(token);
        User user = userRepo.findById(userId).orElseThrow(() -> new UserException("User with id " + userId + " does not exist"));
        Book book = bookRepo.findById(WishlistDTO.getBookId()).orElseThrow(() -> new BookException("book with id " + WishlistDTO.getBookId() + " not found"));
        
        Wishlist wish = new Wishlist(user, book);
        return modelMapper.map(wishlistRepository.save(wish), WishlistResponseDTO.class);
    }
     /*
     * get all wishlist details for user by wishlistRepository method
     * 
     * @param authHeader
     * 
     * @return WishlistResponseDTO list
     */
    @Override
    public List<WishlistResponseDTO> getWishlist(String authHeader) throws UserException {
        String token = jwtUtil.parseToken(authHeader);
        long userId = jwtUtil.decodeToken(token);
        try {
            List<Wishlist> cartList = wishlistRepository.findByUserId(userId);
            return cartList.stream().map(cartItem -> modelMapper.map(cartItem, WishlistResponseDTO.class)).collect(Collectors.toList());
        } catch (Exception e) {
            new UserException("User with id " + userId + " does not exist");
        }

        return null;
       
    }
     /*
     * delete cart details by wishlist id 
     * 
     * @param id,wishlist id
     * 
     * @return boolean
     */
    @Override
    public void deleteFromWishlist(long id) throws UserException, WishlistException {
        try {
            wishlistRepository.deleteById(id);
        } catch (RuntimeException e) {
            throw new WishlistException("wish with id " + id + " not found");
        }
    }
}
