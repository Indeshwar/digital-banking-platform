package com.bank.customer.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DtoErrorResponse {
    private LocalDateTime timeStamp;
    private int status;
    private String error;
    private String message;
}
