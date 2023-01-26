package com.redbee.model.controller;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.redbee.domain.Person;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public class UpsertPersonControllerModel {

    @Pattern(regexp = "^(ID_NUMBER|PASSPORT)$")
    private String documentType;

    @NotNull
    @Positive
    private Long documentNumber;

    @NotBlank
    @Size(max = 50)
    private String firstName;

    @NotBlank
    @Size(max = 50)
    private String surname;

    @NotNull
    @PastOrPresent
    private LocalDate birthDate;

    @Pattern(regexp = "^[MFO]$")
    private String gender;

    @Email
    @Size(max = 100)
    private String email;

    public String getDocumentType() {
        return documentType;
    }

    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }

    public Long getDocumentNumber() {
        return documentNumber;
    }

    public void setDocumentNumber(Long documentNumber) {
        this.documentNumber = documentNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + '[' +
                "documentType=" + documentType +
                ", documentNumber=" + documentNumber +
                ", firstName=" + firstName +
                ", surname=" + surname +
                ", birthDate=" + birthDate +
                ", gender=" + gender +
                ", email=" + email +
                ']';
    }

    public Person.Upsert toDomain() {
        return doConvertToDomain(null);
    }

    public Person.Upsert toDomain(final Long id) {
        return doConvertToDomain(id);
    }

    private Person.Upsert doConvertToDomain(final Long id) {
        return new Person.Upsert(
                id,
                documentType,
                documentNumber,
                firstName,
                surname,
                birthDate,
                gender,
                email
        );
    }

}
