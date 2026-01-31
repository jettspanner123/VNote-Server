package com.vnote.VNote.modules.contact;

import com.vnote.VNote.modules.BaseResponse;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.Optional;

@Getter
@Setter
@NotEmpty
@AllArgsConstructor
@SuperBuilder
public class ContactResponse extends BaseResponse {
   public Optional<Contact> contact;
}
