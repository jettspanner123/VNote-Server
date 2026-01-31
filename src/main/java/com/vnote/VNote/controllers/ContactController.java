package com.vnote.VNote.controllers;

import com.vnote.VNote.exceptions.ContactAlreadyExists;
import com.vnote.VNote.exceptions.UserNotFound;
import com.vnote.VNote.modules.BaseResponse;
import com.vnote.VNote.modules.contact.ContactInputDTO;
import com.vnote.VNote.modules.contact.ContactResponse;
import com.vnote.VNote.services.ContactService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("api/contact")
public class ContactController {

    final ContactService contactService;

    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    @GetMapping("/health")
    public ResponseEntity<BaseResponse> healthCheck() {
        this.contactService.healthCheck();
        return ResponseEntity.ok(
                BaseResponse.builder()
                        .success(true)
                        .message("Contact Service Working Fine!")
                        .build()
        );
    }


    @PostMapping("/add")
    public ResponseEntity<ContactResponse> addContact(@Valid @RequestBody ContactInputDTO contactData) {
        try {
            final var data = this.contactService.addContact(contactData);
            return ResponseEntity.ok(
                    ContactResponse.builder()
                            .success(true)
                            .message("Contact Saved!")
                            .contact(Optional.ofNullable(data))
                            .build()
            );
        } catch (ContactAlreadyExists e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(
                    ContactResponse.builder()
                            .success(false)
                            .message(e.getMessage())
                            .contact(Optional.empty())
                            .build()
            );
        } catch (UserNotFound e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    ContactResponse.builder()
                            .success(false)
                            .message(e.getMessage())
                            .contact(Optional.empty())
                            .build()
            );
        }
    }
}
