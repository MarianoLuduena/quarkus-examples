package com.redbee.mock;

import com.redbee.domain.Person;
import com.redbee.model.controller.PersonControllerModel;
import com.redbee.model.controller.UpsertPersonControllerModel;
import com.redbee.model.entity.PersonEntity;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class PersonMockFactory {

    private static final Long ID = 314L;
    private static final String DOCUMENT_TYPE = "PASSPORT";
    private static final Long DOCUMENT_NUMBER = 40931812L;
    private static final String FIRST_NAME = "Warren";
    private static final String SURNAME = "Robinett";
    private static final LocalDate BIRTH_DATE = LocalDate.of(1951, 12, 25);
    private static final String GENDER = "M";
    private static final String EMAIL = "easter.egg@adventure.com";
    private static final LocalDateTime CREATED_AT = LocalDateTime.of(1977, 10, 25, 12, 30, 45);
    private static final LocalDateTime UPDATED_AT = LocalDateTime.of(1979, 4, 12, 20, 14, 11);

    public static UpsertPersonControllerModel upsertPersonControllerModel() {
        final var model = new UpsertPersonControllerModel();
        model.setDocumentType(DOCUMENT_TYPE);
        model.setDocumentNumber(DOCUMENT_NUMBER);
        model.setFirstName(FIRST_NAME);
        model.setSurname(SURNAME);
        model.setBirthDate(BIRTH_DATE);
        model.setGender(GENDER);
        model.setEmail(EMAIL);

        return model;
    }

    public static PersonControllerModel personControllerModel() {
        return new PersonControllerModel(
                ID,
                DOCUMENT_TYPE,
                DOCUMENT_NUMBER,
                FIRST_NAME,
                SURNAME,
                BIRTH_DATE,
                GENDER,
                EMAIL,
                CREATED_AT,
                UPDATED_AT
        );
    }

    public static Person person() {
        return new Person(
                ID,
                DOCUMENT_TYPE,
                DOCUMENT_NUMBER,
                FIRST_NAME,
                SURNAME,
                BIRTH_DATE,
                GENDER,
                EMAIL,
                CREATED_AT,
                UPDATED_AT
        );
    }

    public static Person.Upsert personUpsert() {
        return new Person.Upsert(
                ID,
                DOCUMENT_TYPE,
                DOCUMENT_NUMBER,
                FIRST_NAME,
                SURNAME,
                BIRTH_DATE,
                GENDER,
                EMAIL
        );
    }

    public static PersonEntity personEntity() {
        final var entity = new PersonEntity();
        entity.id = ID;
        entity.documentType = DOCUMENT_TYPE;
        entity.documentNumber = DOCUMENT_NUMBER;
        entity.firstName = FIRST_NAME;
        entity.surname = SURNAME;
        entity.birthDate = BIRTH_DATE;
        entity.gender = GENDER;
        entity.email = EMAIL;
        entity.createdAt = CREATED_AT;
        entity.updatedAt = UPDATED_AT;

        return entity;
    }

}
