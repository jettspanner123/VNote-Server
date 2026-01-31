package com.vnote.VNote.modules.validators;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public class BusinessInputDTO {
    @NotNull(message = "User id cannot be null!")
    public UUID userId;

    @NotEmpty(message = "Business name cannot be empty!")
    @Size(min = 2, message = "Business name should at least be of 2 characters.")
    public String name;

    @NotEmpty(message = "Business type cannot be empty!")
    public String type;
}
