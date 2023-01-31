package com.example.bookstorebackendappcfp.DTO;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginDTO {

    @NotBlank(message="email cannot be blank")
    @Email
    private String email;

    @NotBlank(message="password cannot be blank")
    private String password;
}
