package com.vnote.VNote.modules.contact;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.util.UUID;

public class ContactInputDTO {
    @NotNull(message = "User id cannot be empty!")
    public UUID userId;

    @NotEmpty(message = "Phone number cannot be empty!")
    @Pattern(
            regexp = "^\\d{10}$",
            message = "Enter a valid phone number of 10 digits."
    )
    public String phoneNumber;

    public String fullName;

    @Email
    public String email;
}
