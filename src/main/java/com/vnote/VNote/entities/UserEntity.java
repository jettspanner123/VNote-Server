package com.vnote.VNote.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Getter
@Setter
@Entity
@Table(name = "users")
@EntityListeners(AuditingEntityListener.class)
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String fullName;
    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, unique = true)
    private String phoneNumber;

    @Column(nullable = false)
    private String password;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @Column()
    private LocalDateTime deletedAt;

    @OneToMany(
            mappedBy = "user",
            cascade = { CascadeType.MERGE, CascadeType.PERSIST },
            orphanRemoval = true
    )
    private java.util.List<BusinessEntity> businesses = new ArrayList<>();

    @OneToMany(
            mappedBy = "user",
            cascade = { CascadeType.MERGE, CascadeType.PERSIST },
            orphanRemoval = true
    )
    private java.util.List<ContactEntity> contacts = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserEntity)) return false;
        return id != null && id.equals(((UserEntity) o).id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }


}
