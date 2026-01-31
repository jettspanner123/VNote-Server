package com.vnote.VNote.modules.contact;

import com.vnote.VNote.entities.ContactEntity;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class Contact {
    public UUID id;
    public String phoneNumber;
    public String email;
    public String fullName;
    public LocalDateTime createdAt;


    public static Contact fromEntity(ContactEntity entity) {
        return Contact.builder()
                .id(entity.getId())
                .fullName(entity.getFullName())
                .phoneNumber(entity.getPhoneNumber())
                .email(entity.getEmail())
                .createdAt(entity.getCreatedAt())
                .build();
    }
}
