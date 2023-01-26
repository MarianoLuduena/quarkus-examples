package com.redbee.model.entity;

import com.redbee.domain.Person;
import io.quarkus.hibernate.reactive.panache.PanacheEntityBase;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

@Entity
@Table(
        name = "person",
        uniqueConstraints = {
                @UniqueConstraint(name = "uq_person_document", columnNames = {"documentType", "documentNumber"})
        }
)
public class PersonEntity extends PanacheEntityBase {

    private static LocalDateTime now() {
        return ZonedDateTime.now().withZoneSameInstant(ZoneOffset.UTC).toLocalDateTime();
    }

    public static PersonEntity from(final Person.Upsert domain) {
        final var currentTimestamp = now();
        final var entity = new PersonEntity();
        entity.id = domain.id();
        entity.documentType = domain.documentType();
        entity.documentNumber = domain.documentNumber();
        entity.firstName = domain.firstName();
        entity.surname = domain.surname();
        entity.birthDate = domain.birthDate();
        entity.gender = domain.gender();
        entity.email = domain.email();
        entity.createdAt = currentTimestamp;
        entity.updatedAt = currentTimestamp;

        return entity;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(nullable = false, length = 20)
    public String documentType;

    @Column(nullable = false)
    public Long documentNumber;

    @Column(nullable = false, length = 50)
    public String firstName;

    @Column(nullable = false, length = 50)
    public String surname;

    @Column(nullable = false)
    public LocalDate birthDate;

    @Column(nullable = false, length = 5)
    public String gender;

    @Column(length = 100)
    public String email;

    @Column(nullable = false, updatable = false)
    public LocalDateTime createdAt;

    @Column(nullable = false)
    public LocalDateTime updatedAt;

    @Override
    public String toString() {
        return getClass().getSimpleName() + '[' +
                "id=" + id +
                ", documentType=" + documentType +
                ", documentNumber=" + documentNumber +
                ", firstName=" + firstName +
                ", surname=" + surname +
                ", birthDate=" + birthDate +
                ", gender=" + gender +
                ", email=" + email +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ']';
    }

    public Person toDomain() {
        return new Person(
                id,
                documentType,
                documentNumber,
                firstName,
                surname,
                birthDate,
                gender,
                email,
                createdAt,
                updatedAt
        );
    }

    public PersonEntity with(final Person.Upsert domain) {
        documentType = domain.documentType();
        documentNumber = domain.documentNumber();
        firstName = domain.firstName();
        surname = domain.surname();
        birthDate = domain.birthDate();
        gender = domain.gender();
        email = domain.email();
        updatedAt = now();

        return this;
    }

}
