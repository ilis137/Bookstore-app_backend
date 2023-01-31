package com.example.bookstorebackendappcfp.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import lombok.*;


import java.time.LocalDate;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRegistrationDTO {

    @NotNull(message = "UserName should not be null")
    private String firstName;

    @NotNull(message = "UserName should not be null")
    private String lastName;

    @Nullable
    private String kyc;

    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern="dd MMM yyyy")
    @NotNull(message = "date of birth cannot be null")
    private LocalDate dob;

    @NotBlank
    @Email(message = "Email address entered is not valid")
    private String email;

    @JsonProperty(access = Access.WRITE_ONLY)
    @NotBlank(message = "password cannot be null")
    private String password;

    @Nullable
    private String verified;

    @Nullable
    private String otp;

}

