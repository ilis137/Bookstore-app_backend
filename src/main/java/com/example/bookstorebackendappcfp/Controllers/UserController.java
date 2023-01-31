package com.example.bookstorebackendappcfp.Controllers;

import com.example.bookstorebackendappcfp.DTO.LoginDTO;
import com.example.bookstorebackendappcfp.DTO.ResetPasswordDTO;
import com.example.bookstorebackendappcfp.DTO.ResponseDTO;
import com.example.bookstorebackendappcfp.DTO.UserRegistrationDTO;
import com.example.bookstorebackendappcfp.Exception.UserNotFoundException;
import com.example.bookstorebackendappcfp.Exception.UsernamePasswordInvalidException;
import com.example.bookstorebackendappcfp.Services.IUserService;
import com.example.bookstorebackendappcfp.util.JWTUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class UserController {
    @Autowired
    private IUserService userService;


    @Autowired
    JWTUtil jwtUtil;
    @PostMapping("/register")
    public ResponseEntity<ResponseDTO> addUser(@Valid @RequestBody UserRegistrationDTO userRegistrationDTO){
        ResponseDTO responseDTO = ResponseDTO.Build("User registration successful", userService.saveUser(userRegistrationDTO));
        return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);
    }


    @PostMapping("/login")
    public ResponseEntity<ResponseDTO> loginUser(@Valid @RequestBody LoginDTO loginDTO) throws UsernamePasswordInvalidException {
        ResponseDTO responseDTO = ResponseDTO.Build("User authentication successful", userService.AuthenticateUser(loginDTO));
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @GetMapping("/retrieve/{id}")
    public ResponseEntity<ResponseDTO> loginUser(@PathVariable long id) {
        ResponseDTO responseDTO = ResponseDTO.Build("User data successfully retreived", userService.findUser(id));
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @PostMapping("/forgotPassword/{email}")
    public ResponseEntity<ResponseDTO> forgotPassword(@PathVariable String email) throws UserNotFoundException {
        ResponseDTO responseDTO = ResponseDTO.Build("forgot password process successful", userService.forgotPassword(email));
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @PostMapping("/resetpassword")
    public ResponseEntity<ResponseDTO> resetPassword(@Valid @RequestBody ResetPasswordDTO resetPasswordDTO) throws UserNotFoundException {
        ResponseDTO responseDTO = ResponseDTO.Build("password reset successful", userService.resetPassword(resetPasswordDTO));
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @GetMapping("/user/verify/{token}")
    public ResponseEntity<ResponseDTO> verifyUser(@PathVariable String token) throws UserNotFoundException {
        ResponseDTO responseDTO = ResponseDTO.Build("password reset successful", userService.verifyUser(token));
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }
}
