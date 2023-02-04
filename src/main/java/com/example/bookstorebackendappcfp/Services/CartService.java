package com.example.bookstorebackendappcfp.Services;

import com.example.bookstorebackendappcfp.DTO.CartDTO;
import com.example.bookstorebackendappcfp.DTO.CartResponseDTO;
import com.example.bookstorebackendappcfp.Exception.BookException;
import com.example.bookstorebackendappcfp.Exception.CartException;
import com.example.bookstorebackendappcfp.Exception.UserException;
import com.example.bookstorebackendappcfp.Model.Book;
import com.example.bookstorebackendappcfp.Model.Cart;
import com.example.bookstorebackendappcfp.Model.User;
import com.example.bookstorebackendappcfp.repository.BookRepository;
import com.example.bookstorebackendappcfp.repository.CartRepository;
import com.example.bookstorebackendappcfp.repository.UserRepository;
import com.example.bookstorebackendappcfp.util.JWTUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CartService implements ICartService {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private BookRepository bookRepo;

    @Autowired
    private CartRepository cartRepository;
    @Autowired
    JWTUtil jwtUtil;

    private static final ModelMapper modelMapper = new ModelMapper();

    @Override
    public CartResponseDTO add(CartDTO cartDTO, String authHeader) throws UserException, BookException {
        String token = jwtUtil.parseToken(authHeader);
        long userId = jwtUtil.decodeToken(token);
        User user = userRepo.findById(userId).orElseThrow(() -> new UserException("User with id " + userId + " does not exist"));
        Book book = bookRepo.findById(cartDTO.getBookId()).orElseThrow(() -> new BookException("book with id " + cartDTO.getBookId() + " not found"));
        Cart cart = new Cart(user, book, cartDTO.getQuantity(),book.getBookPrice()*cartDTO.getQuantity());
        return modelMapper.map(cartRepository.save(cart), CartResponseDTO.class);
    }

    @Override
    public List<CartResponseDTO> getAll() {
        List<Cart> cartList = cartRepository.findAll();
        return cartList.stream().map(cartItem -> modelMapper.map(cartItem, CartResponseDTO.class)).collect(Collectors.toList());
    }

    @Override
    public List<CartResponseDTO> getByUserId(String authHeader) throws UserException {
        String token = jwtUtil.parseToken(authHeader);
        long userId = jwtUtil.decodeToken(token);
        try {
            List<Cart> cartList = cartRepository.findByUserId(userId);
            return cartList.stream().map(cartItem -> modelMapper.map(cartItem, CartResponseDTO.class)).collect(Collectors.toList());
        } catch (Exception e) {
            new UserException("User with id " + userId + " does not exist");
        }

        return null;

    }

    @Override
    public void deleteById(Long id) throws CartException {
        try {
            cartRepository.deleteById(id);
        } catch (RuntimeException e) {
            throw new CartException("cart with id " + id + " not found");
        }
    }

    @Override
    public CartResponseDTO UpdateQuantity( Long id,Long quantity) throws CartException {
        Cart cart = cartRepository.findById(id).orElseThrow(() -> new CartException("cart with id " + id + " not found"));
        cart.setQuantity(quantity);
        cartRepository.save(cart);
        return modelMapper.map(cart, CartResponseDTO.class);

    }
}
