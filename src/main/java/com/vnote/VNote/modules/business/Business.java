package com.vnote.VNote.modules.business;

import com.vnote.VNote.entities.BusinessEntity;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Builder
public class Business {
    private UUID id;
    private String name;
    private String type;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static Business fromEntity(BusinessEntity business) {
        return  Business.builder()
                .id(business.getId())
                .name(business.getName())
                .type(business.getType())
                .createdAt(business.getCreatedAt())
                .updatedAt(business.getUpdatedAt())
                .build();
    }
}
