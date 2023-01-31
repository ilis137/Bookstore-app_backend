package com.example.bookstorebackendappcfp.Services;

import com.example.bookstorebackendappcfp.DTO.LoginDTO;
import com.example.bookstorebackendappcfp.DTO.ResetPasswordDTO;
import com.example.bookstorebackendappcfp.DTO.UserRegistrationDTO;
import com.example.bookstorebackendappcfp.Exception.UserNotFoundException;
import com.example.bookstorebackendappcfp.Exception.UsernamePasswordInvalidException;
import com.example.bookstorebackendappcfp.Model.User;
import com.example.bookstorebackendappcfp.repository.UserRepository;
import com.example.bookstorebackendappcfp.util.EmailSenderService;
import com.example.bookstorebackendappcfp.util.JWTUtil;
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

    private static final ModelMapper modelMapper = new ModelMapper();

    @Override
    public String saveUser(UserRegistrationDTO userRegistrationDTO) {
        String password = userRegistrationDTO.getPassword();
        User user = modelMapper.map(userRegistrationDTO, User.class);
        user.setRegisteredDate(LocalDate.now());
        user.setUpdatedDate(LocalDate.now());
        user.setVerified(false);
        user.setKyc(null);
        user.setOtp(null);
        String encodedPassword = passwordEncoder.encode(password);
        user.setPassword(encodedPassword);
        user = userRepo.save(user);
        String token = jwtUtil.createToken(user.getUserId(), user.getEmail());
        sendRegistrationMail(user,token);
        return token;
    }

    @Override
    public String AuthenticateUser(LoginDTO loginDTO) throws UsernamePasswordInvalidException {

        try {
            User user = userRepo.findByEmail(loginDTO.getEmail()).orElseThrow();
            Authentication authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword()));
            String token = jwtUtil.createToken(user.getUserId(), user.getEmail());
             SecurityContextHolder.getContext().setAuthentication(authentication);
            return token;
        } catch (Exception e) {
            throw new UsernamePasswordInvalidException("Invalid username or password");
        }

    }

    @Override
    public UserRegistrationDTO findUser(long id) {
//       i SecurityContextHolder.getContext().getAuthentication().getDetails();
        return modelMapper.map(userRepo.findById(id), UserRegistrationDTO.class);
    }

    @Override
    public String forgotPassword(String email) throws UserNotFoundException {

        User user = userRepo.findByEmail(email).orElseThrow(()->new UserNotFoundException("user of this email does not exist "+email));
        String token=jwtUtil.createToken(user.getUserId(),user.getEmail());
        return token;
    }

    @Override
    public String resetPassword(ResetPasswordDTO resetPasswordDTO) throws UserNotFoundException {
       String email= jwtUtil.getEmailFromToken(resetPasswordDTO.getToken());
        User user=userRepo.findByEmail(email).orElseThrow(()->new UserNotFoundException("user of this email does not exist "+email));
        user.setPassword(resetPasswordDTO.getNewPassword());
        userRepo.save(user);
        return resetPasswordDTO.getToken();
    }

    @Override
    public boolean verifyUser(String token) throws UserNotFoundException {
       long id = jwtUtil.decodeToken(token);
        User user=userRepo.findById(id).orElseThrow(()->new UserNotFoundException("user of this id does not exist "+token));
        user.setVerified(true);
        user=userRepo.save(user);
        return user.isVerified();

    }


    public void sendRegistrationMail(User user,String token) {
        emailSenderService.sendEmail(user.getEmail(), "Registration",
                "Congratulations!!, you have successfully registered to book store app," +
                        " Your registration is successful please verify yourself: http://localhost:8080/api/auth/user/verify/"+ token );
    }
}
