package com.redbee.repository;

import com.redbee.dao.PersonDao;
import com.redbee.domain.Person;
import com.redbee.mock.PersonMockFactory;
import com.redbee.model.entity.PersonEntity;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import io.smallrye.mutiny.Uni;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.inject.Inject;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

@QuarkusTest
class PersonRepositoryTest {

    private static final Person.Upsert PERSON_TO_UPSERT = PersonMockFactory.personUpsert();
    private static final Person PERSON = PersonMockFactory.person();

    @Inject
    PersonRepository repository;

    @InjectMock
    PersonDao personDao;

    @Test
    void creatingAPersonShouldReturnTheCreatedDomainObject() {
        Mockito.when(personDao.persistAndFlush(Mockito.any(PersonEntity.class)))
                .thenReturn(Uni.createFrom().item(PersonMockFactory.personEntity()));

        final var actual = repository.create(PERSON_TO_UPSERT).await().indefinitely();
        Assertions.assertEquals(PERSON, actual);
    }

    @Test
    void gettingAPersonShouldReturnAnOptionalContainingTheDomainObject() {
        Mockito.when(personDao.findById(PERSON.id()))
                .thenReturn(Uni.createFrom().item(PersonMockFactory.personEntity()));

        final var actual = repository.get(PERSON.id()).await().indefinitely();
        Assertions.assertTrue(actual.isPresent());
        Assertions.assertEquals(PERSON, actual.get());
    }

    @Test
    void gettingANonExistingPersonShouldReturnAnEmptyOptional() {
        Mockito.when(personDao.findById(PERSON.id())).thenReturn(Uni.createFrom().nullItem());
        final var actual = repository.get(PERSON.id()).await().indefinitely();
        Assertions.assertTrue(actual.isEmpty());
    }

    @Test
    void updatingAPersonShouldReturnAnOptionalContainingTheDomainObject() {
        final var now = ZonedDateTime.now().withZoneSameInstant(ZoneOffset.UTC).toLocalDateTime();
        Mockito.when(personDao.findById(PERSON.id()))
                .thenReturn(Uni.createFrom().item(PersonMockFactory.personEntity()));

        final var actual = repository.update(PERSON_TO_UPSERT).await().indefinitely();

        Assertions.assertTrue(actual.isPresent());
        Assertions.assertTrue(now.isBefore(actual.get().updatedAt()));
    }

    @Test
    void updatingANonExistingPersonShouldReturnAnEmptyOptional() {
        Mockito.when(personDao.findById(PERSON.id())).thenReturn(Uni.createFrom().nullItem());
        final var actual = repository.update(PERSON_TO_UPSERT).await().indefinitely();
        Assertions.assertTrue(actual.isEmpty());
    }

    @Test
    void deletingAPersonShouldReturnAnOptionalContainingTheDomainObject() {
        Mockito.when(personDao.findById(PERSON.id()))
                .thenReturn(Uni.createFrom().item(PersonMockFactory.personEntity()));
        Mockito.when(personDao.delete(Mockito.any(PersonEntity.class))).thenReturn(Uni.createFrom().voidItem());

        final var actual = repository.delete(PERSON.id()).await().indefinitely();
        Assertions.assertTrue(actual.isPresent());
        Assertions.assertEquals(PERSON, actual.get());
    }

    @Test
    void deletingANonExistingPersonShouldReturnAnEmptyOptional() {
        Mockito.when(personDao.findById(PERSON.id())).thenReturn(Uni.createFrom().nullItem());
        final var actual = repository.delete(PERSON.id()).await().indefinitely();
        Assertions.assertTrue(actual.isEmpty());
    }

}
