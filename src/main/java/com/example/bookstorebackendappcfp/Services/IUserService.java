package com.example.bookstorebackendappcfp.Services;

import com.example.bookstorebackendappcfp.DTO.LoginDTO;
import com.example.bookstorebackendappcfp.DTO.ResetPasswordDTO;
import com.example.bookstorebackendappcfp.DTO.UserRegistrationDTO;
import com.example.bookstorebackendappcfp.Exception.UserNotFoundException;
import com.example.bookstorebackendappcfp.Exception.UsernamePasswordInvalidException;
import com.example.bookstorebackendappcfp.Model.User;

public interface IUserService {

        public String saveUser(UserRegistrationDTO userRegistrationDTO);

    String AuthenticateUser(LoginDTO loginDTO) throws UsernamePasswordInvalidException;

    UserRegistrationDTO findUser(long id);

    String forgotPassword(String email) throws UserNotFoundException;

    String resetPassword(ResetPasswordDTO resetPasswordDTO) throws UserNotFoundException;

    boolean verifyUser(String token) throws UserNotFoundException;
}
