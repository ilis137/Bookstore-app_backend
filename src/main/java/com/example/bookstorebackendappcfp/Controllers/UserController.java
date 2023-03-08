package com.example.bookstorebackendappcfp.Controllers;

import com.example.bookstorebackendappcfp.DTO.LoginDTO;
import com.example.bookstorebackendappcfp.DTO.ResetPasswordDTO;
import com.example.bookstorebackendappcfp.DTO.ResponseDTO;
import com.example.bookstorebackendappcfp.DTO.UserRegistrationDTO;
import com.example.bookstorebackendappcfp.Exception.UserException;
import com.example.bookstorebackendappcfp.Exception.UsernamePasswordInvalidException;
import com.example.bookstorebackendappcfp.Services.IUserService;
import com.example.bookstorebackendappcfp.util.JWTUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin("*")
public class UserController {
    @Autowired
    private IUserService userService;


    @Autowired
    JWTUtil jwtUtil;
    @PostMapping("/register")
    public ResponseEntity<ResponseDTO> addUser(@Valid @RequestBody UserRegistrationDTO userRegistrationDTO) throws UserException {
        System.out.println("register");
        ResponseDTO responseDTO = ResponseDTO.Build("User registration successful", userService.saveUser(userRegistrationDTO));
        return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);
    }


    @PostMapping("/login")
    public ResponseEntity<ResponseDTO> loginUser(@Valid @RequestBody LoginDTO loginDTO) throws UsernamePasswordInvalidException, UserException {
        ResponseDTO responseDTO = ResponseDTO.Build("User authentication successful", userService.AuthenticateUser(loginDTO));
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @GetMapping("/retrieve/user")
    public ResponseEntity<ResponseDTO> loginUser(@RequestHeader(name="Authorization") String authHeader) {
        ResponseDTO responseDTO = ResponseDTO.Build("User data successfully retreived", userService.findUser(authHeader));
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @PostMapping("/forgotPassword/{email}")
    public ResponseEntity<ResponseDTO> forgotPassword(@PathVariable String email) throws UserException {
        ResponseDTO responseDTO = ResponseDTO.Build("forgot password process successful", userService.forgotPassword(email));
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @PostMapping("/resetpassword")
    public ResponseEntity<ResponseDTO> resetPassword(@Valid @RequestBody ResetPasswordDTO resetPasswordDTO) throws UserException {
        ResponseDTO responseDTO = ResponseDTO.Build("password reset successful", userService.resetPassword(resetPasswordDTO));
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @GetMapping("/user/verify/{token}")
    public ResponseEntity<ResponseDTO> verifyUser(@PathVariable String token,@RequestParam String otp) throws UserException {
        ResponseDTO responseDTO = ResponseDTO.Build("user verification successful", userService.verifyUser(token,otp));
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }
    @GetMapping("/user/generateotp/{token}")
    public ResponseEntity<ResponseDTO> generateOTP(@PathVariable String token) throws UserException {
        ResponseDTO responseDTO = ResponseDTO.Build("OTP re-generated", userService.regenerateOTP(token));
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }
}
