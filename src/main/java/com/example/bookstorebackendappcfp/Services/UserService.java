package com.example.bookstorebackendappcfp.Services;

import com.example.bookstorebackendappcfp.DTO.LoginDTO;
import com.example.bookstorebackendappcfp.DTO.ResetPasswordDTO;
import com.example.bookstorebackendappcfp.DTO.UserRegistrationDTO;
import com.example.bookstorebackendappcfp.Exception.UserException;
import com.example.bookstorebackendappcfp.Exception.UsernamePasswordInvalidException;
import com.example.bookstorebackendappcfp.Model.User;
import com.example.bookstorebackendappcfp.repository.UserRepository;
import com.example.bookstorebackendappcfp.util.EmailSenderService;
import com.example.bookstorebackendappcfp.util.JWTUtil;
import com.example.bookstorebackendappcfp.util.OTPGenerator;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


import java.time.LocalDate;
import java.util.Optional;


@Service
@Slf4j
public class UserService implements IUserService {
    @Autowired
    private UserRepository userRepo;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JWTUtil jwtUtil;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private EmailSenderService emailSenderService;

    @Autowired
    private OTPGenerator otpGenerator;

    private static final ModelMapper modelMapper = new ModelMapper();

    @Override
    public String saveUser(UserRegistrationDTO userRegistrationDTO) throws UserException {

        Optional<User> data = userRepo.findByEmail(userRegistrationDTO.getEmail());
         if(data.isPresent()){
             throw new UserException("Error: email already exists");
         }
        String password = userRegistrationDTO.getPassword();
        User user = modelMapper.map(userRegistrationDTO, User.class);
        user.setRegisteredDate(LocalDate.now());
        user.setUpdatedDate(LocalDate.now());
        user.setVerified(false);
        user.setKyc(null);
        user.setOtp(otpGenerator.generateOTP());
        String encodedPassword = passwordEncoder.encode(password);
        user.setPassword(encodedPassword);
        user = userRepo.save(user);
        String token = jwtUtil.createToken(user.getUserId(), user.getEmail());
        sendRegistrationMail(user,token);
        return token;
    }

    @Override
    public String AuthenticateUser(LoginDTO loginDTO) throws UsernamePasswordInvalidException, UserException {


            User user = userRepo.findByEmail(loginDTO.getEmail()).orElseThrow(()->new UserException("Error:User not found"));
            Authentication authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword()));
            String token = jwtUtil.createToken(user.getUserId(), user.getEmail());
             SecurityContextHolder.getContext().setAuthentication(authentication);
            return token;


    }

    @Override
    public UserRegistrationDTO findUser(String authHeader) {
        String token = jwtUtil.parseToken(authHeader);
        long userId = jwtUtil.decodeToken(token);
        return modelMapper.map(userRepo.findById(userId), UserRegistrationDTO.class);
    }

    @Override
    public String forgotPassword(String email) throws UserException {

        User user = userRepo.findByEmail(email).orElseThrow(()->new UserException("user of this email does not exist "+email));
        String token=jwtUtil.createToken(user.getUserId(),user.getEmail());
        return token;
    }

    @Override
    public String resetPassword(ResetPasswordDTO resetPasswordDTO) throws UserException {
       String email= jwtUtil.getEmailFromToken(resetPasswordDTO.getToken());
        User user=userRepo.findByEmail(email).orElseThrow(()->new UserException("user of this email does not exist "+email));
        String encodedPassword = passwordEncoder.encode(resetPasswordDTO.getNewPassword());
        user.setPassword(encodedPassword);
        userRepo.save(user);
        return resetPasswordDTO.getToken();
    }

    @Override
    public boolean verifyUser(String token,String otp) throws UserException {
       long id = jwtUtil.decodeToken(token);
        User user=userRepo.findById(id).orElseThrow(()->new UserException("user of this id does not exist "+token));
       log.info(user.getOtp());
       log.info(otp);
        if(user.getOtp().equals(otp)){
            user.setVerified(true);
            user=userRepo.save(user);
            log.info(""+user.isVerified());
            return user.isVerified();
        }
            return false;
        
     

    }

    @Override
    public String regenerateOTP(String token) throws UserException{
        long id = jwtUtil.decodeToken(token);
        User user=userRepo.findById(id).orElseThrow(()->new UserException("user of this id does not exist "+token));
        String otp=otpGenerator.generateOTP();
        user.setOtp(otp);
        userRepo.save(user);
        sendOTPMail(user);
        
        return otp;
    }

    public void sendOTPMail(User user){
        emailSenderService.sendEmail(user.getEmail(), "OTP regeneration"," Your new  OTP is: "+user.getOtp()  );
  }

    public void sendRegistrationMail(User user,String token) {
        emailSenderService.sendEmail(user.getEmail(), "Registration",
                "Congratulations!!, you have successfully registered to book store app," +
                        " Your registration is successful please verify yourselfwith OTP: "+user.getOtp()  );
    }
}
