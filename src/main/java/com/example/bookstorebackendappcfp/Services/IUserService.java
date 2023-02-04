package com.example.bookstorebackendappcfp.Services;

import com.example.bookstorebackendappcfp.DTO.LoginDTO;
import com.example.bookstorebackendappcfp.DTO.ResetPasswordDTO;
import com.example.bookstorebackendappcfp.DTO.UserRegistrationDTO;
import com.example.bookstorebackendappcfp.Exception.UserException;
import com.example.bookstorebackendappcfp.Exception.UsernamePasswordInvalidException;

public interface IUserService {

        public String saveUser(UserRegistrationDTO userRegistrationDTO) throws UserException;

    String AuthenticateUser(LoginDTO loginDTO) throws UsernamePasswordInvalidException, UserException;

    UserRegistrationDTO findUser(long id);

    String forgotPassword(String email) throws UserException;

    String resetPassword(ResetPasswordDTO resetPasswordDTO) throws UserException;

    boolean verifyUser(String token) throws UserException;
}
