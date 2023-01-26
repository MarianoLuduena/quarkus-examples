package com.redbee.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record Person(
        Long id,
        String documentType,
        Long documentNumber,
        String firstName,
        String surname,
        LocalDate birthDate,
        String gender,
        String email,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {

    public record Upsert(
            Long id,
            String documentType,
            Long documentNumber,
            String firstName,
            String surname,
            LocalDate birthDate,
            String gender,
            String email
    ) {
    }

}
