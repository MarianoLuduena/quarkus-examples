package com.redbee.service;

import com.redbee.config.error.ErrorCode;
import com.redbee.config.exception.NotFoundException;
import com.redbee.domain.Person;
import com.redbee.mock.PersonMockFactory;
import com.redbee.repository.PersonRepository;
import io.smallrye.mutiny.Uni;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

class PersonServiceTest {

    private static final Person.Upsert PERSON_TO_UPSERT = PersonMockFactory.personUpsert();
    private static final Long PERSON_ID = PERSON_TO_UPSERT.id();

    private final PersonRepository personRepository = Mockito.mock(PersonRepository.class);

    private PersonService service;

    @BeforeEach
    void setup() {
        service = new PersonService(personRepository);
    }

    @Test
    void creatingAPersonReturnsTheCreatedDomainObject() {
        final var expected = PersonMockFactory.person();
        Mockito.when(personRepository.create(PERSON_TO_UPSERT)).thenReturn(Uni.createFrom().item(expected));

        final var actual = service.create(PERSON_TO_UPSERT).await().indefinitely();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void gettingAnExistingPersonByIdShouldReturnAnOptionalContainingTheFoundPerson() {
        final var expected = PersonMockFactory.person();
        Mockito.when(personRepository.get(expected.id())).thenReturn(Uni.createFrom().item(Optional.of(expected)));

        final var actual = service.get(expected.id()).await().indefinitely();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void gettingANonExistingPersonByIdShouldThrowANotFoundException() {
        Mockito.when(personRepository.get(Mockito.anyLong())).thenReturn(Uni.createFrom().item(Optional.empty()));

        final var thrown = Assertions.assertThrowsExactly(
                NotFoundException.class,
                () -> service.get(PERSON_ID).await().indefinitely()
        );

        Assertions.assertEquals(ErrorCode.RESOURCE_NOT_FOUND, thrown.getErrorCode());
    }

    @Test
    void updatingAnExistingPersonReturnsTheUpdatedDomainObject() {
        final var expected = PersonMockFactory.person();
        Mockito.when(personRepository.update(PERSON_TO_UPSERT))
                .thenReturn(Uni.createFrom().item(Optional.of(expected)));

        final var actual = service.update(PERSON_TO_UPSERT).await().indefinitely();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void updatingANonExistingPersonByIdShouldThrowANotFoundException() {
        Mockito.when(personRepository.update(PERSON_TO_UPSERT)).thenReturn(Uni.createFrom().item(Optional.empty()));

        final var thrown = Assertions.assertThrowsExactly(
                NotFoundException.class,
                () -> service.update(PERSON_TO_UPSERT).await().indefinitely()
        );

        Assertions.assertEquals(ErrorCode.RESOURCE_NOT_FOUND, thrown.getErrorCode());
    }

    @Test
    void deletingAnExistingPersonReturnsTheDeletedDomainObject() {
        final var expected = PersonMockFactory.person();
        Mockito.when(personRepository.delete(PERSON_ID)).thenReturn(Uni.createFrom().item(Optional.of(expected)));

        final var actual = service.delete(PERSON_ID).await().indefinitely();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void deletingANonExistingPersonByIdShouldThrowANotFoundException() {
        Mockito.when(personRepository.delete(PERSON_ID)).thenReturn(Uni.createFrom().item(Optional.empty()));

        final var thrown = Assertions.assertThrowsExactly(
                NotFoundException.class,
                () -> service.delete(PERSON_ID).await().indefinitely()
        );

        Assertions.assertEquals(ErrorCode.RESOURCE_NOT_FOUND, thrown.getErrorCode());
    }

}
