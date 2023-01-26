package com.redbee.model.controller;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.redbee.domain.Person;

import java.time.LocalDate;
import java.time.LocalDateTime;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record PersonControllerModel(
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

    public static PersonControllerModel from(final Person person) {
        return new PersonControllerModel(
                person.id(),
                person.documentType(),
                person.documentNumber(),
                person.firstName(),
                person.surname(),
                person.birthDate(),
                person.gender(),
                person.email(),
                person.createdAt(),
                person.updatedAt()
        );
    }

}
