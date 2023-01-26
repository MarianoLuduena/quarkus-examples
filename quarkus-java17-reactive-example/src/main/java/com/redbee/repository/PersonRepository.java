package com.redbee.repository;

import com.redbee.dao.PersonDao;
import com.redbee.domain.Person;
import com.redbee.model.entity.PersonEntity;
import io.quarkus.logging.Log;
import io.smallrye.mutiny.Uni;

import javax.enterprise.context.ApplicationScoped;
import java.util.Optional;

@ApplicationScoped
public class PersonRepository {

    private final PersonDao personDao;

    public PersonRepository(final PersonDao personDao) {
        this.personDao = personDao;
    }

    public Uni<Person> create(final Person.Upsert person) {
        final var entity = PersonEntity.from(person);
        Log.infov("Creating person {0}", entity);
        return personDao.persistAndFlush(entity).map(savedEntity -> {
            Log.infov("Created person {0}", savedEntity);
            return savedEntity.toDomain();
        });
    }

    public Uni<Optional<Person>> update(final Person.Upsert person) {
        return doGet(person.id()).map(oEntity ->
                oEntity.map(entity -> {
                    Log.infov("Updating person {0} with {1}", entity, person);
                    final var updatedEntity = entity.with(person);
                    Log.infov("Updated person {0}", updatedEntity);
                    return updatedEntity.toDomain();
                })
        );
    }

    public Uni<Optional<Person>> get(final long id) {
        return doGet(id).map(oEntity -> oEntity.map(PersonEntity::toDomain));
    }

    public Uni<Optional<Person>> delete(final long id) {
        return doGet(id).flatMap(oEntity ->
                oEntity.map(entity -> {
                            Log.infov("Deleting person {0}", entity);
                            return personDao.delete(entity).map(unused -> {
                                Log.info("Person was deleted");
                                return Optional.of(entity.toDomain());
                            });
                        })
                        .orElseGet(() -> Uni.createFrom().item(Optional.empty()))
        );
    }

    private Uni<Optional<PersonEntity>> doGet(final long id) {
        Log.infov("Getting person with id {0}", id);
        return personDao.findById(id).map(nullableEntity ->
                Optional.ofNullable(nullableEntity).map(entity -> {
                    Log.infov("Found person {0}", entity);
                    return entity;
                })
        );
    }

}
