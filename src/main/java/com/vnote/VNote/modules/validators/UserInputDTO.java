package com.vnote.VNote.modules.validators;

import jakarta.validation.constraints.*;

public class UserInputDTO {

    @NotBlank(message = "Full Name cannot be blank!")
    @Size(min = 2, message = "Full name should be at least of 2 characters.")
    public String fullName;

    @NotBlank(message = "Email cannot be black!")
    @Email(message = "Invalid email address!")
    public String email;

    @NotBlank(message = "Phone number cannot be black!")
    @Pattern(
            regexp = "^\\d{10}$",
            message = "Phone number must contain exactly 10 digits"
    )
    public String phoneNumber;

    @NotBlank(message = "Password cannot be blank!")
    @Size(min = 8, max = 16,message = "Password should be minimum 8 characters and maximum of 16 characters.")
    public String password;
}
