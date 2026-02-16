package com.bank.customer.entity;

import com.bank.customer.enums.KycStatus;
import jakarta.persistence.*;
import lombok.*;


import java.time.LocalDateTime;
import java.util.UUID;
@Entity
@Table(name="customers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Customer {
    @Id // assign Primary Key
    @GeneratedValue(strategy = GenerationType.UUID) //Generates Automatically UUID
    @Column(name="id", nullable = false, updatable = false)
    private UUID id;

    @Column(name="first_name", nullable = false, length = 100)
    private String firstName;

    @Column(name="last_name", nullable = false, length = 100)
    private String lastName;

    @Column(name="email", unique = true, nullable = false, length = 150) //Assign Unique Email
    private String email;

    @Column(name="phone", nullable = false, length = 20)
    private String phone;

    @Enumerated(EnumType.STRING)
    @Column(name="kyc_status", nullable = false, length = 20)
    private KycStatus kycStatus;

    @Column(name="created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name="updated_at")
    private LocalDateTime updatedAt;

    protected void onCreatedAt(){
        createdAt = LocalDateTime.now();
        updatedAt = createdAt;
    }

    protected void onUpdateAt(){
        updatedAt = LocalDateTime.now();
    }

    
}
