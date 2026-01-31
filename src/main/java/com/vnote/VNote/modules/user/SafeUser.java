package com.vnote.VNote.modules.user;

import com.vnote.VNote.entities.BusinessEntity;
import com.vnote.VNote.entities.UserEntity;
import com.vnote.VNote.modules.business.Business;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SafeUser {
    public UUID id;
    public String fullName;
    public String email;
    public String phoneNumber;
    public LocalDateTime createdAt;
    public LocalDateTime updatedAt;
    public java.util.List<Business> businesses;


    public static SafeUser fromEntity(UserEntity user) {
       return SafeUser.builder()
               .id(user.getId())
               .fullName(user.getFullName())
               .email(user.getEmail())
               .phoneNumber(user.getPhoneNumber())
               .createdAt(user.getCreatedAt())
               .updatedAt(user.getUpdatedAt())
               .businesses(user.getBusinesses().stream().map(Business::fromEntity).toList())
               .build();
    }
}
